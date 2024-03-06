package services;

import mapping.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    void addPoroduct(ProductDTO product);
    List<ProductDTO> listAllProducts();
}
