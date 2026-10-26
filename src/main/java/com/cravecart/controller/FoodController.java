package com.cravecart.controller;
import com.cravecart.model.FoodItem;
import com.cravecart.repository.FoodItemRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
@RestController @RequestMapping("/api/foods")
public class FoodController {
 private final FoodItemRepository foods;
 public FoodController(FoodItemRepository foods){this.foods=foods;}
 @GetMapping public List<FoodItem> all(@RequestParam(required=false)String search,@RequestParam(required=false)String category){
  if(search!=null&&!search.isBlank())return foods.searchName(search);
  if(category!=null&&!category.isBlank())return foods.searchCategory(category);
  return foods.findAll();
 }
 @GetMapping("/{id}") public FoodItem one(@PathVariable long id){return foods.findById(id);}
 public record FoodInput(@NotBlank String name,@NotBlank String category,@NotNull @DecimalMin("0.01") BigDecimal price,String emoji,String description,Boolean available){}
 @PostMapping public FoodItem create(@Valid @RequestBody FoodInput i){FoodItem f=new FoodItem(null,i.name(),i.category(),i.price(),i.emoji(),i.description(),i.available()==null||i.available());return foods.save(f);}
 @PutMapping("/{id}") public FoodItem update(@PathVariable long id,@Valid @RequestBody FoodInput i){FoodItem f=new FoodItem(id,i.name(),i.category(),i.price(),i.emoji(),i.description(),i.available()==null||i.available());return foods.save(f);}
 @DeleteMapping("/{id}") public void delete(@PathVariable long id){foods.delete(id);}
}
