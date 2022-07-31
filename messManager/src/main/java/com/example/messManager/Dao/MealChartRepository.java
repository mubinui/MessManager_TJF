package com.example.messManager.Dao;

import com.example.messManager.entities.Meal;
import com.example.messManager.entities.MealChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealChartRepository extends JpaRepository<MealChart, Integer>{
	
	@Query("select u from MealChart u where u.memberID = :id")
	public List<MealChart> getMealByUsername(@Param("id") int id);

	@Query("select u from MealChart u where u.messName = :messName")
	public List<MealChart> getMealByMessName(@Param("messName") String messName);

}
