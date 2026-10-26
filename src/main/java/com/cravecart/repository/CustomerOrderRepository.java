package com.cravecart.repository;
import com.cravecart.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class CustomerOrderRepository {
 private final JdbcTemplate jdbc;
 public CustomerOrderRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
 private final RowMapper<CustomerOrder> mapper=(rs,n)->new CustomerOrder(rs.getLong("id"),rs.getString("customer_name"),rs.getString("customer_email"),rs.getString("delivery_address"),OrderStatus.valueOf(rs.getString("status")),rs.getBigDecimal("subtotal"),rs.getBigDecimal("tax"),rs.getBigDecimal("delivery_charge"),rs.getBigDecimal("total"),rs.getTimestamp("created_at").toLocalDateTime(),findItems(rs.getLong("id")));
 private List<OrderItem> findItems(long orderId){return jdbc.query("SELECT * FROM order_items WHERE order_id=? ORDER BY id",(rs,n)->new OrderItem(rs.getLong("id"),rs.getLong("food_item_id"),rs.getString("food_name"),rs.getInt("quantity"),rs.getBigDecimal("unit_price")),orderId);}
 public CustomerOrder save(CustomerOrder o,List<OrderItem> items){
  jdbc.update("INSERT INTO orders(customer_name,customer_email,delivery_address,status,subtotal,tax,delivery_charge,total) VALUES(?,?,?,?,?,?,?,?)",
    o.getCustomerName(),o.getCustomerEmail(),o.getDeliveryAddress(),o.getStatus().name(),o.getSubtotal(),o.getTax(),o.getDeliveryCharge(),o.getTotal());
  Long id=jdbc.queryForObject("SELECT LAST_INSERT_ID()",Long.class);
  for(OrderItem i:items)jdbc.update("INSERT INTO order_items(order_id,food_item_id,food_name,quantity,unit_price) VALUES(?,?,?,?,?)",id,i.getFoodItemId(),i.getFoodName(),i.getQuantity(),i.getUnitPrice());
  return findById(id);
 }
 public List<CustomerOrder> findAll(){List<Long> ids=jdbc.query("SELECT id FROM orders ORDER BY id DESC",(rs,n)->rs.getLong(1));List<CustomerOrder> out=new ArrayList<>();for(Long id:ids)out.add(findById(id));return out;}
 public CustomerOrder findById(long id){return jdbc.query("SELECT * FROM orders WHERE id=?",mapper,id).stream().findFirst().orElseThrow(()->new NoSuchElementException("Order not found: "+id));}
 public CustomerOrder updateStatus(long id,OrderStatus status){if(jdbc.update("UPDATE orders SET status=? WHERE id=?",status.name(),id)==0)throw new NoSuchElementException("Order not found: "+id);return findById(id);}
 public void delete(long id){if(jdbc.update("DELETE FROM orders WHERE id=?",id)==0)throw new NoSuchElementException("Order not found: "+id);}
}
