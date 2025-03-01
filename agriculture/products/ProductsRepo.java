package com.itvedant.agriculture.products;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<Products, Long> {

    // Custom method to find products by admin ID
    List<Products> findByAdminId(Long adminId);
}