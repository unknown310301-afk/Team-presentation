package com.cravecart.service;
import com.cravecart.model.*;
import com.cravecart.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
@Service
public class OrderService {
 private final FoodItemRepository foods; private final CustomerOrderRepository orders;
 private final Queue<Long> incomingOrderQueue=new ConcurrentLinkedQueue<>(); private final BillCalculator calculator=new BillCalculator();
 public OrderService(FoodItemRepository foods,CustomerOrderRepository orders){this.foods=foods;this.orders=orders;}
 public record RequestedItem(Long foodItemId,int quantity){}
 public record PlaceOrderRequest(String customerName,String customerEmail,String deliveryAddress,List<RequestedItem> items){}
 @Transactional
 public CustomerOrder place(PlaceOrderRequest r){
  if(r.customerName()==null||r.customerName().isBlank())throw new IllegalArgumentException("Customer name is required.");
  if(r.customerEmail()==null||!r.customerEmail().contains("@"))throw new IllegalArgumentException("Enter a valid email.");
  if(r.deliveryAddress()==null||r.deliveryAddress().isBlank())throw new IllegalArgumentException("Delivery address is required.");
  if(r.items()==null||r.items().isEmpty())throw new IllegalArgumentException("Your cart is empty.");
  List<OrderItem> lines=new ArrayList<>();BigDecimal subtotal=BigDecimal.ZERO;
  for(RequestedItem ri:r.items()){
   if(ri.quantity()<1||ri.quantity()>50)throw new IllegalArgumentException("Quantity must be between 1 and 50.");
   FoodItem f=foods.findById(ri.foodItemId());if(!f.isAvailable())throw new IllegalArgumentException(f.getName()+" is unavailable.");
   lines.add(new OrderItem(null,f.getId(),f.getName(),ri.quantity(),f.getPrice()));
   subtotal=subtotal.add(f.getPrice().multiply(BigDecimal.valueOf(ri.quantity())));
  }
  subtotal=subtotal.setScale(2,java.math.RoundingMode.HALF_UP);BigDecimal tax=calculator.tax(subtotal);
  CustomerOrder order=new CustomerOrder(null,r.customerName(),r.customerEmail(),r.deliveryAddress(),OrderStatus.PLACED,subtotal,tax,BillCalculator.DELIVERY_CHARGE,calculator.total(subtotal),java.time.LocalDateTime.now(),lines);
  CustomerOrder saved=orders.save(order,lines);incomingOrderQueue.offer(saved.getId());return saved;
 }
 public Queue<Long> queueSnapshot(){return new LinkedList<>(incomingOrderQueue);}
 public CustomerOrder updateStatus(long id,OrderStatus status){return orders.updateStatus(id,status);}
}
