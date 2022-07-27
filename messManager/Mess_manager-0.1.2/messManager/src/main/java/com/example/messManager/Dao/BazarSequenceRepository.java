package com.example.messManager.Dao;

import com.example.messManager.entities.BazarSequence;
import com.example.messManager.entities.Manager;
import com.example.messManager.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BazarSequenceRepository extends JpaRepository<BazarSequence, Integer>{
	
	@Query("select u from BazarSequence u where u.pairMember_one = :email or u.pairMember_two= :email")
	public Member getUserByUserName(@Param("email") String email);
}
