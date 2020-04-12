package tasks;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;

public class ContainsEmployee extends CommandImpl {
    public ContainsEmployee(EntityManager entityManager, BufferedReader bufferedReader) {
        super(entityManager, bufferedReader);
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Enter full name: ");
        String fullName = this.getBufferedReader().readLine();

        try {
            Employee e = this.getEntityManager().createQuery("SELECT e from Employee  as e " +
                    "where concat(e.firstName,' ',e.lastName) = :name", Employee.class)
                    .setParameter("name", fullName)
                    .getSingleResult();

            System.out.println("Yes");
        } catch (NoResultException e1) {
            System.out.println("No");
        }

    }
}
