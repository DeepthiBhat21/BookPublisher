package com.rakuten.training.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;
import com.rakuten.training.service.BookService;
import com.rakuten.training.service.PublisherService;

@RestController
@CrossOrigin
public class PublishController {

	PublisherService service;
	BookService bookService;

	@Autowired
	public void setService(PublisherService service) {
		this.service = service;
	}

	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/api/publishers/{id}")
	public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
		try {
			Publisher p = service.findById(id);
			return new ResponseEntity<Publisher>(p, HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<Publisher>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/api/publishers")
	public List<Publisher> getAllPublisers() {
		return service.findAll();
	}

	@PostMapping("/api/publishers")
	public ResponseEntity<Publisher> addNewPublisher(@RequestBody Publisher toBeAdded) {
		int id = service.addNewPublisher(toBeAdded);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/publishers/" + id));
		return new ResponseEntity<Publisher>(headers, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/publishers/{id}")
	public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
		try {
			service.removePublisher(id);
			return new ResponseEntity<Publisher>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			return new ResponseEntity<Publisher>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/api/publishers/{publisherId}/books")
	public ResponseEntity<List<Book>> getBookByPublisherId(@PathVariable int publisherId) {
		try {
			Publisher p = service.findById(publisherId);
			List<Book> books = p.getBooks();
			if(books.size() != 0)
				return new ResponseEntity<List<Book>>(p.getBooks(), HttpStatus.OK);
			else
				return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			return new ResponseEntity<List<Book>>(HttpStatus.NOT_FOUND);
		}
	}

}
