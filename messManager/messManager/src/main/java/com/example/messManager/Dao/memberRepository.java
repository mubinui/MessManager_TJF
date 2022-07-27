package com.example.messManager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.messManager.entities.Manager;
import com.example.messManager.entities.Member;

import java.util.List;

public interface memberRepository extends JpaRepository<Member, Integer>{
	
	@Query("select u from Member u where u.email = :email")
	public Member getUserByUserName(@Param("email") String email);

	@Query("select m from Member m where m.manager.id =:id ")
	public List<Member> findMemberByManager(@Param("id") int id);
}
