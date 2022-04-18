package com.lavanda.lavashop.dao;

import com.lavanda.lavashop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;


//<entity, primaryKey>
@CrossOrigin("http://localhost:4200")
public interface ProductRepository extends JpaRepository<Product, Long> {

//    spring query method, based on naming convention, behind the scene spring is doing "SELECT * FROM product where category_id={id}"
    Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable);
}
