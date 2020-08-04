package main.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {

    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleDoubleProperty protein = new SimpleDoubleProperty();
    public SimpleDoubleProperty fats = new SimpleDoubleProperty();
    public SimpleDoubleProperty carbs = new SimpleDoubleProperty();
    public SimpleIntegerProperty calories = new SimpleIntegerProperty();
    public SimpleStringProperty weight = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.calories.set(id);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public double getProtein() {
        return protein.get();
    }
    public void setProtein(double protein) {
        this.protein.set(protein);
    }

    public double getFats() {
        return fats.get();
    }
    public void setFats(double fats) {
        this.fats.set(fats);
    }

    public double getCarbs() {
        return carbs.get();
    }
    public void setCarbs(double carbs) {
        this.carbs.set(carbs);
    }

    public int getCalories() {
        return calories.get();
    }
    public void setCalories(int calories) {
        this.calories.set(calories);
    }

    public String getWeight() {
        return weight.get();
    }
    public void setWeight(String weight) {
        this.weight.set(weight);
    }
}
