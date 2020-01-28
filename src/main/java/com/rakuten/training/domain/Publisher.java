package com.rakuten.training.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Publisher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int publisherid;
	String name;
	String city;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "publisher", cascade = CascadeType.REMOVE)
	@JsonIgnore
	List<Book> books;

	public Publisher() {
		// TODO Auto-generated constructor stub
	}

	public Publisher(String name, String city) {
		super();
		this.name = name;
		this.city = city;
	}

	public int getId() {
		return publisherid;
	}

	public void setId(int id) {
		this.publisherid = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
