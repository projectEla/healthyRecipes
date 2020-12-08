package com.ela.data.services;

import com.ela.data.models.Recipe;
import com.ela.data.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Date.from;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public List<Recipe> recipesFromLastMonth() {
        return recipeRepository.findAll().stream().filter(recipe -> recipe.getAddedAt().after(from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.of("Europe/Warsaw")).toInstant())))
                .collect(Collectors.toList());
    }
}
