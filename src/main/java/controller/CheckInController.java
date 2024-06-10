package controller;

import model.Reservation;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ReservationRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {
    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * POST 요청을 받아서 예약 ID를 기반으로 사용자의 체크인 요청을 처리합니다.
     *
     * @param reservationId 체크인할 예약의 ID
     * @param user          현재 인증된 사용자
     * @return              체크인 성공 여부를 나타내는 HTTP 응답
     */
    @PostMapping("/{reservationId}")
    public ResponseEntity<String> checkIn(@PathVariable Long reservationId, @AuthenticationPrincipal User user) {
        // 예약 ID로 예약을 조회함. 결과가 Optional 타입으로 반환됨.
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        // 예약이 존재하고, 해당 예약이 현재 인증된 사용자와 연관된 경우
        if (reservation.isPresent() && reservation.get().getUser().equals(user)) {
            // 체크인 로직을 처리합니다.
            return ResponseEntity.ok("Check-in successful");
        }

        // 예약이 없거나, 해당 예약이 인증된 사용자와 연관이 없는 경우
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Check-in failed");
    }
}