package com.niedzielski.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long isbn;
	private String author;
	private String year;
	private String description;
	private int numberOfCopies;

	public Book() {
	}

	public Book(Long isbn, String author, String year, String description, int numberOfCopies) {
		this.isbn = isbn;
		this.author = author;
		this.year = year;
		this.description = description;
		this.numberOfCopies = numberOfCopies;
	}

	public Long getId() {
		return id;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfCopies() {
		return numberOfCopies;
	}

	public int incNumberOfCopies() {
		return numberOfCopies++;
	}

	public int decNumberOfCopies() {
		return numberOfCopies--;
	}
}
