package com.example.park.myrestfulservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@JsonIgnoreProperties(value = {"password", "ssn"}  )
public class User {
    private Integer id;

    @Size(min = 2 , message =  "Name은 2글자 이상 입력해 주세요.")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinData;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String ssn;

    public User(Integer id, String name, Date joinData, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinData = joinData;
        this.password = password;
        this.ssn = ssn;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJoinData(Date joinData) {
        this.joinData = joinData;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getJoinData() {
        return joinData;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", joinData=" + joinData +
                ", password='" + password + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }
}