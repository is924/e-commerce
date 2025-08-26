package online_marketplace.services;

import lombok.RequiredArgsConstructor;
import online_marketplace.dto.request.ProductRequest;
import online_marketplace.model.Product;
import online_marketplace.model.Seller;
import online_marketplace.repository.ProductRepository;
import online_marketplace.repository.SellerRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    public Product createProduct(ProductRequest request) {
        Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with id: " + request.getSellerId()));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantityAvailable(request.getQuantityAvailable());
        product.setSeller(seller);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

