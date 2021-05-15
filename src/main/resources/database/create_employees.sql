DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
  employee_id SERIAL PRIMARY KEY,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  department_id smallint NOT NULL,
  job_title varchar(50) NOT NULL,
  gender varchar(50) NOT NULL,
  date_of_birth date NOT NULL
);

INSERT INTO employee VALUES (DEFAULT, 'Anton', 'Ziuzikov', 1, 'Junior Java Developer', 'Male', '1992-10-05');
INSERT INTO employee VALUES (DEFAULT, 'Pavel', 'Gutnik', 1, 'Middle Java Developer', 'Male', '1992-09-27');
INSERT INTO employee VALUES (DEFAULT, 'Alexandra', 'Bondareva', 1, 'Senior Java Developer', 'Female', '1989-12-07');
INSERT INTO employee VALUES (DEFAULT, 'James', 'Fitzhoff', 36, 'Full Stack Developer', 'Male', '1983-04-11');
INSERT INTO employee VALUES (DEFAULT, 'Peter', 'Miller', 36, 'Senior Java Developer', 'Male', '1978-02-02');
INSERT INTO employee VALUES (DEFAULT, 'Sarah', 'Smith', 36, 'Human Resourses', 'Female', '1995-05-14');
INSERT INTO employee VALUES (DEFAULT, 'Mishelle', 'Kanimann', 36, 'DevOps', 'Female', '1987-04-22');
INSERT INTO employee VALUES (DEFAULT, 'Ishani', 'Kumar', 19, 'Junior Java Developer', 'Female', '1998-08-29');
INSERT INTO employee VALUES (DEFAULT, 'Salena', 'Arya', 19, 'Human Resourses', 'Female', '1993-05-03');
INSERT INTO employee VALUES (DEFAULT, 'Farid', 'Bakshi', 19, 'Full Stack Developer', 'Male', '1985-02-09');