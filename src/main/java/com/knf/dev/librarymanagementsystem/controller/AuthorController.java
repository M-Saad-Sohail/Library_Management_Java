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
     * 1) List all authors (paginated) at “/authors” (or “/”)
     *    • Optional query params: “page” and “size”
     */
    @RequestMapping({ "/authors", "/" })
    public String findAllAuthors(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        // Use the service’s pagination method
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
     * 2) Search authors by keyword at “/searchAuthor”
     *    • Looks for “keyword” in name or description (case‐insensitive).
     */
    @RequestMapping("/searchAuthor")
    public String searchAuthor(@Param("keyword") String keyword, Model model) {
        // This requires a method in AuthorService:
        //   public List<Author> searchAuthors(String keyword)
        List<Author> matchingAuthors = authorService.searchAuthors(keyword);

        model.addAttribute("authors", matchingAuthors);
        model.addAttribute("keyword", keyword);
        return "list-authors";
    }

    /**
     * 3) Sort authors in‐memory (no pagination) at “/authors?sort=…”
     *    • Uses the same “bubble”, “selection”, “insertion” params as your BookController.
     *    • Returns a List<Author> (unsliced), binding directly to “authors”.
     */
    @GetMapping("/authors")
    public String viewAuthors(@RequestParam(required = false) String sort, Model model) {
        List<Author> authors = authorService.findAllAuthors();

        if ("bubble".equalsIgnoreCase(sort)) {
            SortingAlgorithms.bubbleSortAuthorsByNameAsc(authors);
        } else if ("selection".equalsIgnoreCase(sort)) {
            SortingAlgorithms.selectionSortAuthorsByNameDesc(authors);
        } else if ("insertion".equalsIgnoreCase(sort)) {
            SortingAlgorithms.insertionSortAuthorsByIdDesc(authors);
        }

        model.addAttribute("authors", authors);
        return "list-authors";
    }

    /**
     * 4) View a single author by ID
     */
    @RequestMapping("/author/{id}")
    public String findAuthorById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.findAuthorById(id));
        return "list-author";
    }

    /**
     * 5) Show “Add Author” form
     */
    @GetMapping("/addAuthor")
    public String showCreateForm(Author author) {
        return "add-author";
    }

    /**
     * 6) Handle form submission for creating a new author
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
     * 7) Show “Update Author” form
     */
    @GetMapping("/updateAuthor/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", authorService.findAuthorById(id));
        return "update-author";
    }

    /**
     * 8) Handle form submission for updating an existing author
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
     * 9) Delete an author
     */
    @RequestMapping("/remove-author/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, Model model) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
