package com.cravecart.model;
public class Restaurant {
 private Long id; private String name,cuisine,address;
 public Restaurant(){} public Restaurant(Long id,String name,String cuisine,String address){this.id=id;this.name=name;this.cuisine=cuisine;this.address=address;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getCuisine(){return cuisine;} public void setCuisine(String v){cuisine=v;}
 public String getAddress(){return address;} public void setAddress(String v){address=v;}
}
