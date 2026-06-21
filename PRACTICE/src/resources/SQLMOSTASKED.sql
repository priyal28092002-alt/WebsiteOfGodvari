 -- 1)Write a query to find the second highest salary in a table. 
select Max(financedAmount) from d320036 where financedAmount <( select Max(financedAmount) from d320036)
select financedAmount from d320036 order BY financedAmount DESC	
OFFSET 1 ROWS fetch NEXT 1 ROWS ONLY

--2) Write a query to find the nth highest salary from the employee table.
DECLARE @n INT= 3
select financedAmount from d320036 ORDER BY financedAmount DESC OFFSET (@n-1) ROWS FETCH Next 1 ROWS ONLY;

--3) Write a query to find duplicate records in a table.
select email, * from d320121 where email in (select email from d320121 GROUP by email having COUNT(*)>1);

--4) Write a query to delete duplicate records from a table.
DELETE FROM D320121 WHERE preleadId IN ( SELECT preleadId FROM (select preleadId, ROW_NUMBER OVER (PARTITION BY email ORDER BY preleadId)as rowNum)t where rowNum>1);

--5)Write a query to find employees who joined in the last 30 days.
SELECT dateOfBirth, * FROM D320025 WHERE dateOfBirth >= curDate -30 INTERVAL day -MYSQL
SELECT lastModifiedDate,* FROM D320025 WHERE lastModifiedDate >= DATEADD(day,-30,getDATE()) -mssql

--6)Write a query to count the number of employees in each department.
select d.departmentname,count(*) as empcount from employement e inner join department d on e.departmentId=d.id group by d.departmentname

--7) Write a query to find employees who do not have a department assigned.
SELECT e.* from employe e left join department d on e.depid=d.id where d.id is null

--8)Write a query to find the highest salary in each department
select d.departmentname, Max(e.salary) as higestSal, from employee left join  department d on e.depid=d.id group by departmentname

--9)Write a query to find the second highest salary in each department.
select s.departmentname ,t.salary from (select e.depid,e.salary , DENSE_RANK() over (partition by e.depid order by  e.salary)as rownum from employee e)t left join department d on t.depid=d.id where rownum =2

--10)Write a query to display employees who earn more than their manager.
select e.* from employee e join employee m on e.empid=m.mangeid where e.sal>m.sal;

--11)Write a query to find employees whose salary is above the average salary.
SELECT(SELECT avg(financedAmount) FROM D320036) AS avergae,*  FROM D320036 WHERE financedAmount >(SELECT avg(financedAmount) FROM D320036) 

--12)Write a query to find departments with more than 5 employees.
select d.department,count(*) as employeecount from employee e join department d on e.depid=d.id  group by d.depid having count(*) > 5

--13)Write a query to get the total salary paid in each department.
select sum(e.salary) as summ from employee e join department d on e.depid=d.id group by d.department

--14)Write a query to find employees with the same salary.	
select e.*, d.department from employee e join department d on e.depid=d.id where e.salary in (select e.salary from employee group by e.salary having count(*)>1);

--15)Write a query to retrieve the first record from a table.
SELECT TOP 1 * FROM D320036 ORDER BY appId ASC;

--16)Write a query to retrieve the last record from a table.
SELECT TOP 1 * from d320036 order by appId DESC

--17)Write a query to find employees hired in a specific year.
SELECT * FROM d320036 WHERE YEAR(lastmodifiedDate) = 2026;

--18)Write a query to get the top 3 highest salaries from the employee table.
select top 3 * from d320036 order by financedAmount desc 

--19)Write a query to find employees whose name starts with 'A'.
select * from employes where empname  like 'A%'

--20)Write a query to find employees whose name contains 'son'.
select * from employee where empname like '%son%'

--21)Write a query to get the total number of employees in the company.
select count(*) as totalemp from employee

--22)Write a query to find the average salary of each department.
select avg(e.salary),d.departmentname from employee e join department d on e.depid=d.id group by d.departmentname

--23)Write a query to find employees who joined between two dates.
select * from employee where hireddate>='2023-01-08' and hidedate<'2023-03-07';

--24)Write a query to find employees who share the same manager.
select * from employee e join employee m on e.miid=m.empid

