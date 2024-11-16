package pl.clockworkjava.gnomix.domain.guest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class GuestRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Guest create(String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        Guest newOne = new Guest(firstName, lastName, dateOfBirth, gender);
        entityManager.persist(newOne);
        return newOne;
    }

    public Guest findById(long id) throws NoSuchElementException {

        Guest result = entityManager.find(Guest.class, id);

        if (result==null) {
           throw new NoSuchElementException();
        } else {
           return result;
        }
    }

    @Transactional
    public void removeById(long id) {
        Guest guestToBeRemoved = this.findById(id);
        entityManager.remove(guestToBeRemoved);
    }

    public List<Guest> findAll() {
        return entityManager.createQuery("SELECT guest FROM Guest guest", Guest.class).getResultList();
    }

    @Transactional
    public void update(Guest guest) {
        entityManager.merge(guest);
    }
}