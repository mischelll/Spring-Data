//package entities;
//
//import entities.sales.Customer;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "sales")
//public class Sale extends BaseEntity {
//    private entities.Product product;
//    private Customer customer;
//    private entities.StoreLocation storeLocation;
//
//    @Column(name = "date")
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    private Date date;
//
//    public Sale() {
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    public entities.Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(entities.Product product) {
//        this.product = product;
//    }
//    @ManyToOne
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//    @ManyToOne
//    @JoinColumn(name = "store_location_id", referencedColumnName = "id")
//    public entities.StoreLocation getStoreLocation() {
//        return storeLocation;
//    }
//
//    public void setStoreLocation(entities.StoreLocation storeLocation) {
//        this.storeLocation = storeLocation;
//    }
//}
//
