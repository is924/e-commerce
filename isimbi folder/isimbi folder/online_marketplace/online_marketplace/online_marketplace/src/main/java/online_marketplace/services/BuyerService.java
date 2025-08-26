package online_marketplace.services;

import lombok.RequiredArgsConstructor;
import online_marketplace.dto.request.BuyerRequest;
import online_marketplace.model.Buyer;
import online_marketplace.model.User;
import online_marketplace.repository.BuyerRepository;
import online_marketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerService {
    
    private final BuyerRepository buyerRepository;
    private final UserRepository userRepository;

    public Buyer createBuyer(BuyerRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
               
        if (user.getAccountType() != User.AccountType.BUYER) {
            throw new IllegalArgumentException("User must have account type BUYER");
        }
        
        Buyer buyer = new Buyer();
        buyer.setUser(user);
        buyer.setContactInfo(request.getContactInfo());
        buyer.setPreferredPaymentMethod(request.getPreferredPaymentMethod());
        return buyerRepository.save(buyer);
    }

    public Buyer getBuyerById(Long id) {
        return buyerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found with id: " + id));
    }
    
    public Buyer getBuyerByUserId(Long userId) {
        return buyerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found for user id: " + userId));
    }

    public java.util.List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    public Buyer updateBuyer(Long id, BuyerRequest request) {
        Buyer buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found with id: " + id));
        
        if (request.getContactInfo() != null)
            buyer.setContactInfo(request.getContactInfo());
        if (request.getPreferredPaymentMethod() != null)
            buyer.setPreferredPaymentMethod(request.getPreferredPaymentMethod());
        
        return buyerRepository.save(buyer);
    }

    public void deleteBuyer(Long id) {
        if (!buyerRepository.existsById(id)) {
            throw new IllegalArgumentException("Buyer not found with id: " + id);
        }
        buyerRepository.deleteById(id);
    }
}