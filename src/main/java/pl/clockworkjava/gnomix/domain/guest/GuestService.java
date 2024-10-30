package pl.clockworkjava.gnomix.domain.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
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
}