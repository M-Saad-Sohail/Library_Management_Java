package com.knf.dev.librarymanagementsystem.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.knf.dev.librarymanagementsystem.util.SortingAlgorithms;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.service.AuthorService;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * List all authors at "/authors".
     * If "sort" parameter is present, do an in‐memory sort (no pagination).
     * Otherwise, use paginated results (page + size).
     *
     * Examples:
     * GET /authors → paginated list (default)
     * GET /authors?sort=bubble → in-memory sort (A→Z), no pagination
     * GET /authors?sort=selection → in-memory sort (Z→A), no pagination
     * GET /authors?sort=insertion → in-memory sort (ID desc), no pagination
     * GET /authors?page=2&size=10 → paginated page 2, 10 items/page
     */
    @RequestMapping("/authors")
    public String findAllAuthors(
            Model model,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", required = false) Optional<Integer> page,
            @RequestParam(value = "size", required = false) Optional<Integer> size) {

        // If "sort" is provided, perform an in-memory sort (no pagination)
        if (sort != null && !sort.isBlank()) {
            List<Author> allAuthors = authorService.findAllAuthors();

            if ("bubble".equalsIgnoreCase(sort)) {
                SortingAlgorithms.bubbleSortAuthorsByNameAsc(allAuthors);
            } else if ("selection".equalsIgnoreCase(sort)) {
                SortingAlgorithms.selectionSortAuthorsByNameDesc(allAuthors);
            } else if ("insertion".equalsIgnoreCase(sort)) {
                SortingAlgorithms.insertionSortAuthorsByIdDesc(allAuthors);
            }

            model.addAttribute("authors", allAuthors);
            model.addAttribute("currentSort", sort);
            return "list-authors";
        }

        // Otherwise, show paginated results
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        var authorPage = authorService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("authors", authorPage);

        int totalPages = authorPage.getTotalPages();
        if (totalPages > 0) {
            var pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "list-authors";
    }

    /**
     * Search authors by keyword at "/searchAuthor"
     */
    @RequestMapping("/searchAuthor")
    public String searchAuthor(@Param("keyword") String keyword, Model model) {
        List<Author> matchingAuthors = authorService.searchAuthors(keyword);

        model.addAttribute("authors", matchingAuthors);
        model.addAttribute("keyword", keyword);
        return "list-authors";
    }

    /**
     * View a single author by ID
     */
    @RequestMapping("/author/{id}")
    public String findAuthorById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.findAuthorById(id));
        return "list-author";
    }

    /**
     * Show “Add Author” form
     */
    @GetMapping("/addAuthor")
    public String showCreateForm(Author author) {
        return "add-author";
    }

    /**
     * Handle form submission for creating a new author
     */
    @RequestMapping("/add-author")
    public String createAuthor(Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-author";
        }
        authorService.createAuthor(author);
        return "redirect:/authors";
    }

    /**
     * Show “Update Author” form
     */
    @GetMapping("/updateAuthor/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.findAuthorById(id));
        return "update-author";
    }

    /**
     * Handle form submission for updating an existing author
     */
    @RequestMapping("/update-author/{id}")
    public String updateAuthor(
            @PathVariable("id") Long id,
            Author author,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            author.setId(id);
            return "update-author";
        }
        authorService.updateAuthor(author);
        return "redirect:/authors";
    }

    /**
     * Delete an author
     */
    @RequestMapping("/remove-author/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, Model model) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
