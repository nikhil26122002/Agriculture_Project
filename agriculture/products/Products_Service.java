package com.itvedant.agriculture.products;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itvedant.agriculture.Role;
import com.itvedant.agriculture.SignUp;
import com.itvedant.agriculture.UserService;

@Service
public class Products_Service {

    @Autowired
    private ProductsRepo productRepository;

    @Autowired
    private UserService userService;  // to fetch admin (SignUp) from DB

    // 1. Add a new product
    public Products addProduct(Products product, Long adminId) {
        // Find the admin by adminId
        SignUp admin = userService.findById(adminId);
        if (admin == null || admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin not found or invalid role!");
        }
        // Set the product's admin
        product.setAdmin(admin);
        return productRepository.save(product);
    }

    // 2. Update an existing product
    public Products updateProduct(Long productId, Products updatedProduct, Long adminId) {
        // Check if the product belongs to the same admin
        Products existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!existingProduct.getAdmin().getId().equals(adminId)) {
            throw new RuntimeException("You are not the owner of this product!");
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(existingProduct);
    }

    // 3. Delete a product
    public void deleteProduct(Long productId, Long adminId) {
        Products existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Ensure that the same admin is deleting
        if (!existingProduct.getAdmin().getId().equals(adminId)) {
            throw new RuntimeException("You are not the owner of this product!");
        }

        productRepository.deleteById(productId);
    }

    // 4. View all products added by a specific admin
    public List<Products> getAllProductsByAdmin(Long adminId) {
        return productRepository.findByAdminId(adminId);
    }
}

