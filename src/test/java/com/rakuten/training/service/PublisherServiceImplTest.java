package com.rakuten.training.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.rakuten.training.dal.BookDAO;
import com.rakuten.training.dal.PublisherDAO;
import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;

public class PublisherServiceImplTest {

	@Test
	public void removePublisher_Removes_If_Publisher_Exists() {
		// Arrange
		PublisherServiceImpl service = new PublisherServiceImpl();
		Publisher toBeDeleted = new Publisher("test", "test");
		int id = 1;
		toBeDeleted.setId(id);
		PublisherDAO dao = Mockito.mock(PublisherDAO.class);
		service.setDao(dao);
		Mockito.when(dao.findById(id)).thenReturn(toBeDeleted);
		// Act
		service.removePublisher(id);
		// Assert
		Mockito.verify(dao).deleteById(id);
	}
	
	@Test(expected = NullPointerException.class)
	public void removePublisher_Throws_NullPointerexception_If_Publisher_Does_Not_Exist() {
		// Arrange
		PublisherServiceImpl service = new PublisherServiceImpl();
		PublisherDAO dao = Mockito.mock(PublisherDAO.class);
		service.setDao(dao);
		int id=1;
		Mockito.when(dao.findById(id)).thenReturn(null);
		// Act
		service.removePublisher(id);
		// Assert
		Mockito.verify(dao).findById(id);
	}
	
	@Test
	public void findById_Returns_Valid_Publisher_If_Publisher_Exists() {
		// Arrange
		PublisherServiceImpl service = new PublisherServiceImpl();
		Publisher found = new Publisher("test", "test");
		int id = 1;
		found.setId(id);
		PublisherDAO dao = Mockito.mock(PublisherDAO.class);
		service.setDao(dao);
		Mockito.when(dao.findById(id)).thenReturn(found);
		// Act
		service.findById(id);
		// Assert
		Mockito.verify(dao).findById(id);
	}
	
	@Test(expected = NullPointerException.class)
	public void findById_Throws_NullPointerexception_If_Publisher_Does_Not_Exist() {
		// Arrange
		PublisherServiceImpl service = new PublisherServiceImpl();
		PublisherDAO dao = Mockito.mock(PublisherDAO.class);
		service.setDao(dao);
		int id=1;
		Mockito.when(dao.findById(id)).thenReturn(null);
		// Act
		service.findById(id);
		// Assert
		Mockito.verify(dao).findById(id);
	}
	

}
