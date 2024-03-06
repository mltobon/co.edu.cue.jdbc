package repository.impl.product;

import model.Product;
import repository.Repository;

import java.util.List;

public class ProductRepositoryFilesImpl implements Repository<Product> {
    @Override
    public List<Product> list() {
        System.out.println("listando desde archivos");
        return null;
    }

    @Override
    public Product byId(int id) {
        return null;
    }

    @Override
    public void save(Product product) {
        System.out.println("Estoy llamando implementacion de archivos");
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Product product) {

    }
}
