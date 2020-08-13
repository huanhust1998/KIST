package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.entity.IngredientTaco;

public interface IngredientRepository extends CrudRepository<IngredientTaco,String> {
    void deleteById(String id);
}
