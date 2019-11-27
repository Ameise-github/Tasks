package model;

import java.io.Serializable;
import java.util.Objects;

public class SubscriptionsKey implements Serializable {
    static final long serialVersionUID = 1L;
    private StudentsEntity students;
    private CoursesEntity course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionsKey that = (SubscriptionsKey) o;
        return Objects.equals(students, that.students) &&
                Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students, course);
    }
}
