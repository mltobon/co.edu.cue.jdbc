package mapping.dto;

import lombok.Builder;
import model.Category;

import java.time.LocalDateTime;
@Builder
public record ProductDTO(String name, double price, LocalDateTime registrationDate, Category category) {
}
