package online_marketplace.controller;

import lombok.RequiredArgsConstructor;
import online_marketplace.dto.request.BuyerRequest;
import online_marketplace.model.Buyer;
import online_marketplace.services.BuyerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyers")
@RequiredArgsConstructor
public class BuyerController {
    private final BuyerService buyerService;

    @PostMapping("/{userId}")
    public Buyer createBuyer(@PathVariable Long userId, @RequestBody BuyerRequest request) {
        return buyerService.createBuyer(request, userId);
    }

    @GetMapping("/{id}")
    public Buyer getBuyer(@PathVariable Long id) {
        return buyerService.getBuyerById(id);
    }
    
    @GetMapping("/user/{userId}")
    public Buyer getBuyerByUserId(@PathVariable Long userId) {
        return buyerService.getBuyerByUserId(userId);
    }

    @GetMapping
    public List<Buyer> getAllBuyers() {
        return buyerService.getAllBuyers();
    }

    @PutMapping("/{id}")
    public Buyer updateBuyer(@PathVariable Long id, @RequestBody BuyerRequest request) {
        return buyerService.updateBuyer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBuyer(@PathVariable Long id) {
        buyerService.deleteBuyer(id);
    }
}