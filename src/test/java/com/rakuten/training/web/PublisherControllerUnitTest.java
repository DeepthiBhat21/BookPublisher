package com.rakuten.training.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;
import com.rakuten.training.service.BookService;
import com.rakuten.training.service.PublisherService;

//  To enable JUnit to be able to stimulate and process the Request-Response, we take the help of Spring
@RunWith(SpringRunner.class)
@WebMvcTest({ PublishController.class })
public class PublisherControllerUnitTest {

	MockMvc mockMvc;

	@Autowired
	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	// Request Spring to Mock this bean for us.
	@MockBean
	PublisherService service;
	@MockBean
	BookService bookService;

	@Test
	public void getPublisherById_Returns_OK_With_Correct_Publisher_If_Found() throws Exception {
		// Arrange
		Publisher found = new Publisher("test", "test");
		int id = 1;
		found.setId(id);
		Mockito.when(service.findById(id)).thenReturn(found);
		// Act//Assert
		mockMvc.perform(get("/api/publishers/{id}", id)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)));
	}

	@Test
	public void getPublisherById_Returns_NotFound_If_Publisher_Does_Not_Exist() throws Exception {
		// Arrange
		int id = 1;
		Mockito.when(service.findById(id)).thenThrow(new NullPointerException());
		// Act//Assert
		mockMvc.perform(get("/api/publishers/{id}", id)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void addNewPublisher_Returns_Created_If_Publisher_Is_Created() throws Exception {
		// Arrange
		Publisher toBeAdded = new Publisher("test", "test");
		int id = 1;
		Mockito.when(service.addNewPublisher(Mockito.any(Publisher.class))).thenReturn(id);
		// Act//Assert
		mockMvc.perform(post("/api/publishers").contentType(MediaType.APPLICATION_JSON).content(objToJson(toBeAdded)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/api/publishers/" + id));
		Mockito.verify(service).addNewPublisher(Mockito.any(Publisher.class));

	}

	@Test
	public void deletePublisher_Returns_NoContent_If_Publisher_Is_Deleted() throws Exception {
		// Arrange
		int id = 1;
		Mockito.doNothing().when(service).removePublisher(id);
		// Act//Assert
		mockMvc.perform(delete("/api/publishers/{id}", id)).andExpect(MockMvcResultMatchers.status().isNoContent());
		Mockito.verify(service).removePublisher(id);

	}

	@Test
	public void deletePublisher_Returns_NotFound_If_Publisher_Does_Not_Exist() throws Exception {
		// Arrange
		int id = 1;
		Mockito.doThrow(new NullPointerException()).when(service).removePublisher(id);
		// Act//Assert
		mockMvc.perform(delete("/api/publishers/{id}", id)).andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(service).removePublisher(id);

	}

	@Test
	public void getBookByPublisherId_Returns_Ok_And_ListOfBooks_If_Publisher_Exist() throws Exception {
		// Arrange
		int id = 1;
		Publisher p = new Publisher("test", "test");
		p.setId(id);
		List<Book> books = new ArrayList<Book>();
		Book r1 = new Book("test1", "test1", 1234, 1234);
		Book r2 = new Book("test2", "test2", 1234, 1234);
		books.add(r1);
		r1.setPublisher(p);
		books.add(r2);
		r2.setPublisher(p);
		p.setBooks(books);

		Mockito.when(service.findById(id)).thenReturn(p);
		// Act//Assert
		mockMvc.perform(get("/api/publishers/{publisherId}/books", id)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].publisher.id", CoreMatchers.hasItem(id)));
		Mockito.verify(service).findById(id);

	}

	@Test
	public void getBookByPublisherId_Returns_NotFound_If_Publisher_Does_Not_Exist() throws Exception {
		// Arrange
		int id = 1;
		Mockito.doThrow(new NullPointerException()).when(service).findById(id);
		// Act//Assert
		mockMvc.perform(get("/api/publishers/{id}/books", id)).andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(service).findById(id);

	}

	@Test
	public void getBookByPublisherId_Returns_NoContent_If_Publisher_Does_Not_Have_Books() throws Exception {
		// Arrange
		int id = 1;
		Publisher p = new Publisher("test", "test");
		p.setId(id);
		List<Book> books = new ArrayList<Book>();
		p.setBooks(books);
		Mockito.when(service.findById(id)).thenReturn(p);
		// Act//Assert
		mockMvc.perform(get("/api/publishers/{publisherId}/books", id)).andExpect(MockMvcResultMatchers.status().isNoContent());		
		Mockito.verify(service).findById(id);

	}

	public static String objToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(obj);
		return requestJson;
	}
}
