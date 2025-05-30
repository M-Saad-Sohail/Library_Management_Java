package com.knf.dev.librarymanagementsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.entity.Category;
import com.knf.dev.librarymanagementsystem.entity.Publisher;
import com.knf.dev.librarymanagementsystem.entity.Role;
import com.knf.dev.librarymanagementsystem.entity.User;
import com.knf.dev.librarymanagementsystem.repository.UserRepository;
import com.knf.dev.librarymanagementsystem.service.BookService;

@SpringBootApplication
public class Application {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private BookService bookService;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				// parameterized constructor
				Book book = new Book("AP1287", "Harry Potter", "CXEF12389", "Book description");
				book.addAuthors(new Author("Matt", "dummy description"));
				book.addCategories(new Category("Dummy category"));
				book.addPublishers(new Publisher("Dummy publisher"));
				bookService.createBook(book);

				Book book1 = new Book("BP567#R", "The 100", "KCXEF12389", "Description1");
				book1.addAuthors(new Author("Maxwell", "Test description1"));
				book1.addCategories(new Category("New category"));
				book1.addPublishers(new Publisher("publisher2"));
				bookService.createBook(book1);

				Book book2 = new Book("GH67F#", "Breaking Bad", "UV#JH", "description2");
				book2.addAuthors(new Author("Josh Lang", "Test description2"));
				book2.addCategories(new Category("Spring category"));
				book2.addPublishers(new Publisher("publisher3"));
				bookService.createBook(book2);

				List<Role> roles = new ArrayList<>();
				roles.add(new Role("ROLE_ADMIN"));

				User user = new User("Saad", "Sohail", "saad@gmail.com", passwordEncoder.encode("Saad123"), roles);
				userRepository.save(user);
			}
		};
	}
}
