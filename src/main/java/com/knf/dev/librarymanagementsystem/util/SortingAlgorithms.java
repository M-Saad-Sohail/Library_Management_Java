package com.knf.dev.librarymanagementsystem.util;

import com.knf.dev.librarymanagementsystem.entity.Book;
import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.entity.Publisher;
import com.knf.dev.librarymanagementsystem.entity.Category;

import java.util.List;

public class SortingAlgorithms {

    //
    // ──────────────── BOOK SORTING ────────────────
    //
    /** Sort books by title ascending (A → Z) using Bubble Sort. */
    public static void bubbleSortBooksByTitleAsc(List<Book> books) {
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books.get(j).getName().compareToIgnoreCase(books.get(j + 1).getName()) > 0) {
                    Book temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }
    }

    /** Sort books by title descending (Z → A) using Selection Sort. */
    public static void selectionSortBooksByTitleDesc(List<Book> books) {
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (books.get(j).getName().compareToIgnoreCase(books.get(maxIdx).getName()) > 0) {
                    maxIdx = j;
                }
            }
            Book temp = books.get(i);
            books.set(i, books.get(maxIdx));
            books.set(maxIdx, temp);
        }
    }

    /** Sort books by ID descending (highest → lowest) using Insertion Sort. */
    public static void insertionSortBooksByIdDesc(List<Book> books) {
        int n = books.size();
        for (int i = 1; i < n; i++) {
            Book key = books.get(i);
            int j = i - 1;
            while (j >= 0 && books.get(j).getId() < key.getId()) {
                books.set(j + 1, books.get(j));
                j--;
            }
            books.set(j + 1, key);
        }
    }

    //
    // ──────────────── AUTHOR SORTING ────────────────
    //
    /** Sort authors by name ascending (A → Z) using Bubble Sort. */
    public static void bubbleSortAuthorsByNameAsc(List<Author> authors) {
        int n = authors.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (authors.get(j).getName().compareToIgnoreCase(authors.get(j + 1).getName()) > 0) {
                    Author temp = authors.get(j);
                    authors.set(j, authors.get(j + 1));
                    authors.set(j + 1, temp);
                }
            }
        }
    }

    /** Sort authors by name descending (Z → A) using Selection Sort. */
    public static void selectionSortAuthorsByNameDesc(List<Author> authors) {
        int n = authors.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (authors.get(j).getName().compareToIgnoreCase(authors.get(maxIdx).getName()) > 0) {
                    maxIdx = j;
                }
            }
            Author temp = authors.get(i);
            authors.set(i, authors.get(maxIdx));
            authors.set(maxIdx, temp);
        }
    }

    /** Sort authors by ID descending (highest → lowest) using Insertion Sort. */
    public static void insertionSortAuthorsByIdDesc(List<Author> authors) {
        int n = authors.size();
        for (int i = 1; i < n; i++) {
            Author key = authors.get(i);
            int j = i - 1;
            while (j >= 0 && authors.get(j).getId() < key.getId()) {
                authors.set(j + 1, authors.get(j));
                j--;
            }
            authors.set(j + 1, key);
        }
    }

    //
    // ──────────────── PUBLISHER SORTING ────────────────
    //
    /** Sort publishers by name ascending (A → Z) using Bubble Sort. */
    public static void bubbleSortPublishersByNameAsc(List<Publisher> publishers) {
        int n = publishers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (publishers.get(j).getName().compareToIgnoreCase(publishers.get(j + 1).getName()) > 0) {
                    Publisher temp = publishers.get(j);
                    publishers.set(j, publishers.get(j + 1));
                    publishers.set(j + 1, temp);
                }
            }
        }
    }

    /** Sort publishers by name descending (Z → A) using Selection Sort. */
    public static void selectionSortPublishersByNameDesc(List<Publisher> publishers) {
        int n = publishers.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (publishers.get(j).getName().compareToIgnoreCase(publishers.get(maxIdx).getName()) > 0) {
                    maxIdx = j;
                }
            }
            Publisher temp = publishers.get(i);
            publishers.set(i, publishers.get(maxIdx));
            publishers.set(maxIdx, temp);
        }
    }

    /** Sort publishers by ID descending (highest → lowest) using Insertion Sort. */
    public static void insertionSortPublishersByIdDesc(List<Publisher> publishers) {
        int n = publishers.size();
        for (int i = 1; i < n; i++) {
            Publisher key = publishers.get(i);
            int j = i - 1;
            while (j >= 0 && publishers.get(j).getId() < key.getId()) {
                publishers.set(j + 1, publishers.get(j));
                j--;
            }
            publishers.set(j + 1, key);
        }
    }

    //
    // ──────────────── CATEGORY SORTING ────────────────
    //
    /** Sort categories by name ascending (A → Z) using Bubble Sort. */
    public static void bubbleSortCategoriesByNameAsc(List<Category> categories) {
        int n = categories.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (categories.get(j).getName().compareToIgnoreCase(categories.get(j + 1).getName()) > 0) {
                    Category temp = categories.get(j);
                    categories.set(j, categories.get(j + 1));
                    categories.set(j + 1, temp);
                }
            }
        }
    }

    /** Sort categories by name descending (Z → A) using Selection Sort. */
    public static void selectionSortCategoriesByNameDesc(List<Category> categories) {
        int n = categories.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (categories.get(j).getName().compareToIgnoreCase(categories.get(maxIdx).getName()) > 0) {
                    maxIdx = j;
                }
            }
            Category temp = categories.get(i);
            categories.set(i, categories.get(maxIdx));
            categories.set(maxIdx, temp);
        }
    }

    /** Sort categories by ID descending (highest → lowest) using Insertion Sort. */
    public static void insertionSortCategoriesByIdDesc(List<Category> categories) {
        int n = categories.size();
        for (int i = 1; i < n; i++) {
            Category key = categories.get(i);
            int j = i - 1;
            while (j >= 0 && categories.get(j).getId() < key.getId()) {
                categories.set(j + 1, categories.get(j));
                j--;
            }
            categories.set(j + 1, key);
        }
    }

}
