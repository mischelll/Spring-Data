//package entities.hospital;
//
//import entities.BaseEntity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//import java.util.Set;
//
//@Entity
//@Table(name = "medicaments")
//public class Medicament extends BaseEntity {
//    private String name;
//
//    private Set<Patient> patients;
//    public Medicament(String name) {
//        setName(name);
//    }
//
//    @Column(name = "name")
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
//    @ManyToMany(mappedBy = "medicaments",targetEntity = Patient.class)
//    public Set<Patient> getPatients() {
//        return patients;
//    }
//
//    public void setPatients(Set<Patient> patients) {
//        this.patients = patients;
//    }
//}
