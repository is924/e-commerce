package online_marketplace.services;

import lombok.RequiredArgsConstructor;
import online_marketplace.dto.request.SellerRequest;
import online_marketplace.model.Seller;
import online_marketplace.model.User;
import online_marketplace.repository.SellerRepository;
import online_marketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;

    public Seller createSeller(SellerRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        if (user.getAccountType() != User.AccountType.SELLER) {
            throw new IllegalArgumentException("User must have account type SELLER");
        }

        Seller seller = new Seller();
        seller.setUser(user);
        seller.setCompanyName(request.getCompanyName());
        seller.setContactInfo(request.getContactInfo());

        Float rating = request.getAverageRating() != null ? request.getAverageRating() : 0.0f;
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Average rating must be between 0 and 5");
        }
        seller.setAverageRating(rating);

        return sellerRepository.save(seller);
    }

    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with ID: " + id));
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Seller updateSeller(Long id, SellerRequest request) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with ID: " + id));

        if (request.getCompanyName() != null && !request.getCompanyName().isBlank()) {
            seller.setCompanyName(request.getCompanyName().trim());
        }

        if (request.getContactInfo() != null && !request.getContactInfo().isBlank()) {
            seller.setContactInfo(request.getContactInfo().trim());
        }

        if (request.getAverageRating() != null) {
            if (request.getAverageRating() < 0 || request.getAverageRating() > 5) {
                throw new IllegalArgumentException("Average rating must be between 0 and 5");
            }
            seller.setAverageRating(request.getAverageRating());
        }

        return sellerRepository.save(seller);
    }

    public void deleteSeller(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new IllegalArgumentException("Seller not found with ID: " + id);
        }
        sellerRepository.deleteById(id);
    }
}
