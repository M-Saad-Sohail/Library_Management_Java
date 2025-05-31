package com.knf.dev.librarymanagementsystem.repository;

import com.knf.dev.librarymanagementsystem.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * Search publishers by name (case-insensitive).
     * (Remove any reference to a non-existent "description" property.)
     */
    @Query("SELECT p " +
            "FROM Publisher p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Publisher> search(String keyword);
}
