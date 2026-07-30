--ALL table creation 
CREATE TABLE Organization (
    Org_id INT PRIMARY KEY,
    Org_name VARCHAR2(100) NOT NULL,
    Org_type VARCHAR2(50) CHECK (Org_type IN ('NGO', 'Government', 'Private', 'Other')),
    address VARCHAR2(500),
    phone VARCHAR2(20),
    email VARCHAR2(100) UNIQUE,
    website VARCHAR2(100)
); 

CREATE TABLE Users (
    User_id INT PRIMARY KEY,
    User_name VARCHAR2(100) NOT NULL,
    phone VARCHAR2(20),
    email VARCHAR2(100) UNIQUE,
    gender CHAR(1) CHECK (gender IN ('M', 'F')),
    Date_of_birth DATE, -- تم حذف القيد من هنا لحل المشكلة
    address VARCHAR2(500),
    user_status VARCHAR2(20) DEFAULT 'active' 
        CHECK (user_status IN ('active', 'inactive', 'suspended'))
);
CREATE TABLE Employee ( 
    User_id INT PRIMARY KEY,
    Hire_date DATE NOT NULL,
    Job_title VARCHAR2(50) NOT NULL,
    Super_User_id INT, 
    Emp_Role VARCHAR2(50) CHECK (Emp_Role IN ('Manager', 'Staff', 'Supervisor', 'Intern')),
    CONSTRAINT fk_emp_users FOREIGN KEY (User_id) REFERENCES Users(User_id) ON DELETE CASCADE,
    CONSTRAINT fk_emp_super FOREIGN KEY (Super_User_id) REFERENCES Employee(User_id) ON DELETE SET NULL
);  




CREATE TABLE Volunteer (
    User_id INT PRIMARY KEY,
    Supervised_Emp_Id INT,
    Proj_id INT,
    Volunteer_Status VARCHAR2(20) DEFAULT 'pending'
        CHECK (Volunteer_Status IN ('pending', 'active', 'inactive', 'completed')),
    CONSTRAINT fk_vol_user FOREIGN KEY (User_id) REFERENCES Users(User_id) ON DELETE CASCADE,
    CONSTRAINT fk_vol_emp FOREIGN KEY (Supervised_Emp_Id) REFERENCES Employee(User_id) ON DELETE SET NULL
);


CREATE TABLE Volunteer_Skills (
    User_id INT,
    Skill VARCHAR2(50),
    PRIMARY KEY (User_id, Skill),
    CONSTRAINT fk_skills_vol FOREIGN KEY (User_id) REFERENCES Volunteer(User_id) ON DELETE CASCADE
);


CREATE TABLE Sponsor (
    Sponsor_id INT PRIMARY KEY,
    Sponsor_name VARCHAR2(100) NOT NULL,
    Sponsor_type VARCHAR2(50),
    website VARCHAR2(100),
    address VARCHAR2(200),
    Org_id INT,
    email VARCHAR2(100),
    phone VARCHAR2(20),
    Contact_person VARCHAR2(100),
    CONSTRAINT fk_spon_org FOREIGN KEY (Org_id) REFERENCES Organization(Org_id)
);


CREATE TABLE Funding (
    Sponsor_id INT,
    Proj_id INT,
    Agreement_date DATE DEFAULT SYSDATE NOT NULL, 
    Amount NUMBER(15, 2) NOT NULL CHECK (Amount > 0),
    Currency VARCHAR2(10) DEFAULT 'USD' NOT NULL,
    Notes VARCHAR2(1000),
    PRIMARY KEY (Sponsor_id, Proj_id),
    CONSTRAINT fk_fund_spon FOREIGN KEY (Sponsor_id) REFERENCES Sponsor(Sponsor_id),
    CONSTRAINT fk_fund_proj FOREIGN KEY (Proj_id) REFERENCES Project(Proj_id)
);


