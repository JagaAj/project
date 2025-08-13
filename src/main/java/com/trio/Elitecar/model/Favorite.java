package com.trio.Elitecar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String carName;
    @Lob
    @Column(name = "car_image", columnDefinition = "LONGBLOB")
    private byte[] carImage;
    private Double carPrice;
}
