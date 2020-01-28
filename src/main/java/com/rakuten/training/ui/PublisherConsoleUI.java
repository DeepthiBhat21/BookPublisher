package com.rakuten.training.ui;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rakuten.training.dal.PublisherDAO;
import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;
import com.rakuten.training.service.BookService;
import com.rakuten.training.service.PublisherService;

@Component
public class PublisherConsoleUI {

	BookService service;
	PublisherService publService;

	@Autowired
	public void setService(BookService service) {
		this.service = service;
	}

	@Autowired
	public void setPublService(PublisherService publService) {
		this.publService = publService;
	}

	public void createPublisherConsoleUI() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Name: ");
		String name = s.nextLine();
		System.out.println("Enter City: ");
		String city = s.nextLine();
		Publisher toBeSaved = new Publisher(name, city);
		publService.addNewPublisher(toBeSaved);
		s.close();
	}

	public void deletePublisher() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter id of publisher to delete");
		int id = Integer.parseInt(s.nextLine());
		publService.removePublisher(id);
		s.close();
	}

	public void findPublisher() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter id of Publisher to find");
		int id = Integer.parseInt(s.nextLine());
		System.out.println(publService.findById(id).getName());
		s.close();
	}

	public void findAllPublishers() {
		List<Publisher> publishers = publService.findAll();
		System.out.println("Publishers available in the database are:");
		for (Publisher p : publishers) {
			System.out.println(p.getName());
		}
	}

	public void createBookConsoleUI() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Name: ");
		String name = s.nextLine();
		System.out.println("Enter Genre: ");
		String genre = s.nextLine();
		System.out.println("Enter number of pages");
		int num = Integer.parseInt(s.nextLine());
		System.out.println("Enter Price");
		double price = Double.parseDouble(s.nextLine());
		System.out.println("Enter publisher ID");
		int publisherID = Integer.parseInt(s.nextLine());

		Book b = new Book(name, genre, num, price);
		Book added = service.addNewBook(b, publisherID);
		System.out.println("Created book with ID :" + added.getId());
		s.close();
	}

	public void deleteBook() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter id of book to delete");
		int id = Integer.parseInt(s.nextLine());
		service.removeBook(id);
		s.close();
	}

	public void findBook() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter id of book to find");
		int id = Integer.parseInt(s.nextLine());
		System.out.println(service.findById(id).getName());
		s.close();
	}

	public void findAllBooks() {
		List<Book> books = service.findAll();
		System.out.println("Books available in the database are:");
		for (Book b : books) {
			System.out.println(b.getName());
		}
	}
}
