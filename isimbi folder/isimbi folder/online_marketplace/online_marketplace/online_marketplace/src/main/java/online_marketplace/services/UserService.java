package online_marketplace.services;

import lombok.RequiredArgsConstructor;
import online_marketplace.dto.request.UserRequest;
import online_marketplace.model.User;
import online_marketplace.model.Buyer;
import online_marketplace.model.Seller;
import online_marketplace.repository.UserRepository;
import online_marketplace.repository.BuyerRepository;
import online_marketplace.repository.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public User createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        try {
            user.setAccountType(User.AccountType.valueOf(request.getAccountType().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid account type: " + request.getAccountType());
        }

        User savedUser = userRepository.save(user);

        // if (savedUser.getAccountType() == User.AccountType.BUYER) {
        //     Buyer buyer = new Buyer();
        //     buyer.setUser(savedUser);
        //     buyer.setContactInfo(request.getContactInfo());
        //     buyer.setPreferredPaymentMethod(request.getPreferredPaymentMethod());
        //     buyerRepository.save(buyer);
        // } else if (savedUser.getAccountType() == User.AccountType.SELLER) {
        //     Seller seller = new Seller();
        //     seller.setUser(savedUser);
        //     seller.setCompanyName(request.getCompanyName());
        //     seller.setContactInfo(request.getContactInfo());
        //     sellerRepository.save(seller);
        // }

        return savedUser;
    }
}
