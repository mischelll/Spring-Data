//package entities.universitysystem;
//
//import entities.BaseEntity;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "students")
//public class Student extends Person {
//    private Double averageGrade;
//    private Double attendance;
//
//    private Set<Course> courses;
//
//    public Student() {
//    }
//
//    @Column(name = "average_grade")
//    public Double getAverageGrade() {
//        return averageGrade;
//    }
//
//    public void setAverageGrade(Double averageGrade) {
//        this.averageGrade = averageGrade;
//    }
//
//    @Column(name = "attendance")
//    public Double getAttendance() {
//        return attendance;
//    }
//
//    public void setAttendance(Double attendance) {
//        this.attendance = attendance;
//    }
//
//    @ManyToMany
//    @JoinTable(name = "students_courses",
//            joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id"))
//
//    public Set<Course> getCourses() {
//        return courses;
//    }
//
//    public void setCourses(Set<Course> courses) {
//        this.courses = courses;
//    }
//}
