CREATE TABLE category(
category_id INT NOT NULL AUTO_INCREMENT, 
category VARCHAR(100) NOT NULL,
tag VARCHAR(100) NOT NULL,
status TINYINT NOT NULL, 

PRIMARY KEY (category_id), 
CHECK (status IN (0,1))
);


CREATE UNIQUE INDEX ux_category ON category(category);
CREATE UNIQUE INDEX ux_tag ON category(tag);


INSERT INTO category (category, tag, status)
VALUES
  ('Electrónica', 'ELEC', 1),
  ('Ropa', 'CLOT', 1),
  ('Hogar', 'HOME', 1),
  ('Deportes', 'SPORT', 0);

SELECT * FROM category;
