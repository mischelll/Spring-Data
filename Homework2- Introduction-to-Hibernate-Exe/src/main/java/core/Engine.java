package core;

import common.DbManipulation;
import entities.Address;
import entities.Employee;
import entities.Project;
import tasks.*;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
           // DbManipulation.insertData(entityManager);
            String inputTask = bufferedReader.readLine();
            System.out.println("--------START OF TASK--------");
            switch (inputTask) {
                case "Remove Objects":
                    new RemoveObjects(entityManager, bufferedReader).execute();

                    break;
                case "Contains Employee":
                    new ContainsEmployee(entityManager, bufferedReader).execute();

                    break;
                case "Employees with Salary Over 50 000":
                    getEmployeesSalary();

                    break;
                case "Employees from Department":
                    employeesFromDepartment();

                    break;
                case "Adding a New Address and Updating Employee":
                    new AddingANewAddressAndUpdatingEmployee(this.entityManager, this.bufferedReader).execute();

                    break;
                case "Addresses with Employee Count":
                    addressesWithEmployeeCount();

                    break;
                case "Get Employee With Project":
                    new GetEmployeeWithProject(this.entityManager, this.bufferedReader).execute();

                    break;
                case "Find Latest 10 Projects":
                    findLatestTenProjects();

                    break;
                case "Increase Salaries":
                    new IncreaseSalaries(this.entityManager, this.bufferedReader).execute();

                    break;
                case "Remove Towns":
                    new RemoveTowns(this.entityManager, this.bufferedReader).execute();

                    break;
                case "Find Employees by First Name":
                    new FindEmployeesByFirstName(this.entityManager, this.bufferedReader).execute();

                    break;
                case "Employees Maximum Salaries":
                    employeesMaximumSalaries();

                    break;
            }


        } catch (IOException e) {
            System.out.println("Invalid task name!");
        }
    }

    private void employeesMaximumSalaries() {
        List<Object[]> resultList = this.entityManager
                .createQuery("select department.name, max(salary)" +
                        "from Employee" +
                        " group by department.id\n" +
                        "having max(salary) not between 30000 and 70000", Object[].class)
                .getResultList();
        System.out.println();
        for (Object[] objects : resultList) {
            System.out.printf("%s - %.2f%n", objects[0], objects[1]);
        }
    }

    private void findLatestTenProjects() {
        final int LAST_TEN_PROJECTS = 10;
        List<Project> projects = this.entityManager
                .createQuery("FROM Project order by start_date DESC", Project.class)
                .setMaxResults(LAST_TEN_PROJECTS)
                .getResultList();

        projects
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project ->
                        System.out.printf("Project name: %s%n\tProject Description: " +
                                        "%s%n\tProject Start Date: %s%n\tProject End Date: %s%n",
                                project.getName(),
                                project.getDescription(),
                                project.getStartDate(),
                                project.getEndDate()));
    }

    private void addressesWithEmployeeCount() {
        List<Address> addresses = this.entityManager
                .createQuery("FROM Address " +
                        "order by  size(employees) desc, town.id", Address.class)
                .setMaxResults(10)
                .getResultList();
        System.out.println();

        addresses.forEach(e -> System.out.printf("%s, %s - %s employees%n",
                e.getText(),
                e.getTown().getName(),
                e.getEmployees().size()));
    }

    private void getEmployeesSalary() {
        final double PARAM = 50000;
        this.entityManager.createQuery("SELECT e from Employee as e " +
                "where e.salary > :salary", Employee.class)
                .setParameter("salary", BigDecimal.valueOf(PARAM))
                .getResultStream()
                .forEach(e -> System.out.println(e.getFirstName()));
    }

    private void employeesFromDepartment() {
        final String DEPARTMENT = "Research and Development";
        this.entityManager
                .createQuery("SELECT e from Employee  as e " +
                        "where e.department.name = :department " +
                        "order by e.salary ASC , e.id ASC", Employee.class)
                .setParameter("department", DEPARTMENT)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n"
                        , e.getFirstName()
                        , e.getLastName()
                        , e.getDepartment().getName()
                        , e.getSalary()));
    }


}
