//package entities.hospital;
//
//import entities.BaseEntity;
//
//import javax.persistence.*;
//import java.util.Date;
//
//
//@Entity
//@Table(name = "visitations")
//public class Visitation extends BaseEntity {
//    private Date date;
//    private String comments;
//
//    private Patient patient;
//
//    public Visitation(Date date) {
//        setDate(date);
//    }
//
//    @Column(name = "comments", length = 1500)
//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//
//    @Column(name = "date", nullable = false)
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        if (date == null){
//            throw new NullPointerException("Date required!");
//        }
//        this.date = date;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "patient_id",referencedColumnName = "id")
//    public Patient getPatient() {
//        return patient;
//    }
//
//    public void setPatient(Patient patient) {
//        this.patient = patient;
//    }
//}
