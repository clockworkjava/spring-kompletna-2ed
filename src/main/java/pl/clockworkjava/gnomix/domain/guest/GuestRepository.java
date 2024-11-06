package pl.clockworkjava.gnomix.domain.guest;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class GuestRepository {

    List<Guest> guests = new ArrayList<>();

    public GuestRepository() {
        Guest guest = new Guest("Paweł", "Cwik", LocalDate.of(1986, 11, 13), Gender.MALE);
        Guest gabriel = new Guest("Gabriel", "Cwik", LocalDate.of(2016, 12, 13), Gender.MALE);
        this.guests.add(guest);
        this.guests.add(gabriel);
    }

    public List<Guest> findAll() {
        return Collections.unmodifiableList(this.guests);
    }

    public Guest create(String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        Guest newOne = new Guest(firstName, lastName, dateOfBirth, gender);
        this.guests.add(newOne);
        return newOne;
    }

    public Guest findById(long id) throws NoSuchElementException {
        return this.guests.stream().filter(guest -> guest.getId() == id).findFirst().orElseThrow();
    }

    public void removeById(long id) {
        Guest guestToBeRemoved = this.findById(id);
        this.guests.remove(guestToBeRemoved);
    }
}