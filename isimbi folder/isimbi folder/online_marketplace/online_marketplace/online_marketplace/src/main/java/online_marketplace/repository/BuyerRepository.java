package online_marketplace.repository;

import online_marketplace.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByUser_UserId(Long userId);
    boolean existsByUser_UserId(Long userId);
}