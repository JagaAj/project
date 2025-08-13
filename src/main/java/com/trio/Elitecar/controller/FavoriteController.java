package com.trio.Elitecar.controller;

import com.trio.Elitecar.model.Favorite;
import com.trio.Elitecar.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody Map<String, String> data) {
        Favorite fav = new Favorite();
        fav.setUserId(Long.parseLong(data.get("userId")));
        fav.setCarName(data.get("carName"));
        fav.setCarPrice(Double.parseDouble(data.get("carPrice")));

        // Decode base64 data URI
        String base64 = data.get("carImage");
        if (base64 != null && base64.startsWith("data:")) {
            base64 = base64.substring(base64.indexOf(",") + 1); // remove data:image/...;base64,
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        fav.setCarImage(imageBytes);

        return ResponseEntity.ok(favoriteRepository.save(fav));
    }
    @GetMapping("/{userId}")
    public List<Favorite> getFavorites(@PathVariable Long userId) {
        return favoriteRepository.findByUserId(userId);
    }



    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam Long userId, @RequestParam String carName) {
        favoriteRepository.deleteByUserIdAndCarName(userId, carName);
        return ResponseEntity.ok().build();
    }



}
