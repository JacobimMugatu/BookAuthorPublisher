package telran.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.books.api.BookEndPoints;
import telran.books.dto.AuthorDto;
import telran.books.dto.BookDto;
import telran.books.service.BookService;

@RestController
public class BooksHandler {

	@Autowired
	BookService bookService;

	@PostMapping(BookEndPoints.BOOK)
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@GetMapping(BookEndPoints.BOOK + "/{isbn}")
	public BookDto getBook(@PathVariable long isbn) {
		return bookService.getBookByIsbn(isbn);
	}

	@DeleteMapping(BookEndPoints.BOOK + "/{isbn}")
	public BookDto removeBook(@PathVariable long isbn) {
		return bookService.removeBook(isbn);
	}

	@GetMapping(BookEndPoints.AUTHOR + "/{name}")
	public Iterable<BookDto> booksByAuthor(@PathVariable String name) {
		return bookService.getBooksByAuthor(name);
	}

	@GetMapping(BookEndPoints.PUBLISHER + "/{name}")
	public Iterable<BookDto> booksByPublisher(@PathVariable String name) {
		return bookService.getBooksByPublisher(name);
	}

	@GetMapping(BookEndPoints.AUTHORS + "/{isbn}")
	public Iterable<AuthorDto> getAuthors(@PathVariable long isbn) {
		return bookService.getBookAuthors(isbn);
	}

	@GetMapping(BookEndPoints.PUBLISHERS + "/{name}")
	public Iterable<String> getPublishers(@PathVariable String name) {
		return bookService.getPublishersByAuthor(name);
	}
}
