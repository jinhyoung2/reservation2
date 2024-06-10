package service;

import model.Review;
import model.Store;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ReviewRepository;
import repository.StoreRepository;
import repository.UserRepository;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    public Review writeReview(Review review, Long storeId, Long userId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        review.setStore(store);
        review.setUser(user);
        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId, Review updatedReview, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getUser().equals(user)) {
            review.setRating(updatedReview.getRating());
            review.setComment(updatedReview.getComment());
            return reviewRepository.save(review);
        } else {
            throw new RuntimeException("You can only update your own reviews.");
        }
    }

    public void deleteReview(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getUser().equals(user) || review.getStore().getManager().equals(user)) {
            reviewRepository.delete(review);
        } else {
            throw new RuntimeException("You do not have permission to delete this review.");
        }
    }

    public List<Review> getReviewsByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        return reviewRepository.findByStore(store);
    }

    public List<Review> getReviewsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reviewRepository.findByUser(user);
    }
}
