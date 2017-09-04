package com.niedzielski.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		bookRepository.deleteAllInBatch();
	}

	@Test
	public void getBookById() throws Exception {
		Book bookToTest = new Book(1L, 123L, "testTitle", "testAuthor", "testYear", "testDescription", null, null, null,
				Status.AVAILABLE);
		bookRepository.saveAndFlush(bookToTest);

		mockMvc.perform(get("/api/books/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id", Matchers.is(1)))
				.andExpect(jsonPath("$.isbn", Matchers.is(123)))
				.andExpect(jsonPath("$.title", Matchers.is("testTitle")))
				.andExpect(jsonPath("$.author", Matchers.is("testAuthor")))
				.andExpect(jsonPath("$.year", Matchers.is("testYear")))
				.andExpect(jsonPath("$.description", Matchers.is("testDescription")))
				.andExpect(jsonPath("$.rentalDate").doesNotExist())
				.andExpect(jsonPath("$.returnDate").doesNotExist())
				.andExpect(jsonPath("user").doesNotExist());
	}

}
