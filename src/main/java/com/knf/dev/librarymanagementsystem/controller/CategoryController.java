package com.knf.dev.librarymanagementsystem.controller;

import java.util.List;

import com.knf.dev.librarymanagementsystem.util.SortingAlgorithms;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.knf.dev.librarymanagementsystem.entity.Category;
import com.knf.dev.librarymanagementsystem.service.CategoryService;

@Controller
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * 1) List ALL categories (no pagination).
	 * If "?sort=…" is present, apply in‐memory sort first.
	 *
	 * Examples:
	 * GET /categories → unsorted list
	 * GET /categories?sort=bubble → name A→Z
	 * GET /categories?sort=selection → name Z→A
	 * GET /categories?sort=insertion → ID Desc
	 */
	@RequestMapping("/categories")
	public String findAllCategories(
			@Param("sort") String sort,
			Model model) {

		// 1) fetch all
		List<Category> categories = categoryService.findAllCategories();

		// 2) apply in‐memory sort if requested
		if (sort != null) {
			switch (sort.toLowerCase()) {
				case "bubble":
					SortingAlgorithms.bubbleSortCategoriesByNameAsc(categories);
					break;
				case "selection":
					SortingAlgorithms.selectionSortCategoriesByNameDesc(categories);
					break;
				case "insertion":
					SortingAlgorithms.insertionSortCategoriesByIdDesc(categories);
					break;
				default:
					break; // unknown sort: leave as‐is
			}
		}

		model.addAttribute("categories", categories);
		model.addAttribute("currentSort", sort);
		return "list-categories";
	}

	/**
	 * 2) Search categories by keyword (no pagination).
	 * Searches only by "name" field. Adjust if you have other searchable fields.
	 *
	 * URL: /searchCategory?keyword=SomeName
	 */
	@RequestMapping("/searchCategory")
	public String searchCategory(@Param("keyword") String keyword, Model model) {
		// Expects CategoryService.searchCategories(keyword) returning List<Category>
		List<Category> matchingCategories = categoryService.searchCategories(keyword);

		model.addAttribute("categories", matchingCategories);
		model.addAttribute("keyword", keyword);
		return "list-categories";
	}

	/**
	 * 3) View a single category by ID
	 */
	@RequestMapping("/category/{id}")
	public String findCategoryById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", categoryService.findCategoryById(id));
		return "list-category";
	}

	/**
	 * 4) Show “Add Category” form
	 */
	@GetMapping("/addCategory")
	public String showCreateForm(Category category) {
		return "add-category";
	}

	/**
	 * 5) Handle form submission for creating a new category
	 */
	@RequestMapping("/add-category")
	public String createCategory(Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-category";
		}
		categoryService.createCategory(category);
		return "redirect:/categories";
	}

	/**
	 * 6) Show “Update Category” form
	 */
	@GetMapping("/updateCategory/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", categoryService.findCategoryById(id));
		return "update-category";
	}

	/**
	 * 7) Handle form submission for updating an existing category
	 */
	@RequestMapping("/update-category/{id}")
	public String updateCategory(
			@PathVariable("id") Long id,
			Category category,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			category.setId(id);
			return "update-category";
		}
		categoryService.updateCategory(category);
		return "redirect:/categories";
	}

	/**
	 * 8) Delete a category
	 */
	@RequestMapping("/remove-category/{id}")
	public String deleteCategory(@PathVariable("id") Long id, Model model) {
		categoryService.deleteCategory(id);
		return "redirect:/categories";
	}
}
