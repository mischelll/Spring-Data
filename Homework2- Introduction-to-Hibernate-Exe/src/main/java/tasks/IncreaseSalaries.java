package tasks;

import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class IncreaseSalaries extends CommandImpl {
    public IncreaseSalaries(EntityManager entityManager, BufferedReader bufferedReader) {
        super(entityManager, bufferedReader);
    }

    @Override
    public void execute() throws IOException {
        List<Integer> ids = Arrays.asList(1, 2, 4, 11);
        this.getEntityManager().getTransaction().begin();

        List<Employee> employees = this.getEntityManager()
                .createQuery("FROM Employee where department.id in(:ids)", Employee.class)
                .setParameter("ids", ids)
                .getResultList();

        final int PERCENTAGE = 12;
        final int DIVISOR = 100;
        final int ROUNDING = 2;

        for (Employee employee : employees) {
            this.getEntityManager().detach(employee);
            BigDecimal increasement = (employee.getSalary().multiply(BigDecimal.valueOf(PERCENTAGE)))
                    .divide(BigDecimal.valueOf(DIVISOR), RoundingMode.valueOf(ROUNDING));

            employee.setSalary(employee.getSalary().add(increasement));
            this.getEntityManager().merge(employee);
            this.getEntityManager().flush();
        }

        this.getEntityManager().getTransaction().commit();

        printEmployeesWithIncreasedSalary(employees);
    }

    private void printEmployeesWithIncreasedSalary(List<Employee> employees) {
        employees.forEach(employee -> System.out.printf("%s %s ($%.2f)%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary()));
    }
}
