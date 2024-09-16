package com.restfront.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface LoginForMemberRepository extends JpaRepository<LoginForMember,Integer>{

	   @Query("SELECT l FROM LoginForMember l WHERE l.login_account = :loginAccount")
	    LoginForMember findByLoginAccount(@Param("loginAccount") String loginAccount);
	   
	// 確保使用 login_account，與實體中的屬性名稱一致
//	    boolean existsByLogin_account(String login_account);
	   @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LoginForMember l WHERE l.login_account = :login_account")
	    boolean existsByLogin_account(@Param("login_account") String login_account);
}
