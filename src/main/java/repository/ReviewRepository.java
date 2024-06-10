package repository;

import model.Review;
import model.Store;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStore(Store store);
    List<Review> findByUser(User user);
}