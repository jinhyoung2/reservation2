package controller;
import model.Reservation;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import repository.StoreRepository;
import service.ReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private StoreRepository storeRepository;

    /**
     * 새로운 예약을 생성합니다.
     *
     * @param reservation 예약 정보를 담은 객체
     * @param user        현재 인증된 사용자
     * @return            생성된 예약 정보를 담은 HTTP 응답
     */
    @PostMapping("/make")
    public ResponseEntity<Reservation> makeReservation(@RequestBody Reservation reservation, @AuthenticationPrincipal User user) {
        reservation.setUser(user);  // 예약에 현재 인증된 사용자를 설정
        return ResponseEntity.ok(reservationService.makeReservation(reservation));  // 예약 생성 후 응답 반환
    }

    /**
     * 현재 인증된 사용자의 모든 예약을 조회합니다.
     *
     * @param user 현재 인증된 사용자
     * @return     사용자의 예약 목록을 담은 HTTP 응답
     */
    @GetMapping("/by-user")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(user));  // 사용자의 예약 목록을 조회하여 응답 반환
    }

    /**
     * 특정 상점과 날짜에 해당하는 모든 예약을 조회합니다.
     *
     * @param storeId 상점의 ID
     * @param date    조회할 날짜
     * @return        해당 날짜의 상점 예약 목록을 담은 HTTP 응답
     */
    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<Reservation>> getReservationsByStoreAndDate(@PathVariable Long storeId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reservationService.getReservationsByStoreAndDate(storeId, date));  // 특정 상점과 날짜의 예약 목록을 조회하여 응답 반환
    }

    /**
     * 특정 예약을 체크인합니다.
     *
     * @param reservationId 체크인할 예약의 ID
     * @param user          현재 인증된 사용자
     * @return              체크인된 예약 정보를 담은 HTTP 응답
     */
    @PostMapping("/checkin/{reservationId}")
    public ResponseEntity<Reservation> checkIn(@PathVariable Long reservationId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reservationService.checkIn(reservationId, user));  // 예약을 체크인 처리 후 응답 반환
    }

    /**
     * 특정 예약을 승인합니다.
     *
     * @param reservationId 승인할 예약의 ID
     * @param manager       현재 인증된 관리자
     * @return              승인된 예약 정보를 담은 HTTP 응답
     */
    @PostMapping("/approve/{reservationId}")
    public ResponseEntity<Reservation> approveReservation(@PathVariable Long reservationId, @AuthenticationPrincipal User manager) {
        return ResponseEntity.ok(reservationService.approveReservation(reservationId, manager));  // 예약을 승인 처리 후 응답 반환
    }

    /**
     * 특정 예약을 거절합니다.
     *
     * @param reservationId 거절할 예약의 ID
     * @param manager       현재 인증된 관리자
     * @return              거절 작업에 대한 HTTP 응답
     */
    @DeleteMapping("/reject/{reservationId}")
    public ResponseEntity<Void> rejectReservation(@PathVariable Long reservationId, @AuthenticationPrincipal User manager) {
        reservationService.rejectReservation(reservationId, manager);  // 예약을 거절 처리
        return ResponseEntity.noContent().build();  // 내용이 없는 응답 반환
    }
}

