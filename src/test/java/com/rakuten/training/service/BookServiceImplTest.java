package com.rakuten.training.service;

import static org.junit.Assert.*;

import javax.validation.constraints.Null;

import org.junit.Test;
import org.mockito.Mockito;

import com.rakuten.training.dal.BookDAO;
import com.rakuten.training.dal.PublisherDAO;
import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;

public class BookServiceImplTest {

	@Test
	public void addNewBook_Returns_Book_With_Valid_Id_If_Created() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		Book toBeAdded = new Book("test", "test", 20, 2000);
		Book saved = new Book("test", "test", 20, 2000);
		int id = 1;
		saved.setId(id);
		Publisher p = new Publisher("test", "test");
		int publId = 1;
		p.setId(publId);
		PublisherDAO publDao = Mockito.mock(PublisherDAO.class);
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		service.setPublDao(publDao);
		Mockito.when(publDao.findById(1)).thenReturn(p);
		Mockito.when(bookdao.save(toBeAdded)).thenReturn(saved);
		// Act
		Book b = service.addNewBook(toBeAdded, publId);
		// Assert
		assertTrue(b.getId() == 1);
	}

	@Test
	public void addNewBook_Returns_Book_With_Valid_Id_If_TextBookHas_Less_Than_1000_Pages() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		Book toBeAdded = new Book("test", "textbook", 999, 2000);
		Book saved = new Book("test", "test", 999, 2000);
		int id = 1;
		saved.setId(id);
		Publisher p = new Publisher("test", "test");
		int publId = 1;
		p.setId(publId);
		PublisherDAO publDao = Mockito.mock(PublisherDAO.class);
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		service.setPublDao(publDao);
		Mockito.when(publDao.findById(1)).thenReturn(p);
		Mockito.when(bookdao.save(toBeAdded)).thenReturn(saved);
		// Act
		Book b = service.addNewBook(toBeAdded, publId);
		// Assert
		assertTrue(b.getId() == 1);
	}

	// Assert
	@Test(expected = IllegalArgumentException.class)
	public void addNewBook_Returns_IllegalArgumentException_IfViolationOf_BusinessLogic() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		Book toBeAdded = new Book("test", "textbook", 1500, 2000); // Note that textbook has more than 100 pages
		Book saved = new Book("test", "test", 1500, 2000);
		int id = 1;
		saved.setId(id);
		Publisher p = new Publisher("test", "test");
		int publId = 1;
		p.setId(publId);
		PublisherDAO publDao = Mockito.mock(PublisherDAO.class);
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		service.setPublDao(publDao);
		Mockito.when(publDao.findById(1)).thenReturn(p);
		// Act
		Book b = service.addNewBook(toBeAdded, publId);
	}

	// Assert
	@Test(expected = NullPointerException.class)
	public void addNewBook_Returns_NullPointerException_If_Publisher_Does_Not_Exist() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		Book toBeAdded = new Book("test", "textbook", 1500, 2000);
		PublisherDAO publDao = Mockito.mock(PublisherDAO.class);
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		service.setPublDao(publDao);
		Mockito.when(publDao.findById(1)).thenReturn(null);
		int publId = 1;
		// Act
		Book b = service.addNewBook(toBeAdded, publId);
	}

	@Test
	public void removeBook_Removes_If_Book_Exists() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		Book toBeDeleted = new Book("test", "test", 20, 2000);
		int id = 1;
		toBeDeleted.setId(id);
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		Mockito.when(bookdao.findById(1)).thenReturn(toBeDeleted);
		// Act
		service.removeBook(id);
		// Assert
		Mockito.verify(bookdao).deleteById(id);
	}

	// Assert
	@Test(expected = IllegalArgumentException.class)
	public void removeBook_Returns_IllegalArgumentException_If_Book_Is_A_Philosophy_Book() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		Book toBeDeleted = new Book("test", "philosophy", 1500, 2000); // Note that textbook has more than 100 pages
		int id = 1;
		toBeDeleted.setId(id);
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		Mockito.when(bookdao.findById(1)).thenReturn(toBeDeleted);
		// Act
		service.removeBook(id);
		// Assert
		Mockito.verify(bookdao).findById(id);
	}

	@Test(expected = NullPointerException.class)
	public void removeBook_Returns_NullPointerException_If_Book_Does_Not_Exist() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		int id = 1;
		Mockito.when(bookdao.findById(id)).thenReturn(null);
		// Act
		service.removeBook(id);
		// Assert
		Mockito.verify(bookdao).findById(id);
	}
	
	@Test(expected = NullPointerException.class)
	public void findBookById_Returns_NullPointerException_If_Book_Does_Not_Exist() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		int id = 1;
		Mockito.when(bookdao.findById(id)).thenReturn(null);
		// Act
		service.findById(id);
		// Assert
		Mockito.verify(bookdao).findById(id);
	}
	
	@Test
	public void findBookById_Returns_Book_If_Exists() {
		// Arrange
		BookServiceImpl service = new BookServiceImpl();
		BookDAO bookdao = Mockito.mock(BookDAO.class);
		service.setDao(bookdao);
		int id = 1;
		Book found = new Book("test", "test", 1500, 2000);
		found.setId(id);
		Mockito.when(bookdao.findById(id)).thenReturn(found);
		// Act
		service.findById(id);
		// Assert
		Mockito.verify(bookdao).findById(id);
		assertTrue(found.getId() == 1);
	}

}
