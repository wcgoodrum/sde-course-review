package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

public class Student {
    private final String userName;
    private final String passWord;

    public Student (String user, String pass) {
        userName = user;
        passWord = pass;
    }

    public String getPassword() {
        return this.passWord;
    }
}
