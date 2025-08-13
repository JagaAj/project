package com.trio.Elitecar.controller;

import com.trio.Elitecar.model.Product;
import com.trio.Elitecar.model.User;
import com.trio.Elitecar.repository.UserRepository;
import com.trio.Elitecar.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private UserRepository userRepository;

//
//    @PostMapping("/auth/google")
//    public ResponseEntity<?> googleAuth(@RequestBody Map<String, String> data) {
//        String email = data.get("email");
//        String name = data.get("name");
//
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            // Register new user as regular user
//            user = new User();
//            user.setEmail(email);
//            user.setName(name);
//            user.setRole("USER");
//            userRepository.save(user);
//        }
//
//        return ResponseEntity.ok(Map.of(
//                "userId", user.getId(),
//                "name", user.getName(),
//                "role", user.getRole()
//        ));
//    }

    @GetMapping("/products")
    public List<Product> getAllProduct() {
        return service.getAllProduct();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Add new product with image
    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestPart("product") Product product,
            @RequestPart(required = false) MultipartFile imageFile
    )
    {
        try {
            Product savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Error saving product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update product with optional image
//    @PutMapping(value = "/product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> updateProduct(@PathVariable Long id,
//                                                @RequestPart Product product,
//                                                @RequestPart(required = false) MultipartFile imageFile)
//    {
//        try {
//            service.updateProduct(id, product, imageFile);
//            return ResponseEntity.ok("Product updated successfully");
//        } catch (Exception e) {
//            return new ResponseEntity<>("Update failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
    @PutMapping(value = "/product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestPart("product") Product product,
                                                @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            service.updateProduct(id, product, imageFile);
            return ResponseEntity.ok("Product updated successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Update failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
