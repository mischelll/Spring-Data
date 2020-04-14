//package entities.hospital;
//
//import com.mysql.cj.jdbc.Blob;
//import entities.BaseEntity;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//@Table(name = "patients")
//public class Patient extends BaseEntity {
//    private String firstName;
//    private String lastName;
//    private String address;
//    private String email;
//    private Date dateOfBirth;
//    private byte[] picture;
//    private Boolean medicalInsurance;
//
//    private Diagnose diagnose;
//    private Set<Visitation> visitations;
//    private Set<Medicament> medicaments;
//
//    public Patient(String firstName, String lastName, Date dateOfBirth) {
//        setFirstName(firstName);
//        setLastName(lastName);
//        setDateOfBirth(dateOfBirth);
//    }
//
//
//    @ManyToOne
//    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
//    public Diagnose getDiagnose() {
//        return diagnose;
//    }
//
//    public void setDiagnose(Diagnose diagnose) {
//        this.diagnose = diagnose;
//    }
//
//    @OneToMany(mappedBy = "patient", targetEntity = Visitation.class, cascade = CascadeType.ALL)
//    public Set<Visitation> getVisitations() {
//        return visitations;
//    }
//
//    public void setVisitations(Set<Visitation> visitations) {
//        this.visitations = visitations;
//    }
//
//    @ManyToMany
//    @JoinTable(name = "patients_medicaments",
//    joinColumns = @JoinColumn(name = "patient_id",referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "medicament_id",referencedColumnName = "id"))
//    public Set<Medicament> getMedicaments() {
//        return medicaments;
//    }
//
//    public void setMedicaments(Set<Medicament> medicaments) {
//        this.medicaments = medicaments;
//    }
//
//    @Column(name = "first_name", nullable = false)
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        if (firstName.isEmpty()) {
//            throw new NullPointerException("First name of patient required!");
//        }
//        this.firstName = firstName;
//    }
//
//    @Column(name = "last_name", nullable = false)
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        if (lastName.isEmpty()) {
//            throw new NullPointerException("Last name of patient required!");
//        }
//        this.lastName = lastName;
//    }
//
//    @Column(name = "address")
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    @Column(name = "email")
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    @Column(name = "date_of_birth")
//    public Date getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(Date dateOfBirth) {
//        if (dateOfBirth == null) {
//            throw new NullPointerException("Date of birth required!");
//        }
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    @Column(name = "picture")
//    public byte[] getPicture() {
//        return picture;
//    }
//
//    public void setPicture(byte[] picture) {
//        this.picture = picture;
//    }
//
//    @Column(name = "medical_insurance")
//    public Boolean getMedicalInsurance() {
//        return medicalInsurance;
//    }
//
//    public void setMedicalInsurance(Boolean medicalInsurance) {
//        this.medicalInsurance = medicalInsurance;
//    }
//}
