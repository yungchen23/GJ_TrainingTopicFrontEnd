package com.restfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfront.model.*;
import com.restfront.service.BookingService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bookings")
public class BookingController implements CommandLineRunner {
	@Autowired
	private BookingRepository dao;

	@Autowired
	private LoginForMemberRepository dao2;

//	@Autowired
//	private BookingRepository dao3;
	
	@Autowired
	private MemberRepository dao4;

	@Autowired
	private BookingService bookingService;

	// 獲取今天之前的訂單
	@GetMapping("/before-today")
	public List<Booking> getBookingsBeforeToday() {
		LocalDate today = LocalDate.now();
		return dao.findAll().stream().filter(
				booking -> LocalDate.parse(booking.getBooking_date(), DateTimeFormatter.ISO_DATE).isBefore(today))
				.collect(Collectors.toList());
	}

	@GetMapping("/{loginAccount}/before-today")
	public List<Booking> getBookingsForMemberBeforeToday(@PathVariable String loginAccount) {
		// 查找會員的 loginAccount 對應的 member_id
		LoginForMember user = dao2.findByLoginAccount(loginAccount);
		if (user != null) {
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = today.format(formatter);

//			return dao.findBookingsFromBeforeDate(formattedDate);
//			return dao.findByLoginAccountAndDateBeforeToday(user.getLogin_id(), formattedDate);
			return dao.findByLoginAccountAndDateBeforeToday(loginAccount, formattedDate);
		}
		return Collections.emptyList(); // 若無會員資料則返回空列表
	}

	@GetMapping("/{loginAccount}/from-today")
	public List<Booking> getBookingsForMemberFromToday(@PathVariable String loginAccount) {
		// 查找會員的 loginAccount 對應的 member_id
		LoginForMember user = dao2.findByLoginAccount(loginAccount);
		if (user != null) {
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = today.format(formatter);

			return dao.findByLoginAccountAndDateFromToday(loginAccount, formattedDate);
//			return dao.findByLoginAccountAndDateFromToday(user.getLogin_id(), formattedDate);
//			return dao.findByLoginAccountAndDateFromToday(user.getLogin_id(), today);
//			return dao.findBookingsFromDate(formattedDate);
		}
		return Collections.emptyList(); // 若無會員資料則返回空列表
	}

	
	@GetMapping
	public List<Booking> getAllBookings() {
		return dao.findAll();
	}

	@GetMapping("/{id}")
	public Booking getBookingById(@PathVariable String id) {
		return dao.findById(id).orElse(null);
	}

	@PostMapping
	public void saveBooking(@RequestBody Booking bk) {
		String v = dao.findAll().stream().max(Comparator.comparing(e -> e.getBooking_id())).get().getBooking_id() + 1;
		bk.setBooking_id(v);
		System.out.println("save:" + bk.toString());
		dao.save(bk);
	}

	@PutMapping("/{id}")
	public void updateBooking(@PathVariable String id, @RequestBody Booking bk) {
		Booking e = dao.findAll().stream().filter(e1 -> e1.getBooking_id().equals(id)).findAny().orElse(null);
		System.out.println("update:" + e.toString());
		// System.out.println("update:"+e);

		e.setBooking_id(bk.getBooking_id());
		// e.setMember_id(bk.getMember_id());
		// e.setBooking_date(bk.getBooking_date());
		// e.setBooking_time(bk.getBooking_time());
		e.setBooking_adult(bk.getBooking_adult());
		e.setBooking_kids(bk.getBooking_kids());
		e.setBooking_remark(bk.getBooking_remark());
		// e.setBooking_name(bk.getBooking_name());
		// e.setBooking_sex(bk.getBooking_sex());
		// e.setBooking_phone(bk.getBooking_phone());
		// e.setBooking_purpose(bk.getBooking_purpose());

		dao.save(e);
	}

	@DeleteMapping("/{id}")
	public void deleteBooking(@PathVariable String id) {
		Booking e = dao.findAll().stream().filter(e1 -> e1.getBooking_id().equals(id)).findAny().orElse(null);
		System.out.println("delete:" + e.toString());
		if (e != null)
			dao.delete(e);
	}

	@PostMapping("/createBooking")
	public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
		System.out.println("Received booking: " + booking);
		bookingService.createBooking(booking);
		return ResponseEntity.ok("Booking created successfully");
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Member m1 = dao4.findById(1).orElseThrow();
		Member m2 = dao4.findById(2).orElseThrow();

		dao.save(new Booking("20200101ABCD", "2020-01-01", "13:13", 2, 1, "good1", "王大明", "小姐", "0000", "1", "1", m1));
		dao.save(new Booking("20200103XDFG", "2020-01-03", "14:13", 1, 3, "good1", "王大明1", "先生", "0000", "2", "2", m1));
		dao.save(new Booking("20240828WERT", "2024-08-28", "14:13", 1, 3, "good1", "王大明1", "先生", "0000", "3", "3", m2));
		dao.save(new Booking("20240829ERTY", "2024-08-29", "14:13", 5, 2, "good1", "王大明1", "先生", "0000", "1", "1", m1));
		dao.save(new Booking("20240830POWQ", "2024-08-30", "15:13", 1, 3, "good1", "王大明1", "先生", "0000", "2", "2", m2));
		dao.save(new Booking("20240831WRTQ", "2024-08-31", "14:13", 8, 3, "good1", "王大明1", "先生", "0000", "3", "3", m2));
		dao.save(new Booking("20240905EAUQ", "2024-09-05", "15:13", 1, 0, "good1", "王大明1", "先生", "0000", "1", "1", m1));
		dao.save(new Booking("20240905X5UQ", "2024-09-05", "15:13", 2, 3, "good1", "王大明1", "先生", "0000", "1", "1", m2));
		dao.save(new Booking("20240905TTYU", "2024-09-10", "12:00", 1, 1, "good1", "李小美1", "先生", "0000", "1", "1", m2));
		dao.save(new Booking("20240908YSZG", "2024-09-12", "13:30", 1, 2, "good1", "王大明1", "先生", "0000", "1", "1", m1));
		dao.save(new Booking("20240910YSZG", "2024-09-15", "13:30", 2, 3, "good1", "李小美1", "小姐", "0000", "1", "1", m2));
		dao.save(new Booking("20240910GSZG", "2024-09-17", "12:30", 1, 1, "good1", "王大明1", "先生", "0000", "1", "1", m1));
		dao.save(new Booking("20240913HHZG", "2024-09-21", "11:30", 3, 4, "good1", "李小美1", "小姐", "0000", "1", "1", m2));

	}

}
