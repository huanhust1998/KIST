package tacos.repository;

import tacos.entity.IngredientTaco;

public interface IngredientTacoRepository {
    Iterable<IngredientTaco> findAll();
    IngredientTaco findById(String id);
    IngredientTaco save(IngredientTaco ingredient);
}
