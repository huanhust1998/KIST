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
import tacos.repository.OrderRepository;

import javax.validation.Valid;

@SessionAttributes("orderForm")
@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderRepository orderRepository;
    @Autowired
    public OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("orderForm", new Order(null,null,null,null,null,null,null,null));
        return "orderForm";
    }

    @PostMapping("/add")
    public String processOrder(Order order,Model model) {
        orderRepository.save(order);
        model.addAttribute("orders",orderRepository.findAll());
        log.info("Order saved: " + order);
        return "resultset";
    }
}
