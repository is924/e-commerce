package online_marketplace.services;

import lombok.RequiredArgsConstructor;
import online_marketplace.dto.request.OrderRequest;
import online_marketplace.dto.response.OrderResponse;
import online_marketplace.exception.ResourceNotFoundException;
import online_marketplace.model.Buyer;
import online_marketplace.model.Order;
import online_marketplace.model.Product;
import online_marketplace.repository.BuyerRepository;
import online_marketplace.repository.OrderRepository;
import online_marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final BuyerRepository buyerRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        Buyer user = buyerRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        if (product.getQuantityAvailable() < request.getQuantity()) {
            throw new IllegalArgumentException("Insufficient product quantity");
        }

        float totalPrice = product.getPrice() * request.getQuantity();

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        order.setOrderDate(new Date());
        order.setShippingAddress(request.getShippingAddress());
        order.setTotalPrice(totalPrice);

        product.setQuantityAvailable(product.getQuantityAvailable() - request.getQuantity());
        productRepository.save(product);

        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return mapToOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUser_UserId(userId).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        // response.setUserId(order.getUser() != null ? order.getUser().getUserId() : null);
        response.setProductId(order.getProduct() != null ? order.getProduct().getProductId() : null);
        response.setQuantity(order.getQuantity());
        if (order.getOrderDate() != null) {
            response.setOrderDate(order.getOrderDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        }
        response.setShippingAddress(order.getShippingAddress());
        response.setTotalPrice(order.getTotalPrice());
        return response;
    }
}
