import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.List;

public class Main {
    private static StandardServiceRegistry registry;
    private static Metadata metadata;
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        String hql = "SELECT Courses.id, Students.id as studId, Courses.teacher_id, PurchaseList.course_name,PurchaseList.student_name, PurchaseList.subscription_date " +
                "FROM PurchaseList" +
                " JOIN Courses ON PurchaseList.course_name = Courses.name  " +
                "JOIN Students ON PurchaseList.student_name = Students.name";

        List<Object[]> rows = session.createSQLQuery(hql).list();
        for (Object[] row : rows) {
            PurchaseList2Entity ple = new PurchaseList2Entity();
            try {
                ple.setIdCourse(Integer.parseInt(row[0].toString()));
                ple.setIdStudent(Integer.parseInt(row[1].toString()));
                ple.setIdTeacher(Integer.parseInt(row[2].toString()));
                ple.setNameCourse(row[3].toString());
                ple.setFioStudent(row[4].toString());
                ple.setSubscriptionDate(new SimpleDateFormat("yyyy-MM-dd").parse(row[5].toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            session.save(ple);
//            session.flush();
//            session.clear();
        }

        String hql1 = "FROM " + PurchaseList2Entity.class.getSimpleName();
        List<PurchaseList2Entity> pl2e = session.createQuery(hql1).setMaxResults(10).getResultList();
//        System.out.println("\nNumber of items inPurchaseList2: " + pl2e.size());

        for(PurchaseList2Entity p : pl2e){
            System.out.println(p);
        }
        tx.commit();
        sessionFactory.close();
        session.close();

    }

    private static TeachersEntity getTeacher(int idTeacher) {
        TeachersEntity teach = null;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        String st = "FROM " + CoursesEntity.class.getSimpleName();
        List<CoursesEntity> coursList = session.createQuery(st).getResultList();
        for (CoursesEntity crs : coursList) {
            if (crs.getTeacher().getId() == idTeacher) {
                teach = crs.getTeacher();
            }
        }
        return teach;
    }
}

