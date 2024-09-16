package com.restfront.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Integer>{

//	@Query("SELECT e FROM #{#entityName} e WHERE e.loginForMember.loginAccount  = ?1")
//	Member findByLoginAccount(String loginAccount);
	
	   @Query("SELECT e FROM Member e WHERE e.loginformember.login_account = ?1")
	    Member findByLoginAccount(String loginAccount);
	
}

