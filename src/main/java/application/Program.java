package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("--TEST 1: SELLER FINDBYID--");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n--TEST 2: SELLER FINDBYDEPARTMENT--");
        Department department = new Department(2,null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list){
            System.out.println(obj);
        }

        System.out.println("\n--TEST 3: SELLER FINDALL--");

         list = sellerDao.findAll();
        for (Seller obj : list){
            System.out.println(obj);
        }
        System.out.println("\n--TEST 4: SELLER INSERT--");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com",new Date(),4000.0,department);
        sellerDao.insert(newSeller);
        System.out.println("Inserido! new id = "+ newSeller.getId());

        System.out.println("\n--TEST 5: SELLER UPDATE--");
        seller = sellerDao.findById(1);
        seller.setName("Juia");
        sellerDao.update(seller);
        System.out.println("Atualizacao concluida!");

        System.out.println("\n--TEST 6: SELLER DELETE--");
        System.out.println("Insira o ID que queira deletar: ");
        int id = scanner.nextInt();
        sellerDao.deleteById(id);
        System.out.println("ID DELETADO COM SUCESSO!");
        scanner.close();

    }
}
