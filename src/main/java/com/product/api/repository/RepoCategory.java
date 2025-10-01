package com.product.api.repository;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoCategory extends JpaRepository<Category, Integer> {

    @Query(value ="SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> getCategory();


}
