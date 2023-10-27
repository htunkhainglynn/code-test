package com.test.code.api;

import com.test.code.dto.RatingDto;
import com.test.code.service.RatingService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@Api(value = "Rating Management")
@Slf4j
public class RatingApi {

    private final RatingService ratingService;

    @Autowired
    public RatingApi(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/book/{bookId}")
    @Operation(summary = "Create a new rating")
    public ResponseEntity<Void> createRating(@PathVariable Integer bookId, @RequestBody RatingDto rating) {
        log.info("Rating: {}", rating);
        ratingService.createRating(bookId, rating.getRating());
        return ResponseEntity.status(201).build();
    }
}
