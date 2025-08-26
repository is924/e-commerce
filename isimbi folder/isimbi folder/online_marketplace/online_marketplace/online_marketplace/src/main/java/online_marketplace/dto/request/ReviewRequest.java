package online_marketplace.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long productId;
    private int rating;
    private String comments;
}