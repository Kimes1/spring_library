package com.niedzielski.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.niedzielski.model.Book;
import com.niedzielski.model.Book.Status;
import com.niedzielski.repository.BookRepository;
import com.niedzielski.service.EmailService;

@Component
public class EmailScheduler {

	private final BookRepository bookRepository;
	private final EmailService emailService;

	@Autowired
	public EmailScheduler(BookRepository bookRepository, EmailService emailService) {
		this.bookRepository = bookRepository;
		this.emailService = emailService;
	}

	@Scheduled(cron = "* * * * * *")
	public void sendReminders() {
		List<Book> books = bookRepository.findByStatus(Status.NON_AVAILABLE);
		for (Book book : books) {
			LocalDate returnDate = book.getReturnDate();
			LocalDate currentDate = LocalDate.now();
			String subject = "Reminder";
			String message = "Please return your book";
			if (returnDate.plusDays(3).isAfter(currentDate)) {
				emailService.sendSimpleMessage(book.getUser().getEmail(), subject, message);
			}

		}
	}

}
