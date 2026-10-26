package com.cravecart.repository;
import com.cravecart.model.FoodItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class FoodItemRepository {
 private final JdbcTemplate jdbc;
 public FoodItemRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
 private final RowMapper<FoodItem> mapper=(rs,n)->new FoodItem(rs.getLong("id"),rs.getString("name"),rs.getString("category"),rs.getBigDecimal("price"),rs.getString("emoji"),rs.getString("description"),rs.getBoolean("available"));
 public List<FoodItem> findAll(){return jdbc.query("SELECT * FROM food_items ORDER BY id",mapper);}
 public List<FoodItem> searchName(String name){return jdbc.query("SELECT * FROM food_items WHERE LOWER(name) LIKE LOWER(?) ORDER BY id",mapper,"%"+name+"%");}
 public List<FoodItem> searchCategory(String category){return jdbc.query("SELECT * FROM food_items WHERE LOWER(category)=LOWER(?) ORDER BY id",mapper,category);}
 public FoodItem findById(long id){return jdbc.query("SELECT * FROM food_items WHERE id=?",mapper,id).stream().findFirst().orElseThrow(()->new java.util.NoSuchElementException("Food item not found: "+id));}
 public FoodItem save(FoodItem f){
  if(f.getId()==null){jdbc.update("INSERT INTO food_items(name,category,price,emoji,description,available) VALUES(?,?,?,?,?,?)",f.getName(),f.getCategory(),f.getPrice(),f.getEmoji(),f.getDescription(),f.isAvailable());}
  else {int n=jdbc.update("UPDATE food_items SET name=?,category=?,price=?,emoji=?,description=?,available=? WHERE id=?",f.getName(),f.getCategory(),f.getPrice(),f.getEmoji(),f.getDescription(),f.isAvailable(),f.getId());if(n==0)throw new java.util.NoSuchElementException("Food item not found: "+f.getId());}
  return f.getId()==null?jdbc.query("SELECT * FROM food_items WHERE id=LAST_INSERT_ID()",mapper).get(0):findById(f.getId());
 }
 public void delete(long id){if(jdbc.update("DELETE FROM food_items WHERE id=?",id)==0)throw new java.util.NoSuchElementException("Food item not found: "+id);}
}
