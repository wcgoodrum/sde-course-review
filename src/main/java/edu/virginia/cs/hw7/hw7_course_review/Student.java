package edu.virginia.cs.hw7.hw7_course_review;

public class Student {
    private final String userName;
    private final String passWord;
    private int id;

    public Student(String user, String pass) {
        userName = user;
        passWord = pass; // maybe implement hashing if we have time
    }

    public String getUserName() {
        return this.userName;
    }
}
