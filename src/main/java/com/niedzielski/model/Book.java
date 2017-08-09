package com.niedzielski.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Objects;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	@Size(min = 13, max = 13)
	private Long isbn;

	@NotEmpty
	@Size(min = 2, max = 30)
	private String author;

	@NotEmpty
	private String year;

	@NotEmpty
	@Size(min = 2, max = 1000)
	private String description;

	@NotEmpty
	@Size(min = 1)
	private int numberOfCopies;

	Book() {
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Book other = (Book) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.isbn, other.isbn)
				&& Objects.equal(this.author, other.author) && Objects.equal(this.year, other.year)
				&& Objects.equal(this.description, other.description)
				&& Objects.equal(this.numberOfCopies, other.numberOfCopies);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.isbn, this.author, this.year, this.description, this.numberOfCopies);
	}
}
