package tacos.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;
import tacos.entity.Order;
import tacos.repository.JdbcOrderRepository;

import javax.validation.Valid;

@SessionAttributes("orderForm")
@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    private JdbcOrderRepository jdbcOrderRepository;
    @Autowired
    public OrderController(JdbcOrderRepository jdbcOrderRepository){
        this.jdbcOrderRepository = jdbcOrderRepository;
    }
    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("orderForm", new Order(null,null,null,null,null,null,null,null));
        return "orderForm";
    }

    @PostMapping("/add")
    public String processOrder(@Valid Order order, Errors errors,Model model) {
        if(errors.hasErrors()){
            return "orderForm";
        }
        jdbcOrderRepository.save(order);
        model.addAttribute("orders",jdbcOrderRepository.findAll());
        log.info("Order saved: " + order);
        return "resultset";
    }
}
