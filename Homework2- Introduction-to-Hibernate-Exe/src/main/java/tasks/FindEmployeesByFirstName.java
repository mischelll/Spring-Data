package tasks;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class FindEmployeesByFirstName extends CommandImpl {
    public FindEmployeesByFirstName(EntityManager entityManager, BufferedReader bufferedReader) {
        super(entityManager, bufferedReader);
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Enter a pattern: ");
        String pattern = getBufferedReader().readLine();


        List<Employee> employees = findEmployeesByPattern(pattern);
        if (employees.isEmpty()) {
            System.out.println("No employees with given pattern found!");
            return;
        }

        printEmployeeCredentials(employees);


    }

    private void printEmployeeCredentials(List<Employee> employees) {
        employees.forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                e.getFirstName(),
                e.getLastName(),
                e.getJobTitle(),
                e.getSalary()));
    }

    private List<Employee> findEmployeesByPattern(String pattern) {
        List<Employee> employeeList = null;
        try {
            employeeList = this.getEntityManager()
                    .createQuery("FROM Employee where first_name like concat(:pattern,'%')", Employee.class)
                    .setParameter("pattern", pattern)
                    .getResultList();
        } catch (NoResultException e) {
            e.getMessage();
        }

        return employeeList;
    }
}
