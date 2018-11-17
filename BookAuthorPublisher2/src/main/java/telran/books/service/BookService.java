package telran.books.service;

import telran.books.dto.AuthorDto;
import telran.books.dto.BookDto;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto removeBook(long isbn);
	
	BookDto getBookByIsbn(long isbn);
	
	Iterable<BookDto> getBooksByAuthor(String authorName);
	
	Iterable<BookDto> getBooksByPublisher(String publisherName);
	
	Iterable<AuthorDto> getBookAuthors(long isbn);
	
	Iterable<String> getPublishersByAuthor(String authorName);

}
