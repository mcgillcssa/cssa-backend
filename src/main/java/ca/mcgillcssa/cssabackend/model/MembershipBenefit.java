package ca.mcgillcssa.cssabackend.model;


import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Document(collection = "benefits")
@Data
@AllArgsConstructor
@ToString
public class MembershipBenefit {
    @Id
    private String merchantName;
    private String merchantBackgroundImageUrl;
    private String merchantDiscount;
    private String merchantLogoUrl;
    private MerchantType merchantType;

    public enum MerchantType {
        RESTAURANT("主食"),
        SWEETS("甜品饮品"),
        SHOPPING("购物"),
        BEAUTY("美甲美发"),
        OTHER("其他");
        private final String value;
        MerchantType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
