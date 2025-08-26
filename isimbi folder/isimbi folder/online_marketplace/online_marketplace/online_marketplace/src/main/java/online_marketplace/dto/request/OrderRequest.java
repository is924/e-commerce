package online_marketplace.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OrderRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String shippingAddress;
    // Removed orderDate and totalPrice — server will set these
}

