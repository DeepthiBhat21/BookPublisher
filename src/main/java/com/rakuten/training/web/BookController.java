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
import com.rakuten.training.service.BookService;

@RestController
@CrossOrigin
public class BookController {

	BookService bookService;

	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/api/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		try {
			Book b = bookService.findById(id);
			return new ResponseEntity<Book>(b, HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/api/books")
	public List<Book> getAllBooks() {
		return bookService.findAll();
	}

	@PostMapping("/api/books/{publisherId}")
	public ResponseEntity<Book> addNewBookByPublisher(@RequestBody Book toBeAdded, @PathVariable int publisherId) {

		try {
			Book added = bookService.addNewBook(toBeAdded, publisherId);
			System.out.println("PublisherId" + publisherId);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("/api/publisher/" + publisherId + "/books/" + added.getId()));
			return new ResponseEntity<Book>(added, headers, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
		}

		catch (NullPointerException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/api/books/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable int id) {
		try {
			bookService.removeBook(id);
			return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Book>(HttpStatus.CONFLICT);
		}
	}

}
