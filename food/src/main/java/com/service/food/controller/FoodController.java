package com.service.food.controller;

import com.service.food.model.Food;
import com.service.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping(value = "/getFood/{id}")
    public Food getFoodById(@PathVariable long id){
        return foodService.getFoodById(id);
    }

    @GetMapping(value = "/getFood/all")
    public List<Food> getFood(){
        return foodService.getFood();
    }

    @GetMapping(value = "/getFoodByPrice/>{price}")
    public List<Food> getFoodByPriceGreater(@PathVariable int price){
        return foodService.getFoodByPriceGreater(price);
    }

    @GetMapping(value = "/getFoodByPrice/<{price}")
    public List<Food> getFoodByPriceLess(@PathVariable int price){
        return foodService.getFoodByPriceLess(price);
    }

    @PostMapping(value = "/insertFood")
    public String insertFood(@RequestBody Food food){
        return foodService.insertFood(food);
    }

    @PutMapping(value = "updateFood/{id}")
    public String updateFood(@PathVariable long id, @RequestBody Food food){
        return foodService.updateFood(id, food);
    }

    @DeleteMapping(value = "/deleteFood/{id}")
    public String deleteFood(@PathVariable long id){
        return foodService.deleteFood(id);
    }

    @DeleteMapping(value = "/deleteFood/all")
    public String deleteAllFood(){
        return foodService.deleteAllFood();
    }
}
