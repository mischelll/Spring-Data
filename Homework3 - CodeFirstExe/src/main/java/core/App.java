package core;



import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/*
Разархивирайте класовете(Ctrl + A -> Ctrl + /) в дадената база и поставете съответния стринг(от дадените по- долу)
като параметър в .createEntityManagerFactory('тук').
Променете Persistence Unit в persistence.xml!
Само класовете на една база трябва да са разархивирани!
 */
public class App {
    private static final String GRINGOTTS_PU = "gringotts";
    private static final String SALES_PU = "sales";
    private static final String UNIVERSITY_SYSTEM_PU = "university_system";
    private static final String HOSPITAL_PU = "hospital_db";
    private static final String PAYMENT_PU = "payment_system_db";
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PAYMENT_PU);
        //EntityManager entityManager = entityManagerFactory.createEntityManager();



    }
}
