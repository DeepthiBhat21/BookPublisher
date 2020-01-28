package com.rakuten.training.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.domain.Publisher;

@Repository
@Transactional
public class PublisherDAOJpaImpl implements PublisherDAO {

	@Autowired
	EntityManager em;

	@Override
	public Publisher save(Publisher toBesaved) {
		em.persist(toBesaved);
		return toBesaved;
	}

	@Override
	public void deleteById(int id) {
		Publisher p = em.getReference(Publisher.class, id);
		em.remove(p);
	}

	@Override
	public Publisher findById(int id) {
		return em.find(Publisher.class, id);
	}

	@Override
	public List<Publisher> findAll() {
		Query q = em.createQuery("select p from Publisher as  p");
		List<Publisher> all = q.getResultList();
		return all;
	}

}
