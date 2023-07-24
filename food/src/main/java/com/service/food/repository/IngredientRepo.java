package com.service.food.repository;

import com.service.food.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {
    Ingredient findByIngredientNameAndDate(String ingredientName, LocalDate date);
    @Query("SELECT i.ingredientName FROM Ingredient i WHERE i.usedIngredient = :usedIngredient")
    String findIngredientNameByUsedIngredient(@Param("usedIngredient") int usedIngredient);
    List<Ingredient> findByDate(LocalDate date);
}
