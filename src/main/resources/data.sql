CREATE TABLE user1 (
  id BIGINT NOT NULL,
  age BIGINT,
  name VARCHAR(255),
  PRIMARY KEY (id)
);

INSERT INTO user1 (id, name, age) VALUES (1, 'raj', 22);
INSERT INTO user1 (id, name, age) VALUES (2, 'deepak', 21);
