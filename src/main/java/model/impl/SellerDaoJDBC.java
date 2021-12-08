package model.impl;

import db.DB;
import db.DBexception;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;
    public SellerDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = connection.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department "+
                   "ON seller.DepartmentId = department.Id "+
                   "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){

                Department department = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs,department);
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DBexception(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultset(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(department);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> finAll() {
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = connection.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs,dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DBexception(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultset(rs);
        }
    }
}
