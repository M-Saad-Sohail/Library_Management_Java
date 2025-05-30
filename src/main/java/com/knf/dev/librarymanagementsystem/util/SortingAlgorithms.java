package com.knf.dev.librarymanagementsystem.util;

import com.knf.dev.librarymanagementsystem.entity.Book;

import java.util.List;

public class SortingAlgorithms {

    public static void bubbleSortByTitle(List<Book> books) {
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books.get(j).getName().compareToIgnoreCase(books.get(j + 1).getName()) > 0) {
                    // Swap
                    Book temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }
    }

    public static void selectionSortByTitle(List<Book> books) {
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


    public static void insertionSortByIdDescending(List<Book> books) {
        int n = books.size();
        for (int i = 1; i < n; i++) {
            Book key = books.get(i);
            int j = i - 1;

            // Descending order based on ID (Serial number)
            while (j >= 0 && books.get(j).getId() < key.getId()) {
                books.set(j + 1, books.get(j));
                j--;
            }
            books.set(j + 1, key);
        }
    }
}
