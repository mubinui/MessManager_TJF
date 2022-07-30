package com.example.messManager.Dao;

import com.example.messManager.entities.BazarSequence;
import com.example.messManager.entities.Manager;
import com.example.messManager.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BazarSequenceRepository extends JpaRepository<BazarSequence, Integer>{
	
	@Query("select u from BazarSequence u where u.pairMember_one = :name or u.pairMember_two= :name and u.messName = :messName")
	public List<BazarSequence> getUserByName(@Param("name") String name, @Param("messName") String messName);

	@Query("select u from BazarSequence u where u.messName = :messName")
	public List<BazarSequence> getBazarSequenceByMess(@Param("messName") String messName);
}
