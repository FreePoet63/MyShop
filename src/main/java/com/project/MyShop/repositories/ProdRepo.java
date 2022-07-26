package com.project.MyShop.repositories;

import com.project.MyShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdRepo extends JpaRepository<Product, Long> {
    List<Product> findByTitle(String title);
}
