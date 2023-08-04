package com.service.food.repository;

import com.service.food.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FoodOrderRepo extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByFoodNameAndDate(String foodName, LocalDate date);

    List<FoodOrder> findByDate(LocalDate date);
}
