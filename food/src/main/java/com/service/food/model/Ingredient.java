package com.service.food.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;
    private String ingredientName;
    private Integer numberOfIngredient;
    private Integer usedIngredient = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getNumberOfIngredient() {
        return numberOfIngredient;
    }

    public void setNumberOfIngredient(Integer numberOfIngredient) {
        this.numberOfIngredient = numberOfIngredient;
    }

    public Integer getUsedIngredient() {
        return usedIngredient;
    }

    public void setUsedIngredient(Integer usedIngredient) {
        this.usedIngredient = usedIngredient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

//    @ManyToOne
//    private Food food;
}
