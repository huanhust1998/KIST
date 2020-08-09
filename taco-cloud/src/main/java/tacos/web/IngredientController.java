package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.entity.IngredientTaco;
import tacos.repository.IngredientTacoRepository;


@Slf4j
@Controller
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientTacoRepository ingredientRepository;
    @Autowired
    public IngredientController(IngredientTacoRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("ingredient",new IngredientTaco(null, null, null));
        return "addIngredient";
    }
    @PostMapping
    public String addIngerdient(IngredientTaco ingredient, Model model){
        ingredientRepository.save(ingredient);
        model.addAttribute(ingredient);
        log.info("Ingredient saved: " + ingredient);
        return "addIngredientSuccess";
    }
}
