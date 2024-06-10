package service;

import model.Store;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.StoreRepository;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getStoresSortedByName() {
        return storeRepository.findAllByOrderByNameAsc();
    }

    public List<Store> getStoresSortedByRating() {
        return storeRepository.findAllByOrderByRatingDesc();
    }

}