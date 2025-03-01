package com.itvedant.agriculture.products;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductsController {

    @Autowired
    private Products_Service productService;

    // 1. Add a product
    @PreAuthorize("hasRole('ADMIN')") 
    @PostMapping("/add/{adminId}")
    public ResponseEntity<?> addProduct(@RequestBody Products product, @PathVariable  Long adminId) {
        try {
            Products savedProduct = productService.addProduct(product, adminId);
            return ResponseEntity.ok(savedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Update a product
    @PutMapping("/update/{adminId}/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long adminId,
            @PathVariable Long productId,
            @RequestBody Products updatedProduct) {
        try {
            Products product = productService.updateProduct(productId, updatedProduct, adminId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Delete a product
    @DeleteMapping("/delete/{adminId}/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long adminId, @PathVariable Long productId) {
        try {
            productService.deleteProduct(productId, adminId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. Get all products for an admin
    @GetMapping("/admin/{adminId}")
    public List<Products> getAllProductsByAdmin(@PathVariable Long adminId) {
        return productService.getAllProductsByAdmin(adminId);
    }
}
