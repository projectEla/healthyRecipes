package com.ela.data.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewsletterService {

    private final RecipeService recipeService;

    public NewsletterService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    public void sendRecipesNewsletter() {
        recipeService.recipesFromLastMonth().stream().forEach(recipe -> System.out.println("Wysylam przepis: " + recipe.getTitle()));
    }

}
