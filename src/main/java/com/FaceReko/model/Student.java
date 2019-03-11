package com.FaceReko.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Student {

    @Id
    @NotBlank
    @Size(max = 12)
    private String enrollId;

    @NotBlank
    @Size(max = 100)
    private String password;

    @Max(25)
    private String name;
    @Max(10)
    private Long phone;


    private String gender;
    private String branch;
    @Email
    private String email;



    public Student()
    {

    }

    public Student(@NotBlank @Size(max = 12) String enrollId, @NotBlank @Size(max = 100) String password) {
        this.enrollId = enrollId;
        this.password = password;
    }



    public String getEnrollId() {
        return enrollId;
    }

    public Student(@NotBlank @Size(max = 12) String enrollId, @NotBlank @Size(max = 100) String password, @Max(25) String name, @Max(10) Long phone, String gender, String branch, @Email String email) {
        this.enrollId = enrollId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.branch = branch;
        this.email = email;


    }

    public void setEnrollId(String enrollId) {
        this.enrollId = enrollId;
    }

    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
