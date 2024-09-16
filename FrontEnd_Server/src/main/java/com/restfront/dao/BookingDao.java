package com.restfront.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restfront.model.Booking;


public interface BookingDao extends JpaRepository<Booking, String> {
    // 可以根據需要添加自定義查詢方法
}
