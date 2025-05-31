package com.knf.dev.librarymanagementsystem.service;

import java.util.List;

import com.knf.dev.librarymanagementsystem.entity.Category;

public interface CategoryService {

	public List<Category> findAllCategories();

	List<Category> searchCategories(String keyword);

	public Category findCategoryById(Long id);

	public void createCategory(Category category);

	public void updateCategory(Category category);

	public void deleteCategory(Long id);

}
