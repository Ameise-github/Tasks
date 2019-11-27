package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Courses", schema = "skillbox_db")
public class CoursesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private CourseType type;
    private String description;

    @Column(name = "students_count")
    private Integer studentsCount;
    private Integer price;

    @Column(name = "price_per_hour")
    private Float pricePerHour;

    @ManyToOne(cascade = CascadeType.ALL)
    private TeachersEntity teacher;

    @ManyToMany
    @JoinTable(name = "Subscriptions",
                joinColumns = {@JoinColumn (name = "course_id")},
                inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private List<StudentsEntity> students;

    @OneToMany(mappedBy = "course")
    private List<SubscriptionsEntity> subscriptionsEntities;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Object getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Float getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public TeachersEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeachersEntity teacher) {
        this.teacher = teacher;
    }

    public List<StudentsEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsEntity> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoursesEntity that = (CoursesEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (studentsCount != null ? !studentsCount.equals(that.studentsCount) : that.studentsCount != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (pricePerHour != null ? !pricePerHour.equals(that.pricePerHour) : that.pricePerHour != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (studentsCount != null ? studentsCount.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (pricePerHour != null ? pricePerHour.hashCode() : 0);
        return result;
    }
}
