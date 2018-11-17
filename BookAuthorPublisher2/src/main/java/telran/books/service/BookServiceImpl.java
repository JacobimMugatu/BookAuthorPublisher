package telran.books.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.books.dao.AuthorRepository;
import telran.books.dao.BookRepository;
import telran.books.dao.PublisherRepository;
import telran.books.domain.Author;
import telran.books.domain.Book;
import telran.books.domain.Publisher;
import telran.books.dto.AuthorDto;
import telran.books.dto.BookDto;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	PublisherRepository publisherRepository;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher()).orElse(null);
		if (publisher == null) {
			publisher = new Publisher(bookDto.getPublisher());
			publisherRepository.save(publisher);
		}
		Set<AuthorDto> authorDtos = bookDto.getAuthors();
		Set<Author> authors = new HashSet<>();
		for (AuthorDto authorDto : authorDtos) {
			Author author = authorRepository.findById(authorDto.getName()).orElse(null);
			if (author == null) {
				author = new Author(authorDto.getName(), authorDto.getBirthDate());
				authorRepository.save(author);
			}
			authors.add(author);
		}
		bookRepository.save(new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher));
		return true;

	}

	@Override
	@Transactional
	public BookDto removeBook(long isbn) {
		Book book = bookRepository.findById(isbn).get();
		if (book == null) {
			return null;
		}
		bookRepository.deleteById(isbn);
		return bookToBookDto(book);
	}

	@Override
	public BookDto getBookByIsbn(long isbn) {
		Book book = bookRepository.findById(isbn).get();
		if (book == null) {
			return null;
		}
		BookDto bookDto = bookToBookDto(book);
		return bookDto;
	}

	private BookDto bookToBookDto(Book book) {
		Set<AuthorDto> authors = book.getAuthors().stream().map(a -> new AuthorDto(a.getName(), a.getBirthDate()))
				.collect(Collectors.toSet());
		BookDto bookDto = new BookDto(book.getIsbn(), book.getTitle(), authors, book.getPublisher().getPublisherName());
		return bookDto;
	}

	@Override
	public Iterable<BookDto> getBooksByAuthor(String authorName) {

		return bookRepository.findBooksByAuthorsName(authorName).stream().map(b -> bookToBookDto(b))
				.collect(Collectors.toSet());

	}

	@Override
	public Iterable<BookDto> getBooksByPublisher(String publisherName) {

		Set<Book> books = bookRepository.findBooksByPublisherPublisherName(publisherName);
		return books.stream().map(this::bookToBookDto).collect(Collectors.toSet());
	}

	@Override
	public Iterable<AuthorDto> getBookAuthors(long isbn) {
		Book book = bookRepository.findById(isbn).get();
		if (book == null) {
			return null;
		}
		Set<Author> authors = book.getAuthors();

		return authors.stream().map(a -> new AuthorDto(a.getName(), a.getBirthDate())).collect(Collectors.toSet());
	}

	@Override
	public Iterable<String> getPublishersByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).get();
		if (author == null) {
			return null;
		}

		return author.getBooks().stream().map(b -> b.getPublisher().getPublisherName()).collect(Collectors.toSet());
	}

}
