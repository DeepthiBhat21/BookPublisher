package com.rakuten.training.service;

import java.util.List;

import com.rakuten.training.domain.Book;

public interface BookService {

	Book addNewBook(Book toBeAdded, int publisherId);

	void removeBook(int id);

	Book findById(int id);

	List<Book> findAll();
}
