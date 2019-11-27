package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Subscriptions", schema = "skillbox_db")
@IdClass(SubscriptionsKey.class)
public class SubscriptionsEntity {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id")
    private StudentsEntity students;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="course_id")
    private CoursesEntity course;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public SubscriptionsEntity() {
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public StudentsEntity getStudents() {
        return students;
    }

    public void setStudents(StudentsEntity students) {
        this.students = students;
    }

    public CoursesEntity getCourse() {
        return course;
    }

    public void setCourse(CoursesEntity course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionsEntity that = (SubscriptionsEntity) o;

        if (subscriptionDate != null ? !subscriptionDate.equals(that.subscriptionDate) : that.subscriptionDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return subscriptionDate != null ? subscriptionDate.hashCode() : 0;
    }
}
