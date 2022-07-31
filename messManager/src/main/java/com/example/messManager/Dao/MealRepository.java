package com.example.messManager.Dao;

import com.example.messManager.entities.BazarSequence;
import com.example.messManager.entities.Meal;
import com.example.messManager.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Integer>{
	
	@Query("select u from Meal u where u.messName = :messName")
	public List<Meal> getMealByMessName(@Param("messName") String messName);

	@Query("select u from Meal u where u.messName = :messName and u.day = :day")
	public Meal getMealByDay_MessName(@Param("messName") String messName, @Param("day") String day);
}
