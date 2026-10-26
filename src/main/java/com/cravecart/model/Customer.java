package com.cravecart.model;
public class Customer {
 private Long id; private String name,email,address;
 public Customer(){} public Customer(Long id,String name,String email,String address){this.id=id;this.name=name;this.email=email;this.address=address;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getAddress(){return address;} public void setAddress(String v){address=v;}
}
