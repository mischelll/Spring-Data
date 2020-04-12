package tasks;

import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class GetEmployeeWithProject extends CommandImpl {
    public GetEmployeeWithProject(EntityManager entityManager, BufferedReader bufferedReader) {
        super(entityManager, bufferedReader);
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Enter employee ID: ");
        int id = Integer.parseInt(getBufferedReader().readLine());

        Employee employee = checkIfEmployeeExists(id);
        if (checkIfEmployeeExists(id) == null) {
            System.out.println("No such employee with given ID!");
            return;
        }


        printEmployeeInformation(employee);
    }

    private void printEmployeeInformation(Employee employee) {
        System.out.printf("%s %s - %s%n\t%s",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employee.getProjects()
                        .stream()
                        .map(Project::getName)
                        .sorted()
                        .collect(Collectors.joining(System.lineSeparator() + "\t")));
    }

    private Employee checkIfEmployeeExists(int id) {
        Employee employee = null;
        try {
            employee = this.getEntityManager()
                    .createQuery("SELECT  e FROM Employee as e " +
                            "where e.id = :id", Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }

        return employee;
    }
}
