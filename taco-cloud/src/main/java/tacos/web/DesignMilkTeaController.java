package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.entity.IngredientMilkTea;
import tacos.entity.MilkTea;
import tacos.repository.IngredientMilkTeaRepository;


import javax.swing.text.TabableView;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/designmilktea")
public class DesignMilkTeaController {

    private final IngredientMilkTeaRepository ingredientMilkTeaRepository;
    @Autowired
    public DesignMilkTeaController(IngredientMilkTeaRepository ingredientMilkTeaRepository){
        this.ingredientMilkTeaRepository = ingredientMilkTeaRepository;
    }
    @GetMapping
    public String showDesignForm(Model model){
        List<IngredientMilkTea> ingredientMilkTeas = new ArrayList<>();
        ingredientMilkTeaRepository.findAll().forEach(ingredientMilkTeas :: add);
        IngredientMilkTea.Type[] types = IngredientMilkTea.Type.values();
        for(IngredientMilkTea.Type type: types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredientMilkTeas,type));
        }
        model.addAttribute("milktea", new MilkTea());
        return "designmilktea";
    }

    @PostMapping
    public String processDesign(MilkTea milkTea){
        log.info("Processing design: " + milkTea);
        return "redirect:/orders/current";
    }
    private List<IngredientMilkTea> filterByType(List<IngredientMilkTea> ingredients, IngredientMilkTea.Type type) {
        List<IngredientMilkTea> ingrList = new ArrayList<IngredientMilkTea>();
        for (IngredientMilkTea ingredient : ingredients) {
            if (ingredient.getType().equals(type))
                ingrList.add(ingredient);
        }
        return ingrList;
    }
}
