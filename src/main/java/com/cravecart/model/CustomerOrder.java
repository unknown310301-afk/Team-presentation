package com.cravecart.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class CustomerOrder {
 private Long id; private String customerName,customerEmail,deliveryAddress; private OrderStatus status;
 private BigDecimal subtotal,tax,deliveryCharge,total; private LocalDateTime createdAt; private List<OrderItem> items=new ArrayList<>();
 public CustomerOrder(){}
 public CustomerOrder(Long id,String name,String email,String address,OrderStatus status,BigDecimal subtotal,BigDecimal tax,BigDecimal deliveryCharge,BigDecimal total,LocalDateTime createdAt,List<OrderItem> items){
  this.id=id;this.customerName=name;this.customerEmail=email;this.deliveryAddress=address;this.status=status;this.subtotal=subtotal;this.tax=tax;this.deliveryCharge=deliveryCharge;this.total=total;this.createdAt=createdAt;this.items=items==null?new ArrayList<>():items;
 }
 public Long getId(){return id;} public String getCustomerName(){return customerName;} public String getCustomerEmail(){return customerEmail;}
 public String getDeliveryAddress(){return deliveryAddress;} public OrderStatus getStatus(){return status;} public BigDecimal getSubtotal(){return subtotal;}
 public BigDecimal getTax(){return tax;} public BigDecimal getDeliveryCharge(){return deliveryCharge;} public BigDecimal getTotal(){return total;}
 public LocalDateTime getCreatedAt(){return createdAt;} public List<OrderItem> getItems(){return items;}
}
