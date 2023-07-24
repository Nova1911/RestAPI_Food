package com.service.food.service;

import com.service.food.exceptionHandler.ServiceValidationException;
import com.service.food.model.FoodOrder;
import com.service.food.repository.FoodOrderRepo;
import com.service.food.repository.FoodRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodOrderService {

    @Autowired
    private FoodOrderRepo foodOrderRepo;
    @Autowired
    private FoodRepo foodRepo;

    Logger logger = LoggerFactory.getLogger(FoodService.class);

    public List<FoodOrder> getFoodOrder() {
        List<FoodOrder> foodOrder = foodOrderRepo.findAll();
        logger.info("Retrieved all food order.");
        return foodOrder;
    }

    public List<FoodOrder> getFoodOrderByDate(FoodOrder foodOrder) {
        LocalDate date = foodOrder.getDate();
        List<FoodOrder> foodOrderList;

        if(!foodOrderRepo.findByDate(date).isEmpty()){
            foodOrderList = foodOrderRepo.findByDate(date);
            logger.info("Retrieved food order by date " + date + ".");
            return foodOrderList;
        }else {
            logger.error("No food order with date " + date + ".");
            throw new ServiceValidationException("No food order with date " + date + ".", 404);
        }
    }

    public String insertFoodOrder(FoodOrder foodOrder) {
        foodOrder.setDate(LocalDate.now());
        foodOrderRepo.save(foodOrder);
        logger.info("Inserted food order with id " + foodOrder.getId());
        return "Inserted foodOrder with id " + foodOrder.getId();
    }

    public String getBestSellingFood(FoodOrder foodOrder) {
        LocalDate date = foodOrder.getDate();

        if(!foodRepo.findAllFoodNames().isEmpty()){
            List<String> foodNameList = foodRepo.findAllFoodNames();
            List<FoodOrder> foodOrderList;
            Map<String, Integer> foodAmount = new HashMap<>();

            int i = 0, amount = 0;

            for (String foodName : foodNameList) {
                if(!foodOrderRepo.findByFoodNameAndDate(foodName, date).isEmpty()){
                    foodOrderList = foodOrderRepo.findByFoodNameAndDate(foodName, date);
                }else{
                    logger.error("No foodOrder with name " + foodName + " and date " + date);
                    throw new ServiceValidationException("No foodOrder with date " + date, 404);
                }

                for (FoodOrder f : foodOrderList) {
                    amount += f.getAmount();
                }

                foodAmount.put(foodName, amount);

                for (Map.Entry<String, Integer> entry : foodAmount.entrySet()) {
                    String food = entry.getKey();
                    amount = entry.getValue();
                }

                amount = 0;
                i++;
            }

            String foodWithMaxAmount = null;
            int maxAmount = Integer.MIN_VALUE;

            for (Map.Entry<String, Integer> entry : foodAmount.entrySet()) {
                String name = entry.getKey();
                amount = entry.getValue();

                if (amount > maxAmount) {
                    maxAmount = amount;
                    foodWithMaxAmount = name;
                }
            }
            return foodWithMaxAmount + ": " + maxAmount + " sold";
        }else{
            logger.error("No food order.");
            throw new ServiceValidationException("No food order.", 404);
        }


    }

    public String deleteAllFoodOrder() {
        foodOrderRepo.deleteAll();
        logger.info("Deleted all food order.");
        return "Deleted all food order";
    }
}
