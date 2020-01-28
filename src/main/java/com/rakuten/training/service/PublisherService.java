package com.rakuten.training.service;

import java.util.List;

import com.rakuten.training.domain.Publisher;

public interface PublisherService {

	int addNewPublisher(Publisher toBeAdded);

	void removePublisher(int id);

	Publisher findById(int id);

	List<Publisher> findAll();
}
