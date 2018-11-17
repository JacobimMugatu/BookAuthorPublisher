package telran.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.books.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {

}
