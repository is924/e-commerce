package online_marketplace.controller;

import lombok.RequiredArgsConstructor;
import online_marketplace.dto.request.OrderRequest;
import online_marketplace.dto.response.OrderResponse;
import online_marketplace.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Get all orders
    @GetMapping("/all")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get orders by user
    @GetMapping
    public List<OrderResponse> getAllOrdersByUser(@RequestParam Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    // Place a new order
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Delete order (to be implemented in service)
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        // TODO: Implement delete logic in OrderService
    }
}
