package com.example.Recommendation.System;

import com.example.Recommendation.System.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/recommend")
    public List<String> recommend(
            @RequestParam long userId,
            @RequestParam(defaultValue = "5") int count
    ) throws Exception {
        return recommendationService.getRecommendations(userId, count);
    }
}
