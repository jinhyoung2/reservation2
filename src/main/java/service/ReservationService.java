package service;

import model.Reservation;
import model.Store;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReservationRepository;
import repository.StoreRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StoreRepository storeRepository;

    public Reservation makeReservation(Reservation reservation) {
        reservation.setApproved(false);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByStore(Store store) {
        return reservationRepository.findByStore(store);
    }

    public List<Reservation> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    public Reservation checkIn(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        if (reservation.getUser().equals(user) && reservation.isApproved() && !reservation.isCheckedIn()) {
            reservation.setCheckedIn(true);
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Check-in failed.");
        }
    }

    public Reservation approveReservation(Long reservationId, User manager) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        if (reservation.getStore().getManager().equals(manager)) {
            reservation.setApproved(true);
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Approval failed.");
        }
    }

    public void rejectReservation(Long reservationId, User manager) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        if (reservation.getStore().getManager().equals(manager)) {
            reservationRepository.delete(reservation);
        } else {
            throw new RuntimeException("Rejection failed.");
        }
    }

    public List<Reservation> getReservationsByStoreAndDate(Long storeId, LocalDate date) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return reservationRepository.findByStoreAndReservationTimeBetween(store, startOfDay, endOfDay);
    }
}
