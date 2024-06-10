package controller;

import model.Review;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /**
     * 새로운 리뷰를 작성합니다.
     *
     * @param review  작성할 리뷰 객체
     * @param storeId 리뷰를 작성할 상점의 ID
     * @param userId  리뷰를 작성하는 사용자의 ID
     * @return        작성된 리뷰 정보를 담은 HTTP 응답
     */
    @PostMapping("/write")
    public ResponseEntity<Review> writeReview(@RequestBody Review review, @RequestParam Long storeId, @RequestParam Long userId) {
        return ResponseEntity.ok(reviewService.writeReview(review, storeId, userId));  // 리뷰 작성 후 응답 반환
    }

    /**
     * 기존 리뷰를 업데이트합니다.
     *
     * @param reviewId      업데이트할 리뷰의 ID
     * @param updatedReview 업데이트된 리뷰 객체
     * @param user          현재 인증된 사용자
     * @return              업데이트된 리뷰 정보를 담은 HTTP 응답
     */
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, updatedReview, user));  // 리뷰 업데이트 후 응답 반환
    }

    /**
     * 기존 리뷰를 삭제합니다.
     *
     * @param reviewId 삭제할 리뷰의 ID
     * @param user     현재 인증된 사용자
     * @return         삭제 작업에 대한 HTTP 응답
     */
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal User user) {
        reviewService.deleteReview(reviewId, user);  // 리뷰 삭제
        return ResponseEntity.noContent().build();  // 내용이 없는 응답 반환
    }

    /**
     * 특정 상점의 모든 리뷰를 조회합니다.
     *
     * @param storeId 상점의 ID
     * @return        상점에 해당하는 리뷰 목록을 담은 HTTP 응답
     */
    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<Review>> getReviewsByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(reviewService.getReviewsByStore(storeId));  // 특정 상점의 리뷰 목록 조회 후 응답 반환
    }

    /**
     * 특정 사용자의 모든 리뷰를 조회합니다.
     *
     * @param userId 사용자의 ID
     * @return       사용자에 해당하는 리뷰 목록을 담은 HTTP 응답
     */
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));  // 특정 사용자의 리뷰 목록 조회 후 응답 반환
    }
}