CREATE TABLE Works_on (
    User_id INT, 
    Proj_id INT,
    Assigned_date DATE DEFAULT SYSDATE NOT NULL,
    Work_Role VARCHAR2(50),
    PRIMARY KEY (User_id, Proj_id),
    CONSTRAINT fk_work_user FOREIGN KEY (User_id) REFERENCES Users(User_id) ON DELETE CASCADE,
    CONSTRAINT fk_work_proj FOREIGN KEY (Proj_id) REFERENCES Project(Proj_id) ON DELETE CASCADE
); 

CREATE TABLE Department (
    Dep_id INT PRIMARY KEY,
    Org_id INT NOT NULL,
    Dep_name VARCHAR2(100) NOT NULL,
    Mgr_User_id INT, 
    Dep_description VARCHAR2(1000),
    CONSTRAINT fk_dep_org FOREIGN KEY (Org_id) REFERENCES Organization(Org_id),
    CONSTRAINT fk_dep_mgr FOREIGN KEY (Mgr_User_id) REFERENCES Employee(User_id)
);


CREATE TABLE Salary_Employee (
    User_id INT PRIMARY KEY,
    salary NUMBER(10, 2),
    Dep_id INT,
    CONSTRAINT fk_sal_emp FOREIGN KEY (User_id) REFERENCES Employee(User_id),
    CONSTRAINT fk_sal_dept FOREIGN KEY (Dep_id) REFERENCES Department(Dep_id)
);

CREATE TABLE Hour_Employee (
    User_id INT PRIMARY KEY,
    working_hours NUMBER,
    hourly_rate NUMBER(10, 2),
    CONSTRAINT fk_hour_emp FOREIGN KEY (User_id) REFERENCES Employee(User_id)
);


CREATE TABLE Project (
    Proj_id INT PRIMARY KEY,
    Proj_name VARCHAR2(100) NOT NULL,
    Proj_description VARCHAR2(1000),
    Start_date DATE NOT NULL,
    End_date DATE,
    status VARCHAR2(20) DEFAULT 'planned' CHECK (status IN ('planned', 'active', 'completed', 'cancelled')), 
    Supervised_by_emp INT NOT NULL,
    CONSTRAINT chk_proj_dates CHECK (End_date IS NULL OR End_date >= Start_date), 
    CONSTRAINT fk_proj_emp FOREIGN KEY (Supervised_by_emp) REFERENCES Employee(User_id)
);
CREATE TABLE Task (
    Task_id INT PRIMARY KEY,
    Project_id INT NOT NULL,
    Task_title VARCHAR2(100) NOT NULL,
    Task_description VARCHAR2(1000),
    status VARCHAR2(20) DEFAULT 'pending' CHECK (status IN ('pending', 'in_progress', 'completed', 'cancelled')), 
    End_date DATE,
    Completed_at DATE,
    Start_date DATE NOT NULL,
    Assigned_User_Id INT,
    CONSTRAINT chk_task_dates CHECK (End_date IS NULL OR End_date >= Start_date),  
    CONSTRAINT chk_task_completed CHECK (Completed_at IS NULL OR Completed_at >= Start_date),
    CONSTRAINT fk_task_proj FOREIGN KEY (Project_id) REFERENCES Project(Proj_id) ON DELETE CASCADE,
    CONSTRAINT fk_task_user FOREIGN KEY (Assigned_User_Id) REFERENCES Users(User_id) ON DELETE SET NULL
); 

CREATE OR REPLACE VIEW Project_Detailed_Report AS
SELECT 
p.Proj_id AS "Project ID",
p.Proj_name AS "Project Name",
p.status AS "Project Status",
p.Start_date AS "Project Start",
p.End_date AS "Project Deadline",
t.Task_id AS "Task ID",
t.Task_title AS "Task Name",
t.status AS "Task Status",
t.Start_date AS "Task Start",
t.End_date AS "Task Deadline",
t.Completed_at AS "Task Completion Date",
CASE 
WHEN t.status = 'completed' THEN '100%'
WHEN t.status = 'in_progress' THEN 'In Progress'
ELSE 'Not Started'
END AS "Progress Status"
FROM Project p
LEFT JOIN Task t ON p.Proj_id = t.Project_id; 


