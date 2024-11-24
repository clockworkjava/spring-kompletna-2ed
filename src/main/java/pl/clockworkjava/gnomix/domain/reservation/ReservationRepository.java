package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoom_Id(Long id);
    List<Reservation> findByConfirmed(boolean confirmed);
    Optional<Reservation> findFirstByConfirmedAndRoom_Id(boolean confirmed, Long id);
    Optional<Reservation> findFirstByConfirmedAndOwner_Id(boolean confirmed, Long id);
}
