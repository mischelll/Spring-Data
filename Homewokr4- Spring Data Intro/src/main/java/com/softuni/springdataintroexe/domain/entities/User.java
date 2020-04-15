//package com.softuni.springdataintroexe.domain.entities;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.validation.constraints.Size;
//import java.util.Base64;
//import java.util.Date;
//
//@Entity
//@Table(name = "users")
//public class User extends BaseEntity {
//    private String username;
//    private String password;
//    private String email;
//    private Date registeredOn;
//    private Date lastTimeLoggedIn;
//    private Integer age;
//    private Boolean isDeleted;
//
//    public User() {
//    }
//
//    @Column(name = "username", nullable = false, unique = true)
//    @Size(min = 4, max = 30)
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Passw
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Date getRegisteredOn() {
//        return registeredOn;
//    }
//
//    public void setRegisteredOn(Date registeredOn) {
//        this.registeredOn = registeredOn;
//    }
//
//    public Date getLastTimeLoggedIn() {
//        return lastTimeLoggedIn;
//    }
//
//    public void setLastTimeLoggedIn(Date lastTimeLoggedIn) {
//        this.lastTimeLoggedIn = lastTimeLoggedIn;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public Boolean getDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(Boolean deleted) {
//        isDeleted = deleted;
//    }
//}
