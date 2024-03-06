package services.impl;

import mapping.dto.ProductDTO;
import mapping.mappers.ProductMapper;
import model.Product;
import repository.Repository;
import repository.impl.product.ProductRepositoryFilesImpl;
import repository.impl.product.ProductRepositoryJDBCImpl;
import services.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private Repository<Product> productRepository;

    public ProductServiceImpl() {
        this.productRepository = new ProductRepositoryJDBCImpl();
    }

    @Override
    public void addPoroduct(ProductDTO product) {
        productRepository.save(ProductMapper.mapFromDTO(product));
    }

    @Override
    public List<ProductDTO> listAllProducts() {
        return productRepository.list()
                .stream()
                .map(ProductMapper::mapFromModel)
                .toList();
    }
}
