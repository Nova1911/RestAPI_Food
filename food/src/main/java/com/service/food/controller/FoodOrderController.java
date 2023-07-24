package com.service.food.controller;

import com.service.food.exceptionHandler.ServiceValidationException;
import com.service.food.model.FoodOrder;
import com.service.food.repository.FoodRepo;
import com.service.food.service.FoodOrderService;
import com.service.food.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FoodOrderController {

    @Autowired
    private FoodOrderService foodOrderService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private FoodRepo foodRepo;

    @GetMapping(value = "/getFoodOrder/all")
    public List<FoodOrder> getFoodOrder(){
        return foodOrderService.getFoodOrder();
    }

    @GetMapping(value = "/getFoodOrderByDate")
    public List<FoodOrder> getFoodOrderByDate(@RequestBody FoodOrder foodOrder){
        return foodOrderService.getFoodOrderByDate(foodOrder);
    }

    @PostMapping(value = "/insertFoodOrder")
    public String insertFoodOrder(@RequestBody FoodOrder foodOrder){
        if(foodRepo.existsByFoodName(foodOrder.getFoodName())){
            if(ingredientService.checkStock(String.valueOf(foodOrder.getFoodName()), foodOrder.getAmount())){
                ingredientService.updateIngredientByOrder(String.valueOf(foodOrder.getFoodName()), foodOrder.getAmount());
                return foodOrderService.insertFoodOrder(foodOrder);
            }else{
                return "Out of stock";
            }
        }else{
            throw new ServiceValidationException(foodOrder.getFoodName() + " is not on the mennu.", 404);
        }
    }

    @GetMapping(value = "/getBestSellingFood")
    public String getBestSellingFood(@RequestBody FoodOrder foodOrder){
        return foodOrderService.getBestSellingFood(foodOrder);
    }

    @DeleteMapping(value = "/deleteFoodOrder/all")
    public String deleteAllFoodOrder(){
        return foodOrderService.deleteAllFoodOrder();
    }
}
