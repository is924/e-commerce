package online_marketplace.dto.request;

import lombok.Data;

@Data
public class SellerRequest {
    private Long userId;
    private String companyName;
    private String contactInfo;
    private Float averageRating;
}
