package com.rakuten.training.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;

@Repository
@Transactional
public class BookDAOJpaImpl implements BookDAO {

	EntityManager em;

	@Autowired
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Book save(Book toBesaved) {
		em.persist(toBesaved);
		return toBesaved;
	}

	@Override
	public Book findById(int id) {
		return em.find(Book.class, id);
	}

	@Override
	public List<Book> findAll() {
		Query q = em.createQuery("select b from Book as  b");
		List<Book> all = q.getResultList();
		return all;
	}

	@Override
	public void deleteById(int id) {
		Book b = em.getReference(Book.class, id);
		em.remove(b);
	}

}
