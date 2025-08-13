package com.trio.Elitecar.repository;

import com.trio.Elitecar.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Optional<Favorite> findByUserIdAndCarName(Long userId, String carName);
    void deleteByUserIdAndCarName(Long userId, String carName);
}
