package com.knf.dev.librarymanagementsystem.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = 100, nullable = false, unique = true)
	private String name;

	// Many-to-Many relationship with books
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "publishers")
	private Set<Book> books = new HashSet<>();

	// Default constructor
	public Publisher() {
	}

	// Constructor to initialize publisher with a name
	public Publisher(String name) {
		this.name = name;
	}

	// Getter and Setter for ID
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Getter and Setter for Name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getter and Setter for Books
	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
}
