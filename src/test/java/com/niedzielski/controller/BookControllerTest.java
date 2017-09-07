package com.niedzielski.controller;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niedzielski.LibraryApplication;
import com.niedzielski.model.Book;
import com.niedzielski.model.Book.Status;
import com.niedzielski.repository.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@WebAppConfiguration
public class BookControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		bookRepository.deleteAllInBatch();
	}

	@Test
	public void getBookByIdTest() throws Exception {
		Book bookToTest = new Book(123L, "testTitle", "testAuthor", "testYear", "testDescription", null, null, null,
				Status.AVAILABLE);
		Book savedBook = bookRepository.saveAndFlush(bookToTest);

		mockMvc.perform(get("/api/books/" + savedBook.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", Matchers.is(savedBook.getId().intValue())))
				.andExpect(jsonPath("$.isbn", Matchers.is(123)))
				.andExpect(jsonPath("$.title", Matchers.is("testTitle")))
				.andExpect(jsonPath("$.author", Matchers.is("testAuthor")))
				.andExpect(jsonPath("$.year", Matchers.is("testYear")))
				.andExpect(jsonPath("$.description", Matchers.is("testDescription")))
				.andExpect(jsonPath("$.rentalDate").doesNotExist())
				.andExpect(jsonPath("$.returnDate").doesNotExist())
				.andExpect(jsonPath("user").doesNotExist());
	}

	@Test
	public void postBookTest() throws Exception {
		Book bookToTest = new Book(312L, "testTitle", "testAuthor", "testYear", "testDescription", null, null, null,
				Status.AVAILABLE);
		
		String json = mapper.writeValueAsString(bookToTest);
		

		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		Book existingBookToTest = bookRepository.findOneByIsbn(312L);
		
		assertThat(existingBookToTest.getIsbn()).isEqualTo(bookToTest.getIsbn());
		assertThat(existingBookToTest.getTitle()).isEqualTo(bookToTest.getTitle());
		assertThat(existingBookToTest.getAuthor()).isEqualTo(bookToTest.getAuthor());
		assertThat(existingBookToTest.getYear()).isEqualTo(bookToTest.getYear());
		assertThat(existingBookToTest.getDescription()).isEqualTo(bookToTest.getDescription());
		assertThat(existingBookToTest.getRentalDate()).isEqualTo(bookToTest.getRentalDate());
		assertThat(existingBookToTest.getReturnDate()).isEqualTo(bookToTest.getReturnDate());

	}
	
	@Test
	public void getBookListTest() throws Exception {
		Book firstBookToTest = new Book(123L, "testTitle", "testAuthor", "testYear", "testDescription", null, null, null,
				Status.AVAILABLE);
		Book secondBookToTest = new Book(456L, "testTitle2", "testAuthor2", "testYear2", "testDescription2", null, null, null,
				Status.AVAILABLE);
		bookRepository.saveAndFlush(firstBookToTest);
		bookRepository.saveAndFlush(secondBookToTest);


		mockMvc.perform(get("/api/books/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].isbn", Matchers.is(123)))
				.andExpect(jsonPath("$[0].title", Matchers.is("testTitle")))
				.andExpect(jsonPath("$[0].author", Matchers.is("testAuthor")))
				.andExpect(jsonPath("$[0].year", Matchers.is("testYear")))
				.andExpect(jsonPath("$[0].description", Matchers.is("testDescription")))
				.andExpect(jsonPath("$[0].rentalDate").doesNotExist())
				.andExpect(jsonPath("$[0].returnDate").doesNotExist())
				.andExpect(jsonPath("$[0].user").doesNotExist())
				.andExpect(jsonPath("$[1].isbn", Matchers.is(456)))
				.andExpect(jsonPath("$[1].title", Matchers.is("testTitle2")))
				.andExpect(jsonPath("$[1].author", Matchers.is("testAuthor2")))
				.andExpect(jsonPath("$[1].year", Matchers.is("testYear2")))
				.andExpect(jsonPath("$[1].description", Matchers.is("testDescription2")))
				.andExpect(jsonPath("$[1].rentalDate").doesNotExist())
				.andExpect(jsonPath("$[1].returnDate").doesNotExist())
				.andExpect(jsonPath("$[1].user").doesNotExist());
	}
	
	@Test
	public void updateBookByIdTest() throws Exception {
		Book bookToTest = new Book(123L, "testTitle", "testAuthor", "testYear", "testDescription", null, null, null,
				Status.AVAILABLE);
		bookRepository.saveAndFlush(bookToTest);
		
		Book fixedBook = new Book(456L, "updatedTitle", "updatedAuthor", "updatedYear", "updatedtDescription", null, null, null,
				Status.AVAILABLE);
		
		String json = mapper.writeValueAsString(fixedBook);
		
		mockMvc.perform(put("/api/books/" + bookToTest.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		Book updatedBook = bookRepository.findOneByIsbn(456L);
		
		assertThat(updatedBook.getIsbn()).isEqualTo(fixedBook.getIsbn());
		assertThat(updatedBook.getTitle()).isEqualTo(fixedBook.getTitle());
		assertThat(updatedBook.getAuthor()).isEqualTo(fixedBook.getAuthor());
		assertThat(updatedBook.getYear()).isEqualTo(fixedBook.getYear());
		assertThat(updatedBook.getDescription()).isEqualTo(fixedBook.getDescription());
		assertThat(updatedBook.getRentalDate()).isEqualTo(fixedBook.getRentalDate());
		assertThat(updatedBook.getReturnDate()).isEqualTo(fixedBook.getReturnDate());
	}
}
