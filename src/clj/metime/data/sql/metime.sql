-- Get the leave-type with the given id
-- :name get-leave-type-by-id :? :1
select id as 'leave-type-id', leave_type, reduce_leave from leave_types where id = :id

-- Get the leave-type with the given name
-- :name get-leave-type-by-name :? :1
select * from leave_types where leave_type = :leave_type


-- Get all leave types
-- :name get-all-leave-types :? :*
select id as 'leave-type-id', leave_type, reduce_leave from leave_types order by leave_type


-- Insert a new leave_type
-- :name insert-leave-type :i!
insert into leave_types (leave_type, reduce_leave)
values (:leave_type, :reduce_leave)


-- Update leave-type
-- :name update-leave-type :! :n
update leave_types
set leave_type = :leave_type,
reduce_leave = :reduce_leave
where id = :id


-- Delete the leave-type with the given id
-- :name delete-leave-type :! :n
delete from leave_types where id = :id


-- Delete all the leave_types
-- :name delete-all-leave-types :! :n
delete from leave_types


-- Get all departments
-- :name get-all-departments :? :*
select
d.id as 'department-id', d.department, d.manager_id,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email'
, (select count(*) from employees where employees.department_id = d.id) as employee_count
from departments d left join employees e on d.manager_id = e.id
order by d.department


-- Get all departments with employees
-- :name get-all-departments-with-employees :? :*
select
-- Department info
d.id as 'department-id', d.department,
-- Employee info
emps.id, emps.firstname, emps.lastname, emps.email
from departments d
    left join employees e on d.manager_id = e.id
    inner join employees emps on emps.department_id = d.id
order by d.department, emps.lastname


-- Get the department with the given name
-- :name get-department-by-name :? :1
select * from departments where department = :department


-- Get the department with the given id
-- :name get-department-by-id :? :1
select
-- Department info
d.id, d.department, d.manager_id,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email'
from departments d left join employees e on d.manager_id = e.id
where d.id = :id
order by department

-- Get the department with the given id and include the department employees
-- :name get-department-by-id-with-employees :? :*
select
-- Department info
d.id, d.department,
-- Manager info
e.firstname as 'manager-firstname', e.lastname as 'manager-lastname', e.email as 'manager-email',
-- Employee info
emps.*
from departments d left join employees e on d.manager_id = e.id
                   left join employees emps on emps.department_id = d.id
where d.id = :id
order by d.department, emps.lastname


-- Insert a new department
-- :name insert-department :i!
insert into departments (department, manager_id)
values (:department, :manager-id)

-- Update department details
-- :name update-department :! :n
update departments
set department = :department, manager_id = :manager-id
where id = :id

-- Delete the department with the given id
-- :name delete-department :! :n
delete from departments where id = :id

-- Delete all the departments
-- :name delete-all-departments :! :n
delete from departments

-- Get all employees
-- :name get-all-employees :? :*
select
id,
firstname,
lastname,
email,
department_id,
manager_id
from employees order by lastname


-- Get the employee with the given id
-- :name get-employee-by-id :? :1
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

-- Get the employee with the given email address
-- :name get-employee-by-email :? :1
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


-- :name all-departments-and-employees :? :*
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
e.password,
e.prev_year_allowance,
e.current_year_allowance,
e.next_year_allowance,
d.department as 'department',
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from departments d
  left join employees m on e.manager_id = m.id
  left join employees e on e.department_id = d.id


-- :name get-department-employees :? :*
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
e.password,
e.prev_year_allowance,
e.current_year_allowance,
e.next_year_allowance,
d.department as 'department',
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from departments d
  inner join employees m on e.manager_id = m.id
  inner join employees e on e.department_id = d.id
where d.id = :id

-- :name departments-with-employees :? :*
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
e.password,
e.prev_year_allowance,
e.current_year_allowance,
e.next_year_allowance,
d.department as 'department',
-- Manager info
m.firstname as 'manager-firstname', m.lastname as 'manager-lastname', m.email as 'manager-email'

from departments d
  inner join employees m on e.manager_id = m.id
  inner join employees e on e.department_id = d.id


-- Insert a new employee
-- :name insert-employee :i!
insert into employees (
    firstname,
    lastname,
    email,
    startdate,
    enddate,
    department_id,
    manager_id,
    dob,
    password,
    prev_year_allowance,
    current_year_allowance,
    next_year_allowance,
    is_approver)
values (
    :firstname,
    :lastname,
    :email,
    :startdate,
    :enddate,
    :department-id,
    :manager-id,
    :dob,
    :password,
    :prev-year-allowance,
    :current-year-allowance,
    :next-year-allowance,
    :is-approver)

-- Update department details
-- :name update-employee :! :n
update employees
set
  firstname = :firstname,
  lastname = :lastname,
  email = :email,
  startdate = :startdate,
  enddate = :enddate,
  department_id = :department-id,
  manager_id = :manager-id,
  dob = :dob,
  prev_year_allowance = :prev-year-allowance,
  current_year_allowance = :current-year-allowance,
  next_year_allowance = :next-year-allowance,
  password = :password

where id = :id

-- Delete the employee with the given id
-- :name delete-employee :!
delete from employees where id = :id

-- Delete all the employees
-- :name delete-all-employees :!
delete from employees

-- ============================= Holidays ===========================
-- Insert a new holiday request
-- :name insert-holiday-request :i! :n
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
                      :start-date,
                      :start-type,
                      :end-date,
                      :employee-id,
                      :employee-name,
                      :leave-type-id,
                      :leave-type,
                      :duration,
                      :deduction,
                      :reason,
                      :status,
                      :unit
                )


-- Get all holidays
-- :name get-holidays :? :*
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

