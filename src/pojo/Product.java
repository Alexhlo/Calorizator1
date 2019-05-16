package pojo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.DriverManager;

public class Product {

    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleDoubleProperty protein = new SimpleDoubleProperty();
    public SimpleDoubleProperty fats = new SimpleDoubleProperty();
    public SimpleDoubleProperty carbs = new SimpleDoubleProperty();
    public SimpleIntegerProperty calories = new SimpleIntegerProperty();

    public String getName() { return name.get(); }
//    public SimpleStringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public double getProtein() { return protein.get(); }
//    public SimpleDoubleProperty proteinProperty() { return protein; }
    public void setProtein(double protein) { this.protein.set(protein); }

    public double getFats() { return fats.get(); }
//    public SimpleDoubleProperty fatsProperty() { return fats; }
    public void setFats(double fats) { this.fats.set(fats); }

    public double getCarbs() { return carbs.get(); }
//    public SimpleDoubleProperty carbsProperty() { return carbs; }
    public void setCarbs(double carbs) { this.carbs.set(carbs); }

    public int getCalories() { return calories.get(); }
//    public SimpleIntegerProperty caloriesProperty() { return calories; }
    public void setCalories(int calories) { this.calories.set(calories); }
}
