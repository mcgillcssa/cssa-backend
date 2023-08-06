package ca.mcgillcssa.cssabackend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document(collection = "merchantCategories")
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "merchantType") // Ensure uniqueness based on merchantType
public class MerchantCategory {
  private String merchantType;
  private String merchantCategoryUrl;
}
