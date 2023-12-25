package com.example.park.myrestfulservice.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@JsonFilter("UserInfo")
public class AdminUser {
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinData;
    private String password;
    private String ssn;



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