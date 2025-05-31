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

import com.knf.dev.librarymanagementsystem.entity.Publisher;
import com.knf.dev.librarymanagementsystem.service.PublisherService;

@Controller
public class PublisherController {

	private final PublisherService publisherService;

	public PublisherController(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	/**
	 * 1) List ALL publishers (no pagination).
	 * If "sort" query‐param is present, apply in‐memory sort first.
	 *
	 * URLs:
	 * • GET /publishers → unsorted list
	 * • GET /publishers?sort=bubble → name A→Z
	 * • GET /publishers?sort=selection → name Z→A
	 * • GET /publishers?sort=insertion → ID Desc
	 */
	@RequestMapping("/publishers")
	public String findAllPublishers(
			@Param("sort") String sort,
			Model model) {

		// 1) fetch all
		List<Publisher> publishers = publisherService.findAllPublishers();

		// 2) apply in‐memory sort if requested
		if (sort != null) {
			switch (sort.toLowerCase()) {
				case "bubble":
					SortingAlgorithms.bubbleSortPublishersByNameAsc(publishers);
					break;
				case "selection":
					SortingAlgorithms.selectionSortPublishersByNameDesc(publishers);
					break;
				case "insertion":
					SortingAlgorithms.insertionSortPublishersByIdDesc(publishers);
					break;
				default:
					// unknown sort → leave as‐is
					break;
			}
		}

		model.addAttribute("publishers", publishers);
		// keep the currentSort so Thymeleaf can highlight menu or re‐append in links if
		// needed
		model.addAttribute("currentSort", sort);
		return "list-publishers";
	}

	/**
	 * 2) Search publishers by keyword (no pagination).
	 *
	 * URL example: GET /searchPublisher?keyword=Pearson
	 */
	@RequestMapping("/searchPublisher")
	public String searchPublisher(@Param("keyword") String keyword, Model model) {
		// Expects PublisherService.searchPublishers(keyword) → List<Publisher>
		List<Publisher> matchingPublishers = publisherService.searchPublishers(keyword);

		model.addAttribute("publishers", matchingPublishers);
		model.addAttribute("keyword", keyword);
		return "list-publishers";
	}

	/**
	 * 3) View a single publisher by ID
	 */
	@RequestMapping("/publisher/{id}")
	public String findPublisherById(@PathVariable("id") Long id, Model model) {
		model.addAttribute("publisher", publisherService.findPublisherById(id));
		return "list-publisher";
	}

	/**
	 * 4) Show "Add Publisher" form
	 */
	@GetMapping("/addPublisher")
	public String showCreateForm(Publisher publisher) {
		return "add-publisher";
	}

	/**
	 * 5) Handle form submission for creating a new publisher
	 */
	@RequestMapping("/add-publisher")
	public String createPublisher(Publisher publisher, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-publisher";
		}
		publisherService.createPublisher(publisher);
		return "redirect:/publishers";
	}

	/**
	 * 6) Show "Update Publisher" form
	 */
	@GetMapping("/updatePublisher/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("publisher", publisherService.findPublisherById(id));
		return "update-publisher";
	}

	/**
	 * 7) Handle form submission for updating an existing publisher
	 */
	@RequestMapping("/update-publisher/{id}")
	public String updatePublisher(
			@PathVariable("id") Long id,
			Publisher publisher,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			publisher.setId(id);
			return "update-publisher";
		}
		publisherService.updatePublisher(publisher);
		return "redirect:/publishers";
	}

	/**
	 * 8) Delete a publisher
	 */
	@RequestMapping("/remove-publisher/{id}")
	public String deletePublisher(@PathVariable("id") Long id, Model model) {
		publisherService.deletePublisher(id);
		return "redirect:/publishers";
	}
}
