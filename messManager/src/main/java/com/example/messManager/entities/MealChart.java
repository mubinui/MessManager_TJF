package com.example.messManager.entities;

import javax.persistence.*;

@Entity
@Table(name="meal_chart")
public class MealChart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private int breakfast;

    @Column(nullable = false)
    private int lunch;

    @Column(nullable = false)
    private int dinner;

    @Column(nullable = false)
    private String messName;


    private int memberID;

    private int totalMeal;

    private int mealPerPerson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    public int getLunch() {
        return lunch;
    }

    public void setLunch(int lunch) {
        this.lunch = lunch;
    }

    public int getDinner() {
        return dinner;
    }

    public void setDinner(int dinner) {
        this.dinner = dinner;
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public int getTotalMeal() {
        return totalMeal;
    }

    public void setTotalMeal(int totalMeal) {
        this.totalMeal = totalMeal;
    }

    public int getMealPerPerson() {
        return mealPerPerson;
    }

    public void setMealPerPerson(int mealPerPerson) {
        this.mealPerPerson = mealPerPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String day) {
        this.date = day;
    }
}
