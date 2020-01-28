package com.rakuten.training.dal;

import java.util.List;

import com.rakuten.training.domain.Book;

public interface BookDAO {

	Book save(Book toBesaved);

	Book findById(int id);

	List<Book> findAll();

	void deleteById(int id);
}
