package com.service.food.repository;

import com.service.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepo extends JpaRepository<Food, Long> {
    List<Food> findByfoodPriceLessThan(Integer foodPrice);
    List<Food> findByfoodPriceGreaterThan(Integer foodPrice);
    Food findByFoodName(String foodName);
    boolean existsByFoodName(String  foodName);
    @Query("SELECT f.foodName FROM Food f")
    List<String> findAllFoodNames();
}
