package application;

import config.DatabaseConnection;
import mapping.dto.ProductDTO;
import model.Category;
import services.ProductService;
import services.impl.ProductServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main2 {
    public static void main(String[] args) {
        try(Connection conn = DatabaseConnection.getInstance()){

            ProductService productService = new ProductServiceImpl();
            productService.addPoroduct(new ProductDTO("test",234, LocalDateTime.now(), new Category(1,"muebles")));
            productService.listAllProducts().stream().forEach(System.out::println);
            /*Repository<Product> repository = new ProductRepositoryImpl();
            System.out.println("**** List products from database");
            repository.list().stream().forEach(System.out::println);
            Thread.sleep(3000);
            System.out.println("**** Get by Id: 1");
            System.out.println(repository.byId(1).toString());
            Thread.sleep(3000);

            System.out.println("**** Save product ");
            repository.save(new Product(0, "testClase2",2.00, LocalDateTime.now(),
                    new Category(1,"muebles")));
            repository.list().stream().forEach(System.out::println);
            Thread.sleep(3000);
            System.out.println("**** Delete product 13");

            repository.delete(13);
            repository.list().stream().forEach(System.out::println);*/

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
