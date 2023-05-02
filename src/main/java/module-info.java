module edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev to javafx.fxml;
    exports edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;
}