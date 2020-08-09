package tacos.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<Order> findAll() {
        return jdbcTemplate.query("select * from Taco_Order ",this::mapRowToIngredient);
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public Order save(Order order) {
        jdbcTemplate.update("insert into Taco_Order (deliveryName, deliveryStreet,deliveryCity,deliveryState,deliveryZip,ccNumber,ccExpiration,ccCVV) values (?, ?, ?, ?, ?, ?, ?, ?)",
                order.getName(),
                order.getStreet(),
                order.getCity(),
                order.getState(),
                order.getZip(),
                order.getCcNumber(),
                order.getCcExpiration(),
                order.getCcCVV()
                );
        return order;
    }
    private Order mapRowToIngredient(ResultSet rs,int rowNum)throws SQLException {
        return new Order(
                rs.getString("deliveryName"),
                rs.getString("deliveryStreet"),
                rs.getString("deliveryCity"),
                rs.getString("deliveryState"),
                rs.getString("deliveryZip"),
                rs.getString("ccNumber"),
                rs.getString("ccExpiration"),
                rs.getString("ccCVV")
        );
    }
}
