package telran.books.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.books.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	Set<Book> findBooksByAuthorsName (String name);
	
	Set<Book> findBooksByPublisherPublisherName (String name);
}
