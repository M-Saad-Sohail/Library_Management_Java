package com.knf.dev.librarymanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.knf.dev.librarymanagementsystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Category> search(String keyword);

}
