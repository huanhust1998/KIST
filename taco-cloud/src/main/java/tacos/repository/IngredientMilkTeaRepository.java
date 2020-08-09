package tacos.repository;

import tacos.entity.IngredientMilkTea;

public interface IngredientMilkTeaRepository {
    Iterable<IngredientMilkTea> findAll();
    IngredientMilkTea findById(String id);
    IngredientMilkTea save(IngredientMilkTea ingredientMilkTea);
}
