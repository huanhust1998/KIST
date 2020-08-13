package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tacos.entity.IngredientTaco;
import tacos.repository.IngredientRepository;


@Slf4j
@Controller
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientRepository ingredientRepository;
    @Autowired
    public IngredientController(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }
 //   /ingredient/add
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("ingredient",new IngredientTaco(null, null, null));
        return "addIngredient";
    }
    @PostMapping
    public String addIngredient(IngredientTaco ingredientTaco, Model model){
        ingredientRepository.save(ingredientTaco);
        model.addAttribute("ingredientTaco",ingredientRepository.findAll());
        log.info("Ingredient saved: " + ingredientTaco);
        return "addIngredientSuccess";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes){
        ingredientRepository.deleteById(id);
        //redirectAttributes.addAllAttributes("successMessage", "Deleted contact successfully!");
        return "redirect:/ingredient";
    }
}
