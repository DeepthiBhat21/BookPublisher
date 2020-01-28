package com.rakuten.training.web;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;
import com.rakuten.training.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest({ BookController.class })
public class BookControllerUnitTest {
	
	MockMvc mockMvc;

	@Autowired
	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	@MockBean
	BookService bookService;

	@Test
	public void getBookById_Returns_Ok_WithValue_If_Book_Exists() throws Exception {
		// Arrange
		Book found = new Book("test", "test",1234,1234);
		int id = 1;
		found.setId(id);
		Mockito.when(bookService.findById(id)).thenReturn(found);
		// Act//Assert
		mockMvc.perform(get("/api/books/{id}", id)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)));
		Mockito.verify(bookService).findById(id);
	}
	
	@Test
	public void getBookById_Returns_NotFound_If_Book_Does_Not_Exists() throws Exception {
		// Arrange
		int id = 1;
		Mockito.when(bookService.findById(id)).thenThrow(new NullPointerException());
		// Act//Assert
		mockMvc.perform(get("/api/books/{id}", id)).andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(bookService).findById(id);
	}
	
	@Test
	public void addNewBook_Returns_Created_If_Book_Is_Created() throws Exception {
		// Arrange
		Book toBeAdded = new Book("test", "test",1234,123);
		Publisher p = new Publisher("test","test");
		int id = 1;
		int publisherId =1;
		p.setId(publisherId);
		toBeAdded.setId(id);
		toBeAdded.setPublisher(p);
		Mockito.when(bookService.addNewBook(Mockito.any(Book.class),Mockito.eq(publisherId))).thenReturn(toBeAdded);
		// Act//Assert
		mockMvc.perform(post("/api/books/{publisherId}",publisherId).contentType(MediaType.APPLICATION_JSON)
				.content(objToJson(toBeAdded))).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/api/publisher/"+publisherId+"/books/"+id));
		Mockito.verify(bookService).addNewBook(Mockito.any(Book.class),Mockito.eq(publisherId));
	}
	
	@Test
	public void addNewBook_Returns_BadRequest_If_ItDoesNotAdhereTo_BusinessRequirements() throws Exception {
		// Arrange
		Book toBeAdded = new Book("test", "test",1234,123);
		int id = 1;
		int publisherId =1;
		Mockito.when(bookService.addNewBook(Mockito.any(Book.class),Mockito.eq(publisherId))).thenThrow(new IllegalArgumentException());
		// Act//Assertoc
		mockMvc.perform(post("/api/books/{publisherId}",publisherId).contentType(MediaType.APPLICATION_JSON)
				.content(objToJson(toBeAdded))).andExpect(MockMvcResultMatchers.status().isBadRequest());
		Mockito.verify(bookService).addNewBook(Mockito.any(Book.class),Mockito.eq(publisherId));
	}
	
	@Test
	public void addNewBook_Returns_NotFound_If_Publisher_Is_Not_Found() throws Exception {
		// Arrange
		Book toBeAdded = new Book("test", "test",1234,123);
		int id = 1;
		int publisherId =1;
		Mockito.when(bookService.addNewBook(Mockito.any(Book.class),Mockito.eq(publisherId))).thenThrow(new NullPointerException());
		// Act//Assertoc
		mockMvc.perform(post("/api/books/{publisherId}",publisherId).contentType(MediaType.APPLICATION_JSON)
				.content(objToJson(toBeAdded))).andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(bookService).addNewBook(Mockito.any(Book.class),Mockito.eq(publisherId));
	}
	
	@Test
	public void deleteBook_Returns_NoContent_If_Book_Is_Deleted() throws Exception {
		// Arrange
		int id = 1;
		Mockito.doNothing().when(bookService).removeBook(id);
		// Act//Assertoc
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}",id))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
		Mockito.verify(bookService).removeBook(id);
	}
	
	@Test
	public void deleteBook_Returns_Conflict_If_Book_Cannot_Be_Deleted() throws Exception {
		// Arrange
		int id = 1;
		Mockito.doThrow(new IllegalArgumentException()).when(bookService).removeBook(id);
		// Act//Assertoc
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}",id))
		.andExpect(MockMvcResultMatchers.status().isConflict());
		Mockito.verify(bookService).removeBook(id);
	}
	
	@Test
	public void deleteBook_Returns_NotFound_If_Book_Does_Not_Exist() throws Exception {
		// Arrange
		int id = 1;
		Mockito.doThrow(new NullPointerException()).when(bookService).removeBook(id);
		// Act//Assertoc
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}",id))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(bookService).removeBook(id);
	}
	
	
	public static String objToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(obj);
		return requestJson;
	}

}
