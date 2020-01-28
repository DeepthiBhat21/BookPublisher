package com.rakuten.training.dal;

import java.util.List;

import com.rakuten.training.domain.Publisher;

public interface PublisherDAO {

	public Publisher save(Publisher toBesaved);

	public void deleteById(int id);

	public Publisher findById(int id);

	public List<Publisher> findAll();

}
