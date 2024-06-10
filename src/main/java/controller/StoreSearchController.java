package controller;

import model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.StoreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class StoreSearchController {
    @Autowired
    private StoreRepository storeRepository;

    /**
     * 주어진 키워드를 포함하는 상점들을 검색하여 반환합니다.
     *
     * @param keyword 상점 이름 또는 주소에 포함된 검색 키워드
     * @return 검색된 상점 목록을 담은 HTTP 응답
     */
    @GetMapping("/stores")
    public ResponseEntity<List<Store>> searchStores(@RequestParam String keyword) {
        // 모든 상점을 조회한 후, 이름 또는 주소에 키워드를 포함하는 상점만 필터링
        List<Store> stores = storeRepository.findAll().stream()
                .filter(store -> store.getName().contains(keyword) || store.getAddress().contains(keyword))
                .collect(Collectors.toList());
        return ResponseEntity.ok(stores);  // 필터링된 상점 목록을 HTTP 응답으로 반환
    }

    /**
     * 특정 ID의 상점 세부 정보를 반환합니다.
     *
     * @param id 검색할 상점의 ID
     * @return 상점 세부 정보를 담은 HTTP 응답
     */
    @GetMapping("/stores/{id}")
    public ResponseEntity<Store> getStoreDetails(@PathVariable Long id) {
        // ID로 상점을 조회하고, 조회된 상점이 있으면 HTTP 응답으로 반환
        return ResponseEntity.of(storeRepository.findById(id));
    }
}