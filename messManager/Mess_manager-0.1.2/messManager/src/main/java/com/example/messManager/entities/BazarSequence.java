package com.example.messManager.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="BazarSequence")
public class BazarSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String pairMember_one;
    @Column(nullable = false)
    private String pairMember_two;


    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String  endDate;

    private String isMiss;

    @Column(nullable = false)
    private String messName;

    public String getPairMember_one() {
        return pairMember_one;
    }

    public void setPairMember_one(String pairMember_one) {
        this.pairMember_one = pairMember_one;
    }

    public String getPairMember_two() {
        return pairMember_two;
    }

    public void setPairMember_two(String pairMember_two) {
        this.pairMember_two = pairMember_two;
    }

    public String  getStartDate() {
        return startDate;
    }

    public void setStartDate(String  startDate) {
        this.startDate = startDate;
    }

    public String  getEndDate() {
        return endDate;
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public void setEndDate(String  endDate) {
        this.endDate = endDate;
    }

    public String getIsMiss() {
        return isMiss;
    }

    public void setIsMiss(String isMiss) {
        this.isMiss = isMiss;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
