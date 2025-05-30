package com.knf.dev.librarymanagementsystem.repository;

import com.knf.dev.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"OR LOWER(b.serialName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Book> search(String keyword);
}
