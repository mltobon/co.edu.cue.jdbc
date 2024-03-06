package mapping.mappers;

import mapping.dto.ProductDTO;
import model.Product;

public class ProductMapper {

    public static ProductDTO mapFromModel(Product product){
        return new ProductDTO(product.getName(), product.getPrice(),product.getRegistrationDate(),product.getCategory());
    }

    public static Product mapFromDTO(ProductDTO product){
        return Product.builder()
                .name(product.name())
                .price(product.price())
                .category(product.category())
                .registrationDate(product.registrationDate())
                .build();
    }
}
