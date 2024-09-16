package com.restfront.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking,String>  {

	// 根據會員帳號和今天之後的日期篩選預約
//    @Query("SELECT b FROM Booking b WHERE b.member_id = :memberId AND b.booking_date >= :today")
//    List<Booking> findByLoginAccountAndDateFromToday(@Param("memberId") Integer memberId, @Param("today") LocalDate today);
	
	// 根據會員帳號和今天之後的日期篩選預約
//    @Query("SELECT b FROM Booking b WHERE b.member_id = :memberId AND b.booking_date >= :today")
//    List<Booking> findByLoginAccountAndDateFromToday(@Param("memberId") Integer memberId, @Param("today") String today);
		
    @Query("SELECT b FROM Booking b WHERE b.member.loginformember.login_account = :loginAccount AND b.booking_date >= :today")
    List<Booking> findByLoginAccountAndDateFromToday(@Param("loginAccount") String loginAccount, @Param("today") String today);
    
    
	// 根據會員帳號和今天之前(不含今天)的日期篩選預約
//    @Query("SELECT b FROM Booking b WHERE b.member_id = :memberId AND b.booking_date < :today")
//    List<Booking> findByLoginAccountAndDateBeforeToday(@Param("memberId") Integer memberId, @Param("today") String today);
    
    @Query("SELECT b FROM Booking b WHERE b.member.loginformember.login_account = :loginAccount AND b.booking_date < :today")
    List<Booking> findByLoginAccountAndDateBeforeToday(@Param("loginAccount") String loginAccount, @Param("today") String today);
	  

    
    
    @Query("SELECT b FROM Booking b WHERE b.booking_date >= :date")
    List<Booking> findBookingsFromDate(@Param("date") String date);
	
    
    @Query("SELECT b FROM Booking b WHERE b.booking_date < :date")
    List<Booking> findBookingsFromBeforeDate(@Param("date") String date);
    
}
