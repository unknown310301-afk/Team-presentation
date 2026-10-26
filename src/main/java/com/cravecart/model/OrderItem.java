package com.cravecart.model;
import java.math.BigDecimal;
public class OrderItem {
 private Long id,foodItemId; private String foodName; private int quantity; private BigDecimal unitPrice;
 public OrderItem(){} public OrderItem(Long id,Long foodItemId,String foodName,int quantity,BigDecimal unitPrice){this.id=id;this.foodItemId=foodItemId;this.foodName=foodName;this.quantity=quantity;this.unitPrice=unitPrice;}
 public Long getId(){return id;} public Long getFoodItemId(){return foodItemId;} public String getFoodName(){return foodName;}
 public int getQuantity(){return quantity;} public BigDecimal getUnitPrice(){return unitPrice;}
 public BigDecimal getLineTotal(){return unitPrice.multiply(BigDecimal.valueOf(quantity));}
}
