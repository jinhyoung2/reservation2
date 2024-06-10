package controller;

import model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.StoreService;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    /**
     * 상점 목록을 이름순으로 정렬하여 반환합니다.
     *
     * @return 이름순으로 정렬된 상점 목록을 담은 HTTP 응답
     */
    @GetMapping("/sorted-by-name")
    public ResponseEntity<List<Store>> getStoresSortedByName() {
        return ResponseEntity.ok(storeService.getStoresSortedByName());  // 이름순으로 정렬된 상점 목록 반환
    }

    /**
     * 상점 목록을 평점순으로 정렬하여 반환합니다.
     *
     * @return 평점순으로 정렬된 상점 목록을 담은 HTTP 응답
     */
    @GetMapping("/sorted-by-rating")
    public ResponseEntity<List<Store>> getStoresSortedByRating() {
        return ResponseEntity.ok(storeService.getStoresSortedByRating());  // 평점순으로 정렬된 상점 목록 반환
    }
}