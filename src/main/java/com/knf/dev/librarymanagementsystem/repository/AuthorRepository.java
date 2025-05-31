package com.knf.dev.librarymanagementsystem.repository;

import com.knf.dev.librarymanagementsystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Find all authors whose name or description contains the given keyword
     * (case‐insensitive).
     */
    @Query("SELECT a " +
            "FROM Author a " +
            "WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Author> search(String keyword);
}
