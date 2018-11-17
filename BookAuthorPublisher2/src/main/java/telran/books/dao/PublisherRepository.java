package telran.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.books.domain.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {

}
