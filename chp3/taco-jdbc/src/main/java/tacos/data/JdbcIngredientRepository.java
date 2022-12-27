package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;

import tacos.Ingredient;

public class JdbcIngredientRepository implements IngredientRepository {

	private JdbcTemplate jdbc;
	
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("select id, name, type from Ingredient", 
				this::mapRowToIngredient);
	}

	@Override
	public Ingredient findOne(String id) {
		return jdbc.queryForObject(
				"select id, name, type from Ingredient where id=?",
				this::mapRowToIngredient, id);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update("insert into Ingredient(id, name, type) values(?,?,?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType());
		return ingredient;
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) 
		throws SQLException {
			return new Ingredient(
					rs.getString("id"),
					rs.getString("name"),
					Ingredient.Type.valueOf(rs.getString("type")));
		}

}