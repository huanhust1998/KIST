package tacos.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;
import tacos.entity.Taco;
import tacos.entity.IngredientTaco;
import tacos.entity.IngredientTaco.Type;
import tacos.repository.IngredientTacoRepository;

import javax.validation.Valid;

@SessionAttributes("design")
@Slf4j
@Controller
@RequestMapping("/designtaco")
public class DesignTacoController {
    private final IngredientTacoRepository ingredientRepository;
    @Autowired
    public DesignTacoController(IngredientTacoRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }
    @GetMapping
    public String showDesignForm(Model model) {
        List<IngredientTaco> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        Type[] types = IngredientTaco.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("taco", new Taco());
        return "designtaco";
    }
    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors){
        if(errors.hasErrors()){
            return "designtaco";
        }
        log.info("Processing design: " + taco);
        return "redirect:/orders/current";
    }
    private List<IngredientTaco> filterByType(List<IngredientTaco> ingredients, Type type) {
        List<IngredientTaco> ingrList = new ArrayList<IngredientTaco>();
        for (IngredientTaco ingredient : ingredients) {
            if (ingredient.getType().equals(type))
                ingrList.add(ingredient);
        }
        return ingrList;
    }
}