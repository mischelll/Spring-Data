//package entities.hospital;
//
//import entities.BaseEntity;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "diagnoses")
//public class Diagnose extends BaseEntity {
//    private String name;
//    private String comments;
//
//    @OneToMany(mappedBy = "diagnose",targetEntity = Patient.class,cascade = CascadeType.ALL)
//    private Set<Patient> patients;
//
//    public Diagnose(String name) {
//        setName(name);
//    }
//
//    @Column(name = "name",nullable = false)
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        if (name.isEmpty()){
//            throw new NullPointerException("Name is missing!");
//        }
//        this.name = name;
//    }
//
//    @Column(name = "comments",length = 1500)
//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//}
