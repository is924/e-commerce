package online_marketplace.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private String description;
    private Float price;
    private Integer quantityAvailable;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
