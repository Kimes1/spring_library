package com.niedzielski.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@JsonIgnore
	@Size(min = 2, max = 12)
	private String username;

	@NotEmpty
	@JsonIgnore
	@Size(min = 2, max = 12)
	private String password;

	@NotEmpty
	@Size(min = 2, max = 30)
	private String name;

	@NotEmpty
	@Size(min = 2, max = 30)
	private String surname;

	@NotEmpty
	private String dateOfBirth;

	@NotEmpty
	@Email
	@Column(unique = true)
	private String email;

	@ManyToMany
	private Set<Book> books = new HashSet<>();

	public User() {
	}

	public User(Long id, String name, String surname, String dateOfBirth, String email) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addBooks(Book book) {
		books.add(book);
	}

	public Set<Book> getBooksLentByUser() {
		return books;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.name, other.name)
				&& Objects.equal(this.surname, other.surname) && Objects.equal(this.dateOfBirth, other.dateOfBirth)
				&& Objects.equal(this.email, other.email);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.name, this.surname, this.dateOfBirth, this.email);
	}
}
