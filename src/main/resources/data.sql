INSERT INTO food_items (name,category,price,emoji,description,available)
SELECT 'Classic Burger','Burgers',149.00,'🍔','Juicy burger with fresh lettuce',TRUE
WHERE NOT EXISTS (SELECT 1 FROM food_items WHERE name='Classic Burger');
INSERT INTO food_items (name,category,price,emoji,description,available)
SELECT 'Margherita Pizza','Pizza',249.00,'🍕','Tomato, mozzarella and basil',TRUE
WHERE NOT EXISTS (SELECT 1 FROM food_items WHERE name='Margherita Pizza');
INSERT INTO food_items (name,category,price,emoji,description,available)
SELECT 'Crispy Fries','Sides',99.00,'🍟','Golden, crunchy potato fries',TRUE
WHERE NOT EXISTS (SELECT 1 FROM food_items WHERE name='Crispy Fries');
INSERT INTO food_items (name,category,price,emoji,description,available)
SELECT 'Paneer Wrap','Vegetarian',179.00,'🌯','Spiced paneer with fresh veggies',TRUE
WHERE NOT EXISTS (SELECT 1 FROM food_items WHERE name='Paneer Wrap');
INSERT INTO food_items (name,category,price,emoji,description,available)
SELECT 'Chocolate Shake','Drinks',129.00,'🥤','Creamy chocolate milkshake',TRUE
WHERE NOT EXISTS (SELECT 1 FROM food_items WHERE name='Chocolate Shake');
INSERT INTO food_items (name,category,price,emoji,description,available)
SELECT 'Veggie Bowl','Healthy',199.00,'🥗','Fresh and colourful veggie bowl',TRUE
WHERE NOT EXISTS (SELECT 1 FROM food_items WHERE name='Veggie Bowl');
