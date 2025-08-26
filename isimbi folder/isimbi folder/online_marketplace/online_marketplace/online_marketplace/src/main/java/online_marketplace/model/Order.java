package online_marketplace.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Integer quantity;
    private Date orderDate;
    private String shippingAddress;
    private Float totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Buyer user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
