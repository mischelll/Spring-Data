package tasks;

import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RemoveTowns extends CommandImpl {
    public RemoveTowns(EntityManager entityManager, BufferedReader bufferedReader) {
        super(entityManager, bufferedReader);
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Enter town name: ");
        String townName = getBufferedReader().readLine();

        Town town = checkIfTownExists(townName);
        if (town == null) {
            System.out.println("No such town with given name found!");
            return;
        }

        removeTown(town);
    }

    private void removeTown(Town town) {
        this.getEntityManager().getTransaction().begin();

        List<Address> addressList = this.getEntityManager()
                .createQuery("FROM Address where town_id = :townId", Address.class)
                .setParameter("townId", town.getId())
                .getResultList();

        for (Address address : addressList) {
            for (Employee employee : address.getEmployees()) {
                employee.setAddress(null);
            }
        }

        for (Address address : addressList) {
            this.getEntityManager().remove(address);
        }
        this.getEntityManager().remove(town);

        int count = addressList.size();
        if (count == 0 || count == 1) {
            System.out.printf("%d address in %s deleted%n",
                    count,
                    town.getName());
        } else {
            System.out.printf("%d addresses in %s deleted%n",
                    count,
                    town.getName());
        }

        this.getEntityManager().getTransaction().commit();
    }

    private Town checkIfTownExists(String townName) {
        Town town = null;
        try {
            town = this.getEntityManager()
                    .createQuery("FROM Town where name = :townName", Town.class)
                    .setParameter("townName", townName)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.getMessage();
        }

        return town;
    }
}
