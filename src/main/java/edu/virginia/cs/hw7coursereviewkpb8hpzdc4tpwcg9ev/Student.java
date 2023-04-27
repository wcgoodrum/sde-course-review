package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

public class Student {
    private final String username;

    private final String password;

    public Student (String user, String pass) {
        username = user;
        password = pass;
    }

    public String getPassword() {
        return this.password;
    }
    public String getUsername() {return username;}
}
