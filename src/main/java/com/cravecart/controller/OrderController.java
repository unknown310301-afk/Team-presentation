package com.cravecart.controller;
import com.cravecart.model.*;
import com.cravecart.repository.CustomerOrderRepository;
import com.cravecart.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/orders")
public class OrderController {
 private final CustomerOrderRepository orders;private final OrderService service;
 public OrderController(CustomerOrderRepository orders,OrderService service){this.orders=orders;this.service=service;}
 @GetMapping public List<CustomerOrder> all(){return orders.findAll();}
 @GetMapping("/{id}") public CustomerOrder one(@PathVariable long id){return orders.findById(id);}
 @PostMapping public CustomerOrder place(@RequestBody OrderService.PlaceOrderRequest r){return service.place(r);}
 @PutMapping("/{id}/status") public CustomerOrder status(@PathVariable long id,@RequestParam OrderStatus status){return service.updateStatus(id,status);}
 @DeleteMapping("/{id}") public void delete(@PathVariable long id){orders.delete(id);}
 @GetMapping("/queue") public Map<String,Object> queue(){return Map.of("message","FIFO incoming-order queue snapshot","orderIds",service.queueSnapshot());}
}
