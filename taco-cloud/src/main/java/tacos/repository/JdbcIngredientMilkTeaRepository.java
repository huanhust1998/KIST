package tacos.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.entity.IngredientMilkTea;


import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientMilkTeaRepository implements IngredientMilkTeaRepository{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcIngredientMilkTeaRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<IngredientMilkTea> findAll() {
        return jdbcTemplate.query("select id, name, type from IngredientMilkTea",
                this::mapRowToIngredient);
    }

    @Override
    public IngredientMilkTea findById(String id) {
        return jdbcTemplate.queryForObject(
                "select id, name, type from IngredientMilkTea where id=?",
                this::mapRowToIngredient, id);
    }

    @Override
    public IngredientMilkTea save(IngredientMilkTea ingredientMilkTea) {
        jdbcTemplate.update("insert into IngredientMilkTea (id, name, type) values (?, ?, ?)",
                ingredientMilkTea.getId(),
                ingredientMilkTea.getName(),
                ingredientMilkTea.getType().toString());
        return ingredientMilkTea;
    }
    private IngredientMilkTea mapRowToIngredient(ResultSet rs, int rowNum)throws SQLException {
        return new IngredientMilkTea(
                rs.getString("id"),
                rs.getString("name"),
                IngredientMilkTea.Type.valueOf(rs.getString("type")));
    }
}
