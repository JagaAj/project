package com.trio.Elitecar.service;

import com.trio.Elitecar.model.Product;
import com.trio.Elitecar.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
        }
        return productRepo.save(product);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }

    public Product updateProduct(Long id, Product updatedProduct, MultipartFile imageFile) throws IOException {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update string fields
        if (updatedProduct.getName() != null) existingProduct.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) existingProduct.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getCategory() != null) existingProduct.setCategory(updatedProduct.getCategory());
        if (updatedProduct.getTransmission() != null) existingProduct.setTransmission(updatedProduct.getTransmission());
        if (updatedProduct.getFuel() != null) existingProduct.setFuel(updatedProduct.getFuel());
        if (updatedProduct.getAvailability() != null) existingProduct.setAvailability(updatedProduct.getAvailability());

        // Update numeric fields only if not default (optional: better use wrapper types in DTO)
        if (updatedProduct.getPrice() > 0) existingProduct.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getStock() > 0) existingProduct.setStock(updatedProduct.getStock());
        if (updatedProduct.getCapacity() > 0) existingProduct.setCapacity(updatedProduct.getCapacity());
        if (updatedProduct.getRating() > 0) existingProduct.setRating(updatedProduct.getRating());
        if (updatedProduct.getReviews() > 0) existingProduct.setReviews(updatedProduct.getReviews());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
            existingProduct.setImageData(imageFile.getBytes());
        }

        return productRepo.save(existingProduct);
    }
}
