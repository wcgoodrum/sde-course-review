package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

// Student class:
// INT id (autoincrement primary key)
// STRING username (must be unique)
// STRING password

public class Student {
    private String username;
    private String password;
    private int id;

    public Student(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
