package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PurchaseList2", schema = "skillbox_db", catalog = "")
public class PurchaseList2Entity {
    @Id
    @Column(name = "ID_RECORD", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRecord;
    @Column(name = "ID_COURSE", nullable = false)
    private Integer idCourse;
    @Column(name = "ID_STUDENT", nullable = false)
    private Integer idStudent;
    @Column(name = "ID_TEACHER")
    private Integer idTeacher;
    @Column(name = "NAME_COURSE", length = 200)
    private String nameCourse;
    @Column(name = "FIO_STUDENT", length = 200)
    private String fioStudent;
    @Column(name = "SUBSCRIPTION_DATE")
    private Date subscriptionDate;

    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(Integer idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getFioStudent() {
        return fioStudent;
    }

    public void setFioStudent(String fioStudent) {
        this.fioStudent = fioStudent;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseList2Entity that = (PurchaseList2Entity) o;

        if (idRecord != that.idRecord) return false;
        if (idCourse != null ? !idCourse.equals(that.idCourse) : that.idCourse != null) return false;
        if (idStudent != null ? !idStudent.equals(that.idStudent) : that.idStudent != null) return false;
        if (idTeacher != null ? !idTeacher.equals(that.idTeacher) : that.idTeacher != null) return false;
        if (nameCourse != null ? !nameCourse.equals(that.nameCourse) : that.nameCourse != null) return false;
        if (fioStudent != null ? !fioStudent.equals(that.fioStudent) : that.fioStudent != null) return false;
        if (subscriptionDate != null ? !subscriptionDate.equals(that.subscriptionDate) : that.subscriptionDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRecord;
        result = 31 * result + (idCourse != null ? idCourse.hashCode() : 0);
        result = 31 * result + (idStudent != null ? idStudent.hashCode() : 0);
        result = 31 * result + (idTeacher != null ? idTeacher.hashCode() : 0);
        result = 31 * result + (nameCourse != null ? nameCourse.hashCode() : 0);
        result = 31 * result + (fioStudent != null ? fioStudent.hashCode() : 0);
        result = 31 * result + (subscriptionDate != null ? subscriptionDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseList2Entity {" +
                "idRecord =" + idRecord +
                ", idCourse =" + idCourse +
                ", idStudent=" + idStudent +
                ", idTeacher =" + idTeacher +
                ", nameCourse ='" + nameCourse + '\'' +
                ", fioStudent ='" + fioStudent + '\'' +
                ", subscriptionDate =" + subscriptionDate +
                '}';
    }
}
