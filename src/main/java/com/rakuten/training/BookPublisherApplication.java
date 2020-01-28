package com.rakuten.training;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/*import com.rakuten.training.dal.PublisherDAO;
import com.rakuten.training.domain.Book;
import com.rakuten.training.domain.Publisher;
import com.rakuten.training.ui.PublisherConsoleUI;
*/
@SpringBootApplication
public class BookPublisherApplication {

	public static void main(String[] args) {
		// ApplicationContext springContainer =
		SpringApplication.run(BookPublisherApplication.class, args);
		/*
		 * PublisherConsoleUI ui = springContainer.getBean(PublisherConsoleUI.class);
		 * ui.findAllPublishers();
		 */
	}

}
