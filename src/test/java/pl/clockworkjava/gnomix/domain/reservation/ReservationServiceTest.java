package pl.clockworkjava.gnomix.domain.reservation;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.clockworkjava.gnomix.domain.room.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationServiceTest {


    @Test
    public void testIfGetAvRoomFailsForTooSmallSize() {

        //given
        ReservationRepository repo = Mockito.mock(ReservationRepository.class);
        RoomService rs = Mockito.mock(RoomService.class);
        ReservationService reservationService = new ReservationService(repo, rs);

        //when then
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservationService.getAvailableRooms(LocalDate.now(), LocalDate.now().plusDays(1), -5);
                }
        );
    }

    @Test
    public void testIfGetAvRoomFailsForTooBigSize() {

        //given
        ReservationRepository repo = Mockito.mock(ReservationRepository.class);
        RoomService rs = Mockito.mock(RoomService.class);
        ReservationService reservationService = new ReservationService(repo, rs);

        //when then
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservationService.getAvailableRooms(LocalDate.now(), LocalDate.now().plusDays(1), 15);
                }
        );
    }

    @Test
    public void testIfGetAvRoomFailsForSameDates() {

        //given
        ReservationRepository repo = Mockito.mock(ReservationRepository.class);
        RoomService rs = Mockito.mock(RoomService.class);
        ReservationService reservationService = new ReservationService(repo, rs);

        //when then
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservationService.getAvailableRooms(LocalDate.now(), LocalDate.now(), 5);
                }
        );
    }

    @Test
    public void testIfGetAvRoomFailsForToBeforeFrom() {

        //given
        ReservationRepository repo = Mockito.mock(ReservationRepository.class);
        RoomService rs = Mockito.mock(RoomService.class);
        ReservationService reservationService = new ReservationService(repo, rs);

        //when then
        assertThrows(IllegalArgumentException.class,
                () -> {
                    reservationService.getAvailableRooms(LocalDate.now().plusDays(1), LocalDate.now(), 5);
                }
        );
    }

    @Test
    public void testPredicateGetReservationForTheSameStartDatePositive() {


        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-02-05");

        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationStartsAtTheSameDate(myStartDate))
                .toList();

        //then
        assertEquals(1, collect.size());
    }

    @Test
    public void testPredicateGetReservationForTheSameStartDateNegative() {

        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-02-25");

        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationStartsAtTheSameDate(myStartDate))
                .toList();

        //then
        assertEquals(0, collect.size());
    }

    @Test
    public void testPredicateGetReservationForTheSameEndDatePositive() {

        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-02-15");

        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationEndsAtTheSameDate(myStartDate))
                .toList();

        //then
        assertEquals(1, collect.size());


    }

    @Test
    public void testPredicateGetReservationForTheSameEndDateNegative() {

        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-02-25");

        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationEndsAtTheSameDate(myStartDate))
                .toList();

        //then
        assertEquals(0, collect.size());
    }

    @Test
    public void testPredicateGetReservationForStartsBetweenPositive() {

        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-01-05");
        LocalDate myEndDate = LocalDate.parse("2022-01-10");

        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationStartsBetween(myStartDate, myEndDate))
                .toList();

        //then
        assertEquals(1, collect.size());


    }

    @Test
    public void testPredicateGetReservationForStartsBetweenNegative() {

        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-02-25");
        LocalDate myEndDate = LocalDate.parse("2022-02-28");


        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationStartsBetween(myStartDate, myEndDate))
                .toList();

        //then
        assertEquals(0, collect.size());
    }

    @Test
    public void testPredicateGetReservationForEndsBetweenPositive() {

        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-01-09");
        LocalDate myEndDate = LocalDate.parse("2022-01-12");

        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationEndsBetween(myStartDate, myEndDate))
                .toList();

        //then
        assertEquals(1, collect.size());


    }

    @Test
    public void testPredicateGetReservationForEndsBetweenNegative() {

        //given
        List<Reservation> reservations = new ArrayList<>();

        LocalDate fromOne = LocalDate.parse("2022-01-08");
        LocalDate toOne = LocalDate.parse("2022-01-10");

        reservations.add(new Reservation(fromOne, toOne, null, null));

        LocalDate fromTwo = LocalDate.parse("2022-02-05");
        LocalDate toTwo = LocalDate.parse("2022-02-15");

        reservations.add(new Reservation(fromTwo, toTwo, null, null));

        LocalDate myStartDate = LocalDate.parse("2022-02-25");
        LocalDate myEndDate = LocalDate.parse("2022-02-28");


        //when
        List<Reservation> collect = reservations
                .stream()
                .filter(ReservationService.reservationEndsBetween(myStartDate, myEndDate))
                .toList();

        //then
        assertEquals(0, collect.size());
    }
}