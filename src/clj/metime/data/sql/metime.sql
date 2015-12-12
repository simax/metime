-- name: db-get-all-departments
-- Get all departments
select
-- Department info
d.id, d.id as 'departmentid', d.department, d.manager_id,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email'
from departments d left join employees e on d.manager_id = e.id
order by d.department


-- name: db-get-all-departments-with-employees
-- Get all department with employees

select
-- Department info
d.id, d.id as 'departmentid', d.id as 'departmentid', d.department, d.manager_id,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email',
-- Employee info
emps.*
from departments d left join employees e on d.manager_id = e.id left join employees emps on emps.department_id = d.id
order by d.department, emps.lastname


-- name: db-get-department-by-name
-- Get the department with the given name
select * from departments where department = :department


-- name: db-get-department-by-id
-- Get the department with the given id
select
-- Department info
d.id, d.department, d.manager_id,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email'
from departments d left join employees e on d.manager_id = e.id
where d.id = :id
order by department

-- name: db-get-department-by-id-with-employees
-- Get the department with the given id and include the department employees

select
-- Department info
d.id, d.department, d.manager_id,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email',
-- Employee info
emps.*
from departments d left join employees e on d.manager_id = e.id left join employees emps on emps.department_id = d.id
where d.id = :id
order by d.department, emps.lastname


-- name: db-insert-department<!
-- Insert a new department
insert into departments (department, manager_id) values (:department, :manager_id)

-- name: db-update-department!
-- Update department details
update departments
set department = :department, manager_id = :managerid
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
department_id,
manager_id,
strftime('%d-%m-%Y', dob) as 'dob',
strftime('%d-%m-%Y', startdate) as 'startdate',
strftime('%d-%m-%Y', enddate) as 'enddate',
prev_year_allowance,
current_year_allowance,
next_year_allowance,
salt,
password
from employees order by lastname


-- name: db-get-employee-by-id
-- Get the employee with the given id
select
-- Employee info
e.id,
e.firstname,
e.lastname,
e.email,
e.department_id,
e.manager_id,
strftime('%d-%m-%Y', e.dob) as 'dob',
strftime('%d-%m-%Y', e.startdate) as 'startdate',
strftime('%d-%m-%Y', e.enddate) as 'enddate',
e.salt,
e.password,
e.prev_year_allowance,
e.current_year_allowance,
e.next_year_allowance,
d.department as 'department',
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from employees e
  left join employees m on e.manager_id = m.id
  left join departments d on e.department_id = d.id
where e.id = :id

-- name: db-get-employee-by-email
-- Get the employee with the given email address
select
-- Employee info
e.id,
e.firstname,
e.lastname,
e.email,
e.department_id,
e.manager_id,
strftime('%d-%m-%Y', e.dob) as 'dob',
strftime('%d-%m-%Y', e.startdate) as 'startdate',
strftime('%d-%m-%Y', e.enddate) as 'enddate',
e.salt,
e.password,
e.prev_year_allowance,
e.current_year_allowance,
e.next_year_allowance,
d.department as 'department',
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from employees e
  left join employees m on e.manager_id = m.id
  left join departments d on e.department_id = d.id
where e.email = :email

-- name: db-insert-employee<!
-- Insert a new employee
insert into employees (firstname,lastname,email,startdate,enddate,department_id,manager_id,dob,salt,password,prev_year_allowance,current_year_allowance,next_year_allowance,is_approver)
values (:firstname,:lastname,:email,:startdate,:enddate,:department_id,:managerid,:dob,:salt,:password,:prev_year_allowance,:current_year_allowance,:next_year_allowance,:is_approver)

-- name: db-update-employee!
-- Update department details
update employees
set
  firstname = :firstname,
  lastname = :lastname,
  email = :email,
  startdate = :startdate,
  enddate = :enddate,
  department_id = :department_id,
  manager_id = :manager_id,
  dob = :dob,
  prev_year_allowance = :prev_year_allowance,
  current_year_allowance = :current_year_allowance,
  next_year_allowance = :next_year_allowance

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


-- name: db-get-holidays
-- Get all holidays
select
id,
[start_date],
[start_type],
[end_date],
[employee_id],
[employee_name],
[leave_type_id],
[leave_type],
[duration],
[deduction],
[actioned_by_id],
[reason],
[declined_reason],
[status],
[unit]

from holidays
order by [start_date] desc

