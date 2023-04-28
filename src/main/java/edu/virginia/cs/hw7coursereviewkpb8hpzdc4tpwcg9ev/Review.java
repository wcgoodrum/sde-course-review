package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

// Review class:
// INT id (autoincrement primary key)
// STRING text
// INT rating

public class Review {
    private int id;
    private String text;
    private int rating;


    public Review(int id, String text, int rating) {
        this.id = id;
        this.text = text;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
