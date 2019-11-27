CREATE TABLE PurchaseList2(
  ID_RECORD INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  ID_COURSE INTEGER,
  ID_STUDENT INTEGER,
  ID_TEACHER INTEGER,
  NAME_COURSE VARCHAR(200),
  FIO_STUDENT VARCHAR(200),
  SUBSCRIPTION_DATE DATE
);

/*ЗАПОЛНЕНИЕ ТАБЛИЦЫ PurchaseList2*/
INSERT INTO PurchaseList2(ID_COURSE, ID_STUDENT, ID_TEACHER, NAME_COURSE, FIO_STUDENT, SUBSCRIPTION_DATE)
            SELECT Courses.id, Students.id, Courses.teacher_id,
                   PurchaseList.course_name,PurchaseList.student_name,PurchaseList.subscription_date
            FROM PurchaseList
                     JOIN Courses ON PurchaseList.course_name = Courses.name
                     JOIN Students ON PurchaseList.student_name = Students.name;

/*ЗАПРОС ДЛЯ ЗАПОЛНЕНИЯ ТАБЛИЦЫ*/
SELECT Courses.id, Students.id, Courses.teacher_id,
       PurchaseList.course_name,PurchaseList.student_name,PurchaseList.subscription_date
FROM PurchaseList
JOIN Courses ON PurchaseList.course_name = Courses.name
JOIN Students ON PurchaseList.student_name = Students.name;

/**/
select *
from PurchaseList2
LIMIT 10; 
