package pl.clockworkjava.gnomix.domain.guest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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
        return this.repository.create(firstName, lastName, dateOfBirth, gender);
    }

    public boolean removeById(long id) throws NoSuchElementException {
        try {
            this.repository.removeById(id);
            return true;
        } catch (NoSuchElementException ex) {
            log.info("Guest marked for removal not found");
            return false;
        }
    }
}