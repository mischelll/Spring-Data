//package entities.billspaymentsystem;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "credit_cards")
//
//public class CreditCard extends BillingDetail {
//    private String cardType;
//    private String expirationMonth;
//    private Integer expirationYear;
//
//    public CreditCard(String cardType, String expirationMonth, Integer expirationYear) {
//        setCardType(cardType);
//        setExpirationMonth(expirationMonth);
//        setExpirationYear(expirationYear);
//    }
//
//    @Column(name = "card_type",nullable = false)
//    public String getCardType() {
//        return cardType;
//    }
//
//    public void setCardType(String cardType) {
//        this.cardType = cardType;
//    }
//
//    @Column(name = "expiration_month",nullable = false)
//    public String getExpirationMonth() {
//        return expirationMonth;
//    }
//
//    public void setExpirationMonth(String expirationMonth) {
//        this.expirationMonth = expirationMonth;
//    }
//
//    @Column(name = "expiration_year",nullable = false)
//    public Integer getExpirationYear() {
//        return expirationYear;
//    }
//
//    public void setExpirationYear(Integer expirationYear) {
//        this.expirationYear = expirationYear;
//    }
//}
