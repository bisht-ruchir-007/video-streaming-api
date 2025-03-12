package com.app.practice;


import java.util.List;

public class StudentDTO {

    private Long studentId;

    private String name;
    private Integer age;

    private String grade;
    private String email;
    private List<Address> addresses;

    public StudentDTO(Long studentId, String name, Integer age, String grade, String email, List<Address> addresses) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.email = email;
        this.addresses = addresses;
    }

    public StudentDTO() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
