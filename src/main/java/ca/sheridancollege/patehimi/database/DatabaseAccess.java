package ca.sheridancollege.patehimi.database;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.patehimi.beans.ShoppingItem;

@Repository
public class DatabaseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	public ArrayList<ShoppingItem> getAllmy_purchases() {
		ArrayList<ShoppingItem> my_purchases = null;
		String sql = "select * from my_purchases";
		my_purchases = (ArrayList<ShoppingItem>)jdbc.query(sql, new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		return my_purchases;
		}

	public ShoppingItem selectItem(int id) {
		ShoppingItem a = null;
		String sql = "select * from my_purchases where id =:id";
		MapSqlParameterSource ps = new MapSqlParameterSource();
		ps.addValue("id", id);
		ArrayList<ShoppingItem> my_purchases =
		(ArrayList<ShoppingItem>)jdbc.query(sql, ps, new BeanPropertyRowMapper<ShoppingItem>(ShoppingItem.class));
		a = my_purchases.get(0);
		return a;
		}

		public void modifyItem(ShoppingItem a) {
		String sql = "update my_purchases set  name=:name, price=:price, " +
		"description=:description, link=:link where id=:id";
		MapSqlParameterSource ps = new MapSqlParameterSource();
		ps.addValue("id", a.getId());
		ps.addValue("name", a.getName());
		ps.addValue("description", a.getDescription());
		ps.addValue("price", a.getPrice());
		ps.addValue("link", a.getLink());
		jdbc.update(sql, ps);
		}

		public void deleteItem(int id) {
			String sql = "delete from my_purchases where id = :id";
			MapSqlParameterSource ps = new MapSqlParameterSource();
			ps.addValue("id", id);
			jdbc.update(sql, ps);
		}
		public void addItem(ShoppingItem a) {
			String sql = "insert into my_purchases (name,price,description,link) values (:name, :price, :description, :link)";
			MapSqlParameterSource ps = new MapSqlParameterSource();
			ps.addValue("id", a.getId());
			ps.addValue("name", a.getName());
			ps.addValue("description", a.getDescription());
			ps.addValue("price", a.getPrice());
			ps.addValue("link", a.getLink());
			jdbc.update(sql, ps);
			}	
}