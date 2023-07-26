package com.service.food.controller;

import com.service.food.repository.FoodRepo;
import com.service.food.model.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private FoodRepo foodRepo;

    @GetMapping(value = "/")
    public  String getPage(){
        return "Welcome";
    }

    @GetMapping(value = "/food")
    public List<Food> getFood(){
        return foodRepo.findAll();
    }

    @PostMapping(value = "/save")
    public String saveFood(@RequestBody Food food){
        foodRepo.save(food);
        return "Saved";
    }

    @PutMapping(value = "update/{id}")
    public String updateFood(@PathVariable long id, @RequestBody Food food){
        Food updatedFood = foodRepo.findById(id).get();
        updatedFood.setFoodName(food.getFoodName());
        updatedFood.setFoodPrice(food.getFoodPrice());
        updatedFood.setFoodType(food.getFoodType());
        foodRepo.save(updatedFood);
        return "Updated";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteFood(@PathVariable long id){
        Food deleteFood = foodRepo.findById(id).get();
        foodRepo.delete(deleteFood);
        return "Delete food with the id:" + id;
    }
    
    //JJ
}
