-- name: db-get-all-departments
-- Get all departments
select
-- Department info
d.id, d.department, d.managerid,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email'
from departments d left join employees e on d.managerid = e.id
order by d.department


-- name: db-get-all-departments-with-employees
-- Get all department with employees
select
-- Department info
d.id, d.department, d.managerid,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email',
-- Employee info
emps.*
from departments d left join employees e on d.managerid = e.id left join employees emps on emps.departments_id = d.id
order by d.department, emps.lastname


-- name: db-get-department-by-name
-- Get the department with the given name
select * from departments where department = :department


-- name: db-get-department-by-id
-- Get the department with the given id
select
-- Department info
d.id, d.department, d.managerid,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email'
from departments d left join employees e on d.managerid = e.id
where d.id = :id
order by department

-- name: db-get-department-by-id-with-employees
-- Get the department with the given id and include the department employees

select
-- Department info
d.id, d.department, d.managerid,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email',
-- Employee info
emps.*
from departments d left join employees e on d.managerid = e.id left join employees emps on emps.departments_id = d.id
where d.id = :id
order by d.department, emps.lastname


-- name: db-insert-department<!
-- Insert a new department
insert into departments (department, managerid) values (:department, :managerid)

-- name: db-update-department!
-- Update department details
update departments
set department = :department, managerid = :managerid
where id = :id

-- name: db-delete-department!
-- Delete the department with the given id
delete from departments where id = :id

-- name: db-delete-all-departments!
-- Delete all the departments
delete from departments


-- name: db-get-all-employees
-- Get all employees
select * from employees order by lastname


-- name: db-get-employee-by-id
-- Get the employee with the given id
select
-- Employee info
e.*,
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from employees e left join employees m on e.managerid = m.id
where e.id = :id

-- name: db-insert-employee<!
-- Insert a new employee
insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid,dob,salt,password,this_year_opening,this_year_remaining,next_year_opening,next_year_remaining)
values (:firstname,:lastname,:email,:startdate,:enddate,:departments_id,:managerid,:dob,:salt,:password,:this_year_opening,:this_year_remaining,:next_year_opening,:next_year_remaining)

-- name: db-update-employee!
-- Update department details
update employees
set
  firstname = :firstname,
  lastname = :lastname,
  email = :email,
  startdate = :startdate,
  enddate = :enddate,
  departments_id = :departments_id,
  managerid = :managerid,
  dob = :dob,
  this_year_opening = :this_year_opening,
  this_year_remaining = :this_year_remaining,
  next_year_opening = :next_year_opening,
  next_year_remaining = :next_year_remaining

where id = :id


-- name: db-delete-employee!
-- Delete the employee with the given id
delete from employees where id = :id

-- name: db-delete-all-employees!
-- Delete all the employees
delete from employees

