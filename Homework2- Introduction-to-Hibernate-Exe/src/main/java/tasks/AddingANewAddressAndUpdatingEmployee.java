package tasks;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;

public class AddingANewAddressAndUpdatingEmployee extends CommandImpl {
    private static final String ADDRESS_NAME = "Vitoshka 15";

    public AddingANewAddressAndUpdatingEmployee(EntityManager entityManager, BufferedReader bufferedReader) {
        super(entityManager, bufferedReader);
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Input employee last name: ");
        String employeeName = this.getBufferedReader().readLine();

        Employee employee = searchEmployeeByLastName(employeeName);
        if (employee == null) {
            System.out.println("No such employee with given last name exists!");
        }
        Address address = createAddress(ADDRESS_NAME);

        updateEmployeeAddress(employee,address);

    }

    private void updateEmployeeAddress(Employee employee, Address addressName) {
        this.getEntityManager().getTransaction().begin();

        this.getEntityManager().detach(employee);
        employee.setAddress(addressName);
        this.getEntityManager().merge(employee);
        this.getEntityManager().flush();

        this.getEntityManager().getTransaction().commit();
    }

    private Address createAddress(String addressName) {
        Address address = new Address();
        address.setText(addressName);

        this.getEntityManager().getTransaction().begin();

        this.getEntityManager().persist(address);

        this.getEntityManager().getTransaction().commit();

        return address;
    }

    private Employee searchEmployeeByLastName(String employeeName) {
        Employee employee = null;
        try {
            employee = this.getEntityManager()
                    .createQuery("SELECT  e FROM Employee as e " +
                            "where e.lastName = :lastname", Employee.class)
                    .setParameter("lastname", employeeName)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

        return employee;
    }
}