------------------------------------------------------------------------------------------------------------------------------------------------------------------
--For role creation and granting acess 
CREATE ROLE admin;
CREATE ROLE manager;
CREATE ROLE employee;
CREATE ROLE volunteer_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON User TO admin;
GRANT SELECT, UPDATE ON User TO manager;
GRANT SELECT ON User TO employee;
GRANT SELECT ON User TO volunteer_role;  -- يرى بياناته الأساسية فقط

GRANT SELECT, INSERT, UPDATE, DELETE ON Organization TO admin;
GRANT SELECT ON Organization TO manager;
GRANT SELECT ON Organization TO employee;

GRANT SELECT, INSERT, UPDATE, DELETE ON Department TO admin;
GRANT SELECT, UPDATE ON Department TO manager;
GRANT SELECT ON Department TO employee;

GRANT SELECT, INSERT, UPDATE, DELETE ON Employee TO admin;
GRANT SELECT, UPDATE ON Employee TO manager;
GRANT SELECT ON Employee TO employee;  -- يرى بياناته هو

GRANT SELECT, INSERT, UPDATE, DELETE ON Project TO admin;
GRANT SELECT, INSERT, UPDATE ON Project TO manager;
GRANT SELECT ON Project TO employee;
GRANT SELECT ON Project TO volunteer_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON Volunteer TO admin;
GRANT SELECT, INSERT, UPDATE ON Volunteer TO manager;
GRANT SELECT ON Volunteer TO employee;
GRANT SELECT ON Volunteer TO volunteer_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON Salary_employee TO admin;
GRANT SELECT ON Salary_employee TO manager;

GRANT SELECT, INSERT, UPDATE, DELETE ON Hour_employee TO admin;
GRANT SELECT, UPDATE ON Hour_employee TO manager;
GRANT SELECT ON Hour_employee TO employee;  -- يرى ساعاته هو فقط


GRANT SELECT, INSERT, UPDATE, DELETE ON Task TO admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON Task TO manager;
GRANT SELECT, UPDATE ON Task TO employee;   -- يعدّل status مهامه
GRANT SELECT ON Task TO volunteer_role;


GRANT SELECT, INSERT, UPDATE, DELETE ON Sponsor TO admin;
GRANT SELECT ON Sponsor TO manager;
GRANT SELECT ON Sponsor TO employee;


GRANT SELECT, INSERT, UPDATE, DELETE ON Funding TO admin;
GRANT SELECT ON Funding TO manager;

GRANT SELECT, INSERT, UPDATE, DELETE ON Volunteer_Skills TO admin;
GRANT SELECT, INSERT, UPDATE ON Volunteer_Skills TO manager;
GRANT SELECT ON Volunteer_Skills TO employee;
GRANT SELECT, INSERT, UPDATE ON Volunteer_Skills TO volunteer_role;  -- يدير مهاراته

GRANT SELECT, INSERT, UPDATE, DELETE ON Works_on TO admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON Works_on TO manager;
GRANT SELECT ON Works_on TO employee;
GRANT SELECT ON Works_on TO volunteer_role;
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
--For inserting 
-- 1. المنظمة
INSERT INTO Organization (Org_id, Org_name, Org_type, address, phone, email, website)
VALUES (1, 'Yemen Shabab Organization', 'NGO', 'Sanaa, Yemen', '9671234567', 'info@yemenshabab.org', 'www.yemenshabab.org');
-- 2. المستخدمين
INSERT INTO Users (User_id, User_name, email, phone, gender, user_status) VALUES (1, 'Samaa Galal', 'alshargabisama@gmail.com', '739307165', 'F', 'active');

