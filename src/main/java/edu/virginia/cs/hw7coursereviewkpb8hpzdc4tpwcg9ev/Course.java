package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

// Course class:
// INT id (autoincrement primary key)
// STRING department (i.e. "CS")
// STRING catalog (i.e. "3140")

public class Course {
    private String department;
    private String catalog;
    private int id;

    public Course(String department, String catalog, int id) {
        this.department = department;
        this.catalog = catalog;
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public String getCatalog() {
        return catalog;
    }

    public int getId() {
        return id;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setId(int id) {
        this.id = id;
    }
}
