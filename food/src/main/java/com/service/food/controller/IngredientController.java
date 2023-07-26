package com.service.food.controller;

import com.service.food.model.Ingredient;
import com.service.food.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping(value = "/getIngredient/{id}")
    public Ingredient getIngredientById(@PathVariable long id){
        return ingredientService.getIngredientById(id);
    }

    @GetMapping(value = "/getIngredient/all")
    public List<Ingredient> getIngredient(){
        return ingredientService.getIngredient();
    }

    @PostMapping(value = "/insertIngredient")
    public String insertIngredient(@RequestBody Ingredient ingredient){
        return ingredientService.insertIngredient(ingredient);
    }

    @PutMapping(value = "updateIngredient/{id}")
    public String updateIngredient(@PathVariable long id, @RequestBody Ingredient ingredient){
        return ingredientService.updateIngredient(id, ingredient);
    }

    @DeleteMapping(value = "/deleteIngredient/{id}")
    public String deleteIngredient(@PathVariable long id){
        return ingredientService.deleteIngredient(id);
    }

    @DeleteMapping(value = "/deleteIngredient/all")
    public String deleteAllIngredient(){
        return ingredientService.deleteAllIngredient();
    }

    @DeleteMapping(value = "/deleteIngredientByDate")
    public String deleteIngredientByDate(@RequestBody Ingredient ingredient){
        return ingredientService.deleteIngredientByDate(ingredient);
    }

    @GetMapping(value = "/getMostUsedIngredient")
    public String getMostUsedIngredient(@RequestBody Ingredient ingredient){
        return ingredientService.getMostUsedIngredient(ingredient);
    }
}
