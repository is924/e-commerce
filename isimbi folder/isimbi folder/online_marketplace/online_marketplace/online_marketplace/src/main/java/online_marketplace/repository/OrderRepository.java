package online_marketplace.repository;

import online_marketplace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.user.userId = :userId")
    List<Order> findByUser_UserId(Long userId);
}
