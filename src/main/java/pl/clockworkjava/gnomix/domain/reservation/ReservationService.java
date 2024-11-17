package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationRepository repository;

    @Autowired
    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }
}