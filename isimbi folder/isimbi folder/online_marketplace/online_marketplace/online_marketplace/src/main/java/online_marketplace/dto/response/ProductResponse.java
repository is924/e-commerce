package online_marketplace.dto.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long productId;
    private String name;
    private String description;
    private Float price;
    private Integer quantityAvailable;
    private Long sellerId;
}
