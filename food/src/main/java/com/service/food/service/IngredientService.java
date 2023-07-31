package com.service.food.service;

import com.service.food.exceptionHandler.ServiceValidationException;
import com.service.food.model.Food;
import com.service.food.model.Ingredient;
import com.service.food.repository.FoodRepo;
import com.service.food.repository.IngredientRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IngredientService {

    Logger logger = LoggerFactory.getLogger(FoodService.class);

    @Autowired
    private IngredientRepo ingredientRepo;
    @Autowired
    private FoodRepo foodRepo;

    public Ingredient getIngredientById(long id) {
        if (ingredientRepo.findById(id).isPresent()) {
            Ingredient ingredient = ingredientRepo.findById(id).get();
            logger.info("Retrieved ingredient with id " + id);
            return ingredient;
        } else {
            logger.error("Ingredient with id " + id + " not found.");
            throw new ServiceValidationException("Ingredient with id " + id + " not found.", 404);
        }
    }

    public List<Ingredient> getIngredient() {
        List<Ingredient> ingredient = ingredientRepo.findAll();
        logger.info("Retrieved all ingredient.");
        return ingredient;
    }

    public String insertIngredient(Ingredient ingredient) {
        LocalDate limitDate = LocalDate.now().plusDays(7);

        if(ingredient.getDate().isBefore(LocalDate.now()) || ingredient.getDate().isAfter(limitDate)){
            logger.error("Unable to add stock outside the specified period.");
            throw new ServiceValidationException("Unable to add stock outside the specified period.", 422);
        }

        ingredientRepo.save(ingredient);
        logger.info("Inserted ingredient with id " + ingredient.getId());
        return "Inserted ingredient with id " + ingredient.getId();
    }

    public String updateIngredient(long id, Ingredient ingredient) {
        if (ingredientRepo.findById(id).isPresent()) {

            Ingredient updatedIngredient = ingredientRepo.findById(id).get();
            logger.info("Retrieved ingredient with id " + id);

            if (ingredient.getIngredientName() != null) {
                logger.info("Get ingredient name");
                updatedIngredient.setIngredientName(ingredient.getIngredientName());
                logger.info("Set ingredient name");
            }
            if (ingredient.getNumberOfIngredient() != null) {
                logger.info("Get number of ingredient");
                updatedIngredient.setNumberOfIngredient(ingredient.getNumberOfIngredient());
                logger.info("Set number of ingredient");
            }
            if (ingredient.getDate() != null) {
                logger.info("Get date of ingredient");
                updatedIngredient.setDate(ingredient.getDate());
                logger.info("Set date of ingredient");
            }
            ingredientRepo.save(updatedIngredient);
            logger.info("Updated ingredient with the id: " + id);
            return "Updated ingredient with the id: " + id;
        } else {
            logger.error("Ingredient with id " + id + " not found.");
            throw new ServiceValidationException("Ingredient with id " + id + " not found.", 404);
        }
    }

    public boolean checkStock(String foodName, int foodAmount) {
        int i = 0;
        LocalDate now = LocalDate.now();
        logger.info("Find " + foodName + " in DB");
        Food food = foodRepo.findByFoodName(foodName);
        logger.info("Retrieved food with name " + foodName);

        for (String ingredient : food.getIngredient()) {
            if (ingredientRepo.findByIngredientNameAndDate(ingredient, now) != null) {
                logger.info("Retrieved ingredient with name " + ingredient + " and date " + now);
                if (ingredientRepo.findByIngredientNameAndDate(ingredient, now).getNumberOfIngredient() < foodAmount)
                    i++;
            } else {
                logger.error("Ingredient with name: " + ingredient + " and date " + now + " not found.");
                throw new ServiceValidationException("Ingredient with name: " + ingredient + " and date " + now + " not found.", 404);
            }
        }

        if (i > 0)
            return false;
        else
            return true;
    }

    public void updateIngredientByOrder(String foodName, int foodAmount) {
        Ingredient updatedIngredient;
        logger.info("Find " + foodName + " in DB");
        Food updateFood = foodRepo.findByFoodName(foodName);
        logger.info("Retrieved food with name " + foodName);

        for (String ingredient : updateFood.getIngredient()) {
            updatedIngredient = ingredientRepo.findByIngredientNameAndDate(ingredient, LocalDate.now());
            logger.info(String.valueOf(updatedIngredient.getNumberOfIngredient()));
            updatedIngredient.setNumberOfIngredient(updatedIngredient.getNumberOfIngredient() - foodAmount);
            updatedIngredient.setUsedIngredient(updatedIngredient.getUsedIngredient() + foodAmount);
            ingredientRepo.save(updatedIngredient);
        }
    }

    public String getMostUsedIngredient(Ingredient ingredient) {
        LocalDate date = ingredient.getDate();
        List<Ingredient> ingredientList;

        if(!ingredientRepo.findByDate(date).isEmpty()){
            ingredientList = ingredientRepo.findByDate(date);
        }else{
            logger.error("No ingredient with date " + date);
            throw new ServiceValidationException("No ingredient with date " + date, 404);
        }

        int size = ingredientList.size();
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (ingredientList.get(i).getUsedIngredient() > max)
                max = ingredientList.get(i).getUsedIngredient();
        }
        String maxIngredient = ingredientRepo.findIngredientNameByUsedIngredient(max);
        return maxIngredient + ": " + max + " used";
    }

    public String deleteIngredient(long id) {
        if (ingredientRepo.findById(id).isPresent()) {
            logger.info("Retrieved ingredient with id " + id);
            ingredientRepo.delete(ingredientRepo.findById(id).get());
            logger.info("Deleted ingredient with the id: " + id);
            return "Deleted ingredient with the id: " + id;
        } else {
            logger.error("Ingredient with id " + id + " not found.");
            throw new ServiceValidationException("Ingredient with id " + id + " not found.", 404);
        }
    }

    public String deleteIngredientByDate(Ingredient ingredient) {
        LocalDate date = ingredient.getDate();
        List<Ingredient> ingredientList;

        if(!ingredientRepo.findByDate(date).isEmpty()){
            ingredientList = ingredientRepo.findByDate(date);
            ingredientRepo.deleteAllInBatch(ingredientList);
            logger.info("Deleted ingredient by date: " + date + ".");
        }else{
            logger.error("No ingredient with date " + date);
            throw new ServiceValidationException("No ingredient with date " + date, 404);
        }

        return "Deleted ingredient by date: " + date + ".";
    }

    public String deleteAllIngredient() {
        ingredientRepo.deleteAll();
        logger.info("Deleted all ingredient.");
        return "Deleted all  ingredient.";
    }
}
