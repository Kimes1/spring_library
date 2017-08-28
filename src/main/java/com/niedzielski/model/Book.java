package com.niedzielski.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Objects;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long isbn;

	@NotEmpty
	@Size(min = 2, max = 30)
	private String author;

	@NotEmpty
	private String year;

	@NotEmpty
	@Size(min = 2, max = 1000)
	private String description;

	@JsonInclude(Include.NON_NULL)
	private LocalDate rentalDate;

	@JsonInclude(Include.NON_NULL)
	private LocalDate returnDate;

	@OneToOne
	@JsonBackReference
	@JsonInclude(Include.NON_NULL)
	private User user;

	Status status = Status.AVAILABLE;

	Book() {
	}

	public Book(Long id, Long isbn, String author, String year, String description, LocalDate rentalDate,
			LocalDate returnDate, User user, Status status) {
		this.id = id;
		this.isbn = isbn;
		this.author = author;
		this.year = year;
		this.description = description;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.user = user;
		this.status = status;
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

	// public int getNumberOfCopies() {
	// return numberOfCopies;
	// }
	//
	// public int incNumberOfCopies() {
	// return numberOfCopies++;
	// }
	//
	// public int decNumberOfCopies() {
	// return numberOfCopies--;
	// }

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(LocalDate rentalDate) {
		this.rentalDate = rentalDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
				&& Objects.equal(this.description, other.description);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.isbn, this.author, this.year, this.description);
	}

	public enum Status {
		AVAILABLE, NON_AVAILABLE
	}

}
