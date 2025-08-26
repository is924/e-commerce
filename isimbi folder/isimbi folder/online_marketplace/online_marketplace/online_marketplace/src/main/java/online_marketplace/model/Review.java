package online_marketplace.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Integer rating;
    private String comments;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")  // ✅ Reference userId
    private User user;  // ✅ Use User, not Buyer

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")  // ✅ Reference productId
    private Product product;
}
