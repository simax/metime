-- name: db-get-all-departments
-- Get all departments
select
-- Department info
d.id, d.id as 'departmentid', d.department, d.managerid,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email'
from departments d left join employees e on d.managerid = e.id
order by d.department


-- name: db-get-all-departments-with-employees
-- Get all department with employees

select
-- Department info
d.id, d.id as 'departmentid', d.id as 'departmentid', d.department, d.managerid,
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
select
id,
firstname,
lastname,
email,
departments_id,
managerid,
strftime('%d-%m-%Y', dob) as 'dob',
strftime('%d-%m-%Y', startdate) as 'startdate',
strftime('%d-%m-%Y', enddate) as 'enddate',
salt,
password,
this_year_opening,
this_year_remaining,
next_year_opening,
next_year_remaining
from employees order by lastname


-- name: db-get-employee-by-id
-- Get the employee with the given id
select
-- Employee info
e.id,
e.firstname,
e.lastname,
e.email,
e.departments_id,
e.managerid,
strftime('%d-%m-%Y', e.dob) as dob,
strftime('%d-%m-%Y', e.startdate) as startdate,
strftime('%d-%m-%Y', e.enddate) as enddate,
e.salt,
e.password,
e.this_year_opening,
e.this_year_remaining,
e.next_year_opening,
e.next_year_remaining,
d.department as 'department',
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from employees e
  left join employees m on e.managerid = m.id
  left join departments d on e.departments_id = d.id
where e.id = :id

-- name: db-get-employee-by-email
-- Get the employee with the given email address
select
-- Employee info
e.id,
e.firstname,
e.lastname,
e.email,
e.departments_id,
e.managerid,
e.strftime('%d-%m-%Y', dob) as e.dob,
e.strftime('%d-%m-%Y', startdate) as e.startdate,
e.strftime('%d-%m-%Y', enddate) as e.enddate,
e.salt,
e.password,
e.this_year_opening,
e.this_year_remaining,
e.next_year_opening,
e.next_year_remaining
d.department as 'department',
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from employees e
  left join employees m on e.managerid = m.id
  left join departments d on e.departments_id = d.id
where e.email = :email

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

-- ============================= Holidays ===========================
-- name: db-insert-holiday-request<!
-- Insert a new holiday request
insert into holidays (start_date,
                      start_type,
                      end_date,
                      employee_id,
                      employee_name,
                      leave_type_id,
                      leave_type,
                      duration,
                      deduction,
                      reason,
                      status,
                      unit)
                values (
                      :start_date,
                      :start_type,
                      :end_date,
                      :employee_id,
                      :employee_name,
                      :leave_type_id,
                      :leave_type,
                      :duration,
                      :deduction,
                      :reason,
                      :status,
                      :unit
                )