--25)Write a query to find employees who share the same manager.
select * from employee e join employee m on e.miid=m.empid

--26)Write a query to list departments that have no employees.
select d.departmentname from department d left join employee e on e.depid=d.id group by d.departmentname having count(e.empid)=0;
select d.departmentname from department d left join employee e on e.depid=d.id where e.depid is null

--27)Write a query to rank employees based on salary using RANK(). 
select empid , rank() over (order by salary desc)as salrank from employee

--28)Write a query to find the top 3 highest salaries in each department using DENSE_RANK().
select d.departmentname,t.salary, t.salrank from (select salary,DENSE_RANK() over (partition by depid order by salary desc)as salrank form employee )t join department d on d.depid=t.id where t.salrank<=3

--29)Write a query to find employees whose salary is greater than the average salary of their department.
select e.* from employee join (select depid ,avg(sal)as salryavg from employee group by depid)t on e.depid=t.depid where e.salary >t.salary
select e.* from employee where e.salary>(select avg(salary) from employee where depid= e.depid);

--30)Write a query to find duplicate records using ROW_NUMBER().
select e.* from (select email, ROW_NUMBER() over(PARTITION BY email ORDER BY empid)as eamilrank from employee)t where count(eamilrank)>1

--31)Write a query to delete duplicate rows using a CTE and ROW_NUMBER().
with CTE as(select email, empid , ROW_NUMBER() over (PARtition by email order by empid)as emailrank from employee)
delete from cte where emailrank>1;

--32)Write a query to display department-wise employee count using JOIN.
select d.departmentname, count(*) from employee e join department d on e.depid = d.id group by d.departmentname

--33)Write a query to find departments with no employees using LEFT JOIN.
select d.department, count (e.empid) as countofemp from department d left join employee e on d.id=e.depid where e.depid is null

--34)Write a query to find employees who have the same salary in the same department.
select * from (select *,count(*) over (partition by depid, salary)as countrank from employee)t where countrank>1;
select * from employee where (depid,salary)in (select depid,salary from employee group by depid,salary having count(*)>1);

--35)Write a query to find the running total of salary ordered by employee id.
select empid,salary, sum(salary) over (order by empid desc)as runningtotal from employee;

--36)Write a query to calculate cumulative salary using window functions.
select empid, salary,sum(salary) over (order by empid)as cumulativesal from employee;

--37)Write a query to find employees whose salary is the highest in their department using window functions.
select t.empid,d.department from (select empid, DENSE_RANK() over (partition by depid order by salary desc)as deprank from employee)t join department d on d.id=t.deptid where deprank=1;

--38)Write a query to find employees who joined first in each department using ROW_NUMBER().
select d.departmentname, t.* from (select empid , ROW_NUMBER() over (partition by depid order by joiningdate asc)as joinasrank from employee)t join department d on t.depid=d.id where t.joinasrank=1; 

--39)Write a query to get the latest record for each employee using ROW_NUMBER().
SELECT * FROM ( SELECT *,ROW_NUMBER() OVER (PARTITION BY empid ORDER BY joiningdate DESC) AS rn FROM employee) t WHERE rn = 1;

--40)Write a query to find the median salary using window functions.
with cte as(select salary,ROW_NUMBER() over (order by salary desc) as rn, count(*) over () as total_count from employee) select AVG(salary) AS median_salary from cte where rn in ((total_count+1/2),(total_count+2/2));

--41)Write a query to find the next employee salary using LEAD().
select empid,salary as currentSal,LEAD(salary) over (order by empid)as next_salary from employee;

--42)Write a query to split a comma-separated string into rows using SQL.
select value from STRING_SPLIT('apple','orange','grapes',,);

--43)Write a query to find employees who worked continuously for more than 3 years using date functions.
select empid, empname from employee where DATEDIFF('YEAR',joiningDate,ISNULL(endDate,GETDATE())>=3;

--44)Write a query to identify gaps in sequential employee IDs.
select empid,LEAD(empid) over (order by empid)as nextid, from employee where LEAD(empid) over (order by empid) as currentplusno- empid>1;

--45)Write a query to find the percentage contribution of each employee’s salary to the total department salary.
select empid,salary, sum(salary) as totalsum,(salary *100/SUM(salary)) over (PARTITION by depid) as salary_perecntage from employee