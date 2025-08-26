package online_marketplace.dto.response;

import lombok.Data;

@Data
public class ReviewResponse {
    private Long reviewId;
    private Long userId;
    private Long productId;
    private int rating;
    private String comments;
}