INSERT INTO Users (User_id, User_name, email, phone, gender, user_status) VALUES (3, 'Rafa Sadeq', 'rafa@example.com', '732222222', 'F', 'active');
INSERT INTO Users (User_id, User_name, email, phone, gender, user_status) VALUES (2, 'Sarah ', 'sarah@example.com', '733333333', 'F', 'active');
INSERT INTO Users (User_id, User_name, email, phone, gender, user_status) VALUES (4, 'Aisha Abdulaziz', 'aisha@example.com', '733333333', 'F', 'active');
INSERT INTO Users (User_id, User_name, email, phone, gender, user_status) VALUES (5, 'Aya Ghanem', 'aya@example.com', '734444444', 'F', 'active');
INSERT INTO Users (User_id, User_name, email, phone, gender, user_status) VALUES (6, 'Rahmat', 'rahmat@example.com', '735555555', 'F', 'active');
-- 3. الموظفين
INSERT INTO Employee (User_id, Hire_date, Job_title, Emp_Role) VALUES (1, SYSDATE, 'General Manager', 'Manager');
INSERT INTO Employee (User_id, Hire_date, Job_title, Emp_Role) VALUES (2, SYSDATE, 'Project Coordinator', 'Employee');
INSERT INTO Employee (User_id, Hire_date, Job_title, Emp_Role) VALUES (3, SYSDATE, 'Finance Officer', 'Employee');
INSERT INTO Employee (User_id, Hire_date, Job_title, Emp_Role) VALUES (4, SYSDATE, 'Field Researcher', 'Employee');
INSERT INTO Employee (User_id, Hire_date, Job_title, Emp_Role) VALUES (5, SYSDATE, 'HR Specialist', 'Employee');
-- 4. الأقسام
INSERT INTO Department (Dep_id, Org_id, Dep_name, Mgr_User_id, Dep_description)
VALUES (10, 1, 'Management & HR', 1, 'Oversees all organization operations');
INSERT INTO Department (Dep_id, Org_id, Dep_name, Mgr_User_id, Dep_description)
VALUES (20, 1, 'Finance & Projects', 1, 'Handles funding and project execution');
-- 5. رواتب الموظفين
INSERT INTO Salary_Employee (User_id, salary, Dep_id) VALUES (2, 1500.00, 20);
INSERT INTO Salary_Employee (User_id, salary, Dep_id) VALUES (3, 1400.00, 20);
INSERT INTO Salary_Employee (User_id, salary, Dep_id) VALUES (5, 1200.00, 10);
-- 6. موظفي الساعات
INSERT INTO Hour_Employee (User_id, working_hours, hourly_rate) VALUES (4, 40, 15.00);
-- 7. المتطوعين
INSERT INTO Volunteer (User_id, Supervised_Emp_Id, Volunteer_Status) VALUES (6, 1, 'active');
-- 8. مهارات المتطوعين
INSERT INTO Volunteer_Skills (User_id, Skill) VALUES (6, 'Graphic Design');
INSERT INTO Volunteer_Skills (User_id, Skill) VALUES (6, 'Public Speaking');
-- 1. إضافة المشاريع (Projects)
-- المشروع الأول: تطوير تطبيق المنظمة
INSERT INTO Project (Proj_id, Proj_name, Proj_description, Start_date, End_date, status, Supervised_by_emp)
VALUES (100, 'Mobile App Development', 'Creating a mobile platform for youth engagement', TO_DATE('2026-01-01', 'YYYY-MM-DD'), TO_DATE('2026-12-31', 'YYYY-MM-DD'), 'active', 1);
-- المشروع الثاني: حملة التعليم للجميع
INSERT INTO Project (Proj_id, Proj_name, Proj_description, Start_date, End_date, status, Supervised_by_emp)
VALUES (200, 'Education for All', 'Distributing school kits to rural areas', TO_DATE('2026-05-01', 'YYYY-MM-DD'), TO_DATE('2026-09-01', 'YYYY-MM-DD'), 'active', 2);
-- 2. ربط الموظفين بالمشاريع (Works_on)
-- توزيع الصديقات على المشاريع
INSERT INTO Works_on (User_id, Proj_id, Assigned_date, Work_Role) VALUES (1, 100, SYSDATE, 'Project Lead');
INSERT INTO Works_on (User_id, Proj_id, Assigned_date, Work_Role) VALUES (4, 100, SYSDATE, 'Developer');
INSERT INTO Works_on (User_id, Proj_id, Assigned_date, Work_Role) VALUES (2, 200, SYSDATE, 'Field Coordinator');
INSERT INTO Works_on (User_id, Proj_id, Assigned_date, Work_Role) VALUES (3, 200, SYSDATE, 'Accountant');
INSERT INTO Works_on (User_id, Proj_id, Assigned_date, Work_Role) VALUES (6, 200, SYSDATE, 'Volunteer');
-- 3. إضافة الرعاة (Sponsors)
INSERT INTO Sponsor (Sponsor_id, Sponsor_name, Sponsor_type, website, Org_id, email, phone)
VALUES (50, 'Global Tech Fund', 'Corporate', 'www.globaltech.com', 1, 'grants@globaltech.com', '001222333');
INSERT INTO Sponsor (Sponsor_id, Sponsor_name, Sponsor_type, website, Org_id, email, phone)
VALUES (60, 'Yemen Aid Foundation', 'Non-Profit', 'www.yemenaid.org', 1, 'support@yemenaid.org', '967777777');
-- 4. بيانات التمويل (Funding)
INSERT INTO Funding (Sponsor_id, Proj_id, Amount, Currency, Notes)
VALUES (50, 100, 50000.00, 'USD', 'For software licenses and servers');
INSERT INTO Funding (Sponsor_id, Proj_id, Amount, Currency, Notes)
VALUES (60, 200, 20000.00, 'USD', 'For purchasing 1000 school kits');
-- مهام المشروع الأول (التطبيق)
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (1, 100, 'UI Design', 'Designing the main interface', 'completed', SYSDATE-10, 1);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (2, 100, 'Database Schema', 'Creating tables in Oracle', 'in_progress', SYSDATE-5, 4);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (3, 100, 'API Integration', 'Connecting backend with frontend', 'pending', SYSDATE, 4);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (4, 100, 'Security Audit', 'Testing for vulnerabilities', 'pending', SYSDATE, 1);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (5, 100, 'Beta Testing', 'Testing with sample users', 'pending', SYSDATE, 4);
-- مهام المشروع الثاني (التعليم)
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (6, 200, 'Supplier Contact', 'Negotiating with kit suppliers', 'completed', SYSDATE-20, 3);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (7, 200, 'Logistics Planning', 'Arranging transportation', 'in_progress', SYSDATE-10, 2);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (8, 200, 'Volunteer Training', 'Training for distribution', 'pending', SYSDATE, 6);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (9, 200, 'Site Survey', 'Identifying rural schools', 'in_progress', SYSDATE-2, 2);
INSERT INTO Task (Task_id, Project_id, Task_title, Task_description, status, Start_date, Assigned_User_Id)
VALUES (10, 200, 'Reporting', 'Financial report for donors', 'pending', SYSDATE, 3);

UPDATE Users SET password = 'password1' WHERE User_id = 1;
UPDATE Users SET password = 'password2' WHERE User_id = 2;
UPDATE Users SET password = 'password3' WHERE User_id = 3;
UPDATE Users SET password = 'password4' WHERE User_id = 4;
UPDATE Users SET password = 'password5' WHERE User_id = 5;
UPDATE Users SET password = 'password6' WHERE User_id = 6;


COMMIT;
COMMIT;