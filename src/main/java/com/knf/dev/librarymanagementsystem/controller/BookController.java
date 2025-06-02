package com.knf.dev.librarymanagementsystem.controller;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.knf.dev.librarymanagementsystem.util.SortingAlgorithms;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.service.AuthorService;
import com.knf.dev.librarymanagementsystem.service.BookService;
import com.knf.dev.librarymanagementsystem.service.CategoryService;
import com.knf.dev.librarymanagementsystem.service.PublisherService;

@Controller
@SessionAttributes("recentlyViewed")
public class BookController {

	private final BookService bookService;
	private final AuthorService authorService;
	private final CategoryService categoryService;
	private final PublisherService publisherService;

	public BookController(PublisherService publisherService, CategoryService categoryService, BookService bookService,
			AuthorService authorService) {
		this.authorService = authorService;
		this.bookService = bookService;
		this.categoryService = categoryService;
		this.publisherService = publisherService;
	}

	@RequestMapping({ "/books", "/" })
	public String findAllBooks(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);

		var bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("books", bookPage);

		var totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "list-books";
	}

	@RequestMapping("/searchBook")
	public String searchBook(@Param("keyword") String keyword, Model model) {

		model.addAttribute("books", bookService.searchBooks(keyword));
		model.addAttribute("keyword", keyword);
		return "list-books";
	}

	@GetMapping("/books")
	public String viewBooks(@RequestParam(required = false) String sort, Model model) {
		List<Book> books = bookService.findAllBooks();

		// Apply sorting based on parameter
		if ("bubble".equalsIgnoreCase(sort)) {
			SortingAlgorithms.bubbleSortBooksByTitleAsc(books);
		} else if ("selection".equalsIgnoreCase(sort)) {
			SortingAlgorithms.selectionSortBooksByTitleDesc(books);
		} else if ("insertion".equalsIgnoreCase(sort)) {
			SortingAlgorithms.insertionSortBooksByIdDesc(books);
		}

		model.addAttribute("books", books);
		return "list-books"; // your Thymeleaf page name
	}

	@ModelAttribute("recentlyViewed")
	public Stack<Book> recentlyViewed() {
		return new Stack<>();
	}

	@RequestMapping("/book/{id}")
	public String findBookById(@PathVariable("id") Long id, Model model,
			@ModelAttribute("recentlyViewed") Stack<Book> recentlyViewed) {
		Book book = bookService.findBookById(id);
		model.addAttribute("book", book);

		// Avoid duplicates
		recentlyViewed.removeIf(b -> b.getId().equals(book.getId()));
		recentlyViewed.push(book);

		// Limit to last 5
		while (recentlyViewed.size() > 5) {
			recentlyViewed.remove(0);
		}

		return "list-book";
	}

	@GetMapping("/recently-viewed")
	public String showRecentlyViewedBooks(@ModelAttribute("recentlyViewed") Stack<Book> recentlyViewed,
			Model model) {
		model.addAttribute("recentBooks", recentlyViewed);
		return "recent-books"; // New .html file
	}

	@RequestMapping("/remove-recently-viewed/{id}")
	public String removeRecentlyViewed(@PathVariable("id") Long id,
			@ModelAttribute("recentlyViewed") Stack<Book> recentlyViewed) {
		recentlyViewed.removeIf(book -> book.getId().equals(id));
		return "redirect:/recently-viewed";
	}

	@GetMapping("/add")
	public String showCreateForm(Book book, Model model) {

		model.addAttribute("categories", categoryService.findAllCategories());
		model.addAttribute("authors", authorService.findAllAuthors());
		model.addAttribute("publishers", publisherService.findAllPublishers());
		return "add-book";
	}

	@RequestMapping("/add-book")
	public String createBook(Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-book";
		}

		bookService.createBook(book);
		model.addAttribute("book", bookService.findAllBooks());
		return "redirect:/books";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("book", bookService.findBookById(id));
		return "update-book";
	}

	@RequestMapping("/update-book/{id}")
	public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			book.setId(id);
			return "update-book";
		}

		bookService.updateBook(book);
		model.addAttribute("book", bookService.findAllBooks());
		return "redirect:/books";
	}

	@RequestMapping("/remove-book/{id}")
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		bookService.deleteBook(id);

		model.addAttribute("book", bookService.findAllBooks());
		return "redirect:/books";
	}

}
