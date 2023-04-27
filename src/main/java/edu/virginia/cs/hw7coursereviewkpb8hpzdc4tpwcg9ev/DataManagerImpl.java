package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import java.util.List;

public class DataManagerImpl implements DataManager {

    @Override
    public Student login(String user, String password) {
        return null;
    }

    @Override
    public Student createNewUser(String user, String password, String confirm) {
        return null;
    }

    @Override
    public boolean validCourse(String courseName, Student student) {
        return false;
    }

    @Override
    public void addReview(Student student, String courseName, String text, int rating) {

    }

    @Override
    public List<Review> getReviews(String courseName) {
        return null;
    }

    @Override
    public double getAverageRating(String courseName) {
        return 0;
    }
}