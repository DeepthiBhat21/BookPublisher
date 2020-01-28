package com.rakuten.training.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rakuten.training.dal.PublisherDAO;
import com.rakuten.training.domain.Publisher;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

	PublisherDAO dao;

	@Autowired
	public void setDao(PublisherDAO dao) {
		this.dao = dao;
	}

	@Override
	public int addNewPublisher(Publisher toBeAdded) {
		Publisher added = dao.save(toBeAdded);
		return added.getId();
	}

	@Override
	public void removePublisher(int id) {
		Publisher p = dao.findById(id);
		if (p != null)
			dao.deleteById(id);
		else 
			throw new NullPointerException("Publisher Does Not Exist");
	}

	@Override
	public Publisher findById(int id) {
		Publisher p = dao.findById(id);
		if(p != null) {
			return p;
		}
		else {
			throw new NullPointerException("Publisher Does Not Exist");
		}
	}

	@Override
	public List<Publisher> findAll() {
		return dao.findAll();
	}

	public PublisherDAO getDao() {
		return dao;
	}

}
