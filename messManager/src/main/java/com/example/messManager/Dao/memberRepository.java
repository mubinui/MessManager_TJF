package com.example.messManager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.messManager.entities.Manager;
import com.example.messManager.entities.Member;

public interface memberRepository extends JpaRepository<Member, Integer>{
	
	@Query("select u from Member u where u.email = :email")
	public Member getUserByUserName(@Param("email") String email);
}
