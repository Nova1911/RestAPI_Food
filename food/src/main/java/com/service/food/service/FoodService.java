package com.service.food.service;

import com.service.food.exceptionHandler.ServiceValidationException;
import com.service.food.model.Food;
import com.service.food.model.Ingredient;
import com.service.food.repository.FoodRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    private FoodRepo foodRepo;

    Logger logger = LoggerFactory.getLogger(FoodService.class);

    public Food getFoodById(long id) {
        if (foodRepo.findById(id).isPresent()) {
            Food food = foodRepo.findById(id).get();
            logger.info("Retrieved food with id " + id);
            return food;
        } else {
            logger.error("Food with id " + id + " not found.");
            throw new ServiceValidationException("Food with id " + id + " not found.", 404);
        }
    }

    public List<Food> getFood() {
        List<Food> food = foodRepo.findAll();
        logger.info("Retrieved all food.");
        return food;
    }

    public List<Food> getFoodByPriceGreater(int price) {
        List<Food> food = foodRepo.findByfoodPriceGreaterThan(price);
        logger.info("Retrieved food with price greater than " + price);
        return food;
    }

    public List<Food> getFoodByPriceLess(int price) {
        List<Food> food = foodRepo.findByfoodPriceLessThan(price);
        logger.info("Retrieved food with price less than " + price);
        return food;
    }

    public String insertFood(Food food) {
        List<String> ingredientList = food.getIngredient();
        if(ingredientList.size() != 5){
            logger.info("Must have 5 ingredients.");
            throw new ServiceValidationException("Must have 5 ingredients.", 400);
        }else{
            foodRepo.save(food);
            logger.info("Inserted food with id " + food.getId());
            return "Inserted food with id " + food.getId();
        }
    }

    public String updateFood(long id, Food food) {
        if (foodRepo.findById(id).isPresent()) {

            Food updatedFood = foodRepo.findById(id).get();
            logger.info("Retrieved food with id " + id);

            if (food.getFoodName() != null) {
                logger.info("Get food name");
                updatedFood.setFoodName(food.getFoodName());
                logger.info("Set food name");
            }
            if (food.getFoodPrice() != null) {
                logger.info("Get food price");
                updatedFood.setFoodPrice(food.getFoodPrice());
                logger.info("Set food price");
            }
            if (food.getFoodType() != null) {
                logger.info("Get food type");
                updatedFood.setFoodPrice(food.getFoodPrice());
                logger.info("Set food type");
            }
            foodRepo.save(updatedFood);
            logger.info("Updated food with the id: " + id);
            return "Updated food with the id: " + id;
        } else {
            logger.error("Food with id " + id + " not found.");
            throw new ServiceValidationException("Food with id " + id + " not found.", 404);
        }
    }

    public String deleteFood(long id) {
        if (foodRepo.findById(id).isPresent()) {
            logger.info("Retrieved food with id " + id);
            foodRepo.delete(foodRepo.findById(id).get());
            logger.info("Deleted food with the id: " + id);
            return "Deleted food with the id: " + id;
        } else {
            logger.error("Food with id " + id + " not found.");
            throw new ServiceValidationException("Food with id " + id + " not found.", 404);
        }
    }

    public String deleteAllFood() {
        foodRepo.deleteAll();
        logger.info("Deleted all food.");
        return "Deleted all food";
    }
}
