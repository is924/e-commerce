package online_marketplace.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private String companyName;
    private String contactInfo;
    private Float averageRating;
}
