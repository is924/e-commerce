package online_marketplace.repository;

import online_marketplace.model.Seller;
import online_marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    boolean existsByUser(User user);
    
    // Optional: You might also want this method for finding sellers by user
    java.util.Optional<Seller> findByUser(User user);
}