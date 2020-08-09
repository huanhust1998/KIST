package tacos.repository;

import tacos.entity.Order;

public interface OrderRepository {
    Iterable<Order> findAll();
    Order findById(int id);
    Order save(Order order);
}
