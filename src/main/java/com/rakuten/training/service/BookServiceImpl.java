package com.rakuten.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.dal.BookDAO;
import com.rakuten.training.dal.PublisherDAO;
import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	BookDAO dao;
	PublisherDAO publDao;

	@Autowired
	public void setDao(BookDAO dao) {
		this.dao = dao;
	}

	@Override
	public Book addNewBook(Book toBeAdded, int publisherId) {
		Publisher p= publDao.findById(publisherId);
		if(p != null)
		{
			if (toBeAdded.getGenre().equalsIgnoreCase("textbook"))
				if (toBeAdded.getNumOfPages() > 1000)
					throw new IllegalArgumentException("Cannot add a textbook with more than thousand pages!");
			toBeAdded.setPublisher(p);
			Book added = dao.save(toBeAdded);
			return added;
		}
		else
		{
			throw new NullPointerException("Publisher does not exist");
		}
		
	}

	@Override
	public void removeBook(int id) {
		Book existingBook = dao.findById(id);
		if (existingBook != null) {
			if (existingBook.getGenre().equalsIgnoreCase("philosophy"))
				throw new IllegalArgumentException("Cannot delete philosophy books!!!");
			else
				dao.deleteById(existingBook.getId());
		}
		else {
			throw new NullPointerException("Book does not exist");
		}
			
	}

	@Override
	public Book findById(int id) {
		Book b = dao.findById(id);
		if(b != null)
		{
			return b;
		}
		else
		{
			throw new NullPointerException("Book does not exist");
		}
	}

	@Override
	public List<Book> findAll() {
		return dao.findAll();
	}

	@Autowired
	public void setPublDao(PublisherDAO publDao) {
		this.publDao = publDao;
	}

}
