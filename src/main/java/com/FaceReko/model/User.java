package com.FaceReko.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @NotBlank
    @Size(max = 12)
    private String enrollId;

    @NotBlank
    @Size(max = 100)
    private String password;


    private String name;

    private Long phone;


    private String gender;
    private String department;
    @Email
    private String email;
    @Lob
    private byte[] image;

    private String role;



    public User() {

    }

    public User(@NotBlank @Size(max = 12) String enrollId, @NotBlank @Size(max = 100) String password, String name, Long phone, String gender, String department, @Email String email, byte[] image, String role) {
        this.enrollId = enrollId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.department = department;
        this.email = email;
        this.image = image;
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(String enrollId) {
        this.enrollId = enrollId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
