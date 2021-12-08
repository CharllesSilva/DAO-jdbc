package application;

import model.dao.DaoFacttory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFacttory.createSellerDao();
        System.out.println("--TEST 1: SELLER FINDBYID--");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n--TEST 2: SELLER FINDBYDEPARTMENT--");
        Department department = new Department(2,null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list){
            System.out.println(obj);
        }

    }
}
