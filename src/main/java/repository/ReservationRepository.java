package repository;

import model.Reservation;
import model.Store;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStore(Store store);
    List<Reservation> findByUser(User user);
    List<Reservation> findByStoreAndReservationTimeBetween(Store store, LocalDateTime start, LocalDateTime end);

}