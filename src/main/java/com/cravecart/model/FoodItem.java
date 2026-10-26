package com.cravecart.model;
import java.math.BigDecimal;
public class FoodItem {
 private Long id; private String name,category,emoji,description; private BigDecimal price; private boolean available;
 public FoodItem() {}
 public FoodItem(Long id,String name,String category,BigDecimal price,String emoji,String description,boolean available){
  this.id=id;this.name=name;this.category=category;this.price=price;this.emoji=emoji;this.description=description;this.available=available;
 }
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
 public String getEmoji(){return emoji;} public void setEmoji(String v){emoji=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public boolean isAvailable(){return available;} public void setAvailable(boolean v){available=v;}
}
