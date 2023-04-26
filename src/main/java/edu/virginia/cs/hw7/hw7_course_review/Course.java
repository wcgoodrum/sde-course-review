package edu.virginia.cs.hw7.hw7_course_review;

public class Course {
    private final String department;
    private final String catalog;
    private int id;

    public Course (String user, String pass) {
        userName = user;
        passWord = pass; // maybe implement hashing if we have time
    }

    public String getUserName() {
        return this.userName;
    }
}
