package com.trio.Elitecar.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trio.Elitecar.model.Product;
import com.trio.Elitecar.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class DataImportController {

    @Autowired
    private ProductRepo productRepo;

    @PostMapping("/products/import")
    public String importData(@RequestBody List<Product> products) {
        productRepo.saveAll(products);
        return "Data imported successfully!";
    }

}