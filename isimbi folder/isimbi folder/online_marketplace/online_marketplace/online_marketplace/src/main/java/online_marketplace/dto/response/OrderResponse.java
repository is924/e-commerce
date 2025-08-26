package online_marketplace.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDate orderDate;
    private String shippingAddress;
    private Float totalPrice;
}
