package com.restfront.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfront.dao.BookingDao;
import com.restfront.model.Booking;
import com.restfront.model.Member;
import com.restfront.model.MemberRepository;




@Service
public class BookingService {

    @Autowired
    private BookingDao bookingRepository;
    
    @Autowired
    private MemberRepository memberRepository; // 加入 MemberRepository

    
    public void createBooking(Booking booking) {
        // 確保在這裡設定唯一的 booking_id
        if (booking.getBooking_id() == null || booking.getBooking_id().isEmpty()) {
            throw new IllegalArgumentException("Booking ID must be provided");
        }

        // 從數據庫中獲取 member 對象
        Member member = memberRepository.findById(booking.getMember().getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // 設置正確的 member 對象到 booking 中
        booking.setMember(member);

        // 保存 booking 到數據庫
        bookingRepository.save(booking);
    }

//    public void createBooking(Booking booking) {
//        // 確保在這裡設定唯一的 bookingid
//        if (booking.getBooking_id() == null || booking.getBooking_id().isEmpty()) {
//            throw new IllegalArgumentException("Booking ID must be provided");
//        }
//        bookingRepository.save(booking);
//    }
}
