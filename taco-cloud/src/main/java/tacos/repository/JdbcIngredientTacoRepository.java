package tacos.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tacos.entity.IngredientTaco;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientTacoRepository implements IngredientTacoRepository {

    private JdbcTemplate jdbc;
    @Autowired
    public JdbcIngredientTacoRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<IngredientTaco> findAll() {
        return jdbc.query("select id, name, type from IngredientTaCo",
                this::mapRowToIngredient);
    }

    @Override
    public IngredientTaco findById(String id) {
        return jdbc.queryForObject(
                "select id, name, type from IngredientTaCo where id=?",
                this::mapRowToIngredient, id);
    }

    @Override
    public IngredientTaco save(IngredientTaco ingredient) {
        jdbc.update("insert into IngredientTaCo (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }
    private IngredientTaco mapRowToIngredient(ResultSet rs, int rowNum)throws SQLException {
        return new IngredientTaco(
                rs.getString("id"),
                rs.getString("name"),
                IngredientTaco.Type.valueOf(rs.getString("type")));
    }
}
