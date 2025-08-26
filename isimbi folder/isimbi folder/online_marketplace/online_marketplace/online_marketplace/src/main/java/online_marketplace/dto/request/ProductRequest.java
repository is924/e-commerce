package online_marketplace.dto.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Float price;
    private Integer quantityAvailable;
    private Long sellerId;

    
}
