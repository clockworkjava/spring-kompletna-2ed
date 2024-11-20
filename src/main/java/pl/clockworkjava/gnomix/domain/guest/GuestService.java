package pl.clockworkjava.gnomix.domain.guest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class GuestService {

    private final GuestRepository repository;

    @Autowired
    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findAll() {
        return this.repository.findAll();
    }

    public Guest create(String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        Guest newOne = new Guest(firstName, lastName, dateOfBirth, gender);
        return this.repository.save(newOne);
    }

    public void removeById(long id) throws NoSuchElementException {
        this.repository.deleteById(id);
    }

    public Optional<Guest> findById(long id) {

        return this.repository.findById(id);

    }

    public void editGuest(long id, String firstName, String lastName, LocalDate birthDate, Gender gender) {
        this.findById(id).ifPresent( guest -> {
                guest.modify(firstName, lastName, birthDate, gender);
                this.repository.save(guest);
        });
    }

    public Guest createNewGuest(String firstName, String lastName, LocalDate dateOfBirth) {
        Guest g = new Guest(firstName, lastName, dateOfBirth, Gender.MALE);
        this.repository.save(g);
        return g;
    }

    public void patch(long id, String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        this.findById(id).ifPresent( guest -> {
            guest.patch(firstName, lastName, dateOfBirth, gender);
            this.repository.save(guest);
        });
    }
}