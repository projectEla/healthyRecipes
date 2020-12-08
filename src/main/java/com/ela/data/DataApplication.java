package com.ela.data;

import com.ela.data.models.Recipe;
import com.ela.data.models.Tag;
import com.ela.data.repository.RecipeRepository;
import com.ela.data.services.NewsletterService;
import com.ela.data.services.RecipeService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Stream.of;

@SpringBootApplication
@EnableScheduling
public class DataApplication {


    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializer(RecipeRepository repository) {
        Recipe recipe1 = Recipe.builder().title("Fit szarlotka z ashwagandą")
                .description("Szybka w przygotowaniu wege szarlotka na uspokojenie!").content("1. Przygotuj jablka. 2. Przygotuj mąkę. 3. Przygotuj ashwa..").addedAt(new Date()).build();
        Recipe recipe2 = Recipe.builder().title("Prozdrowotny shake z goku kola")
                .description("Szybki w przygotowaniu shake poprawiający wygląd skóry!").content("1. Przygotuj jarmuż. 2. Przygotuj mleko sojowe. 3. Przygotuj goku kola..").addedAt(DateUtils.addDays(new Date(), -35)).build();

        Tag tag1 = Tag.builder().name("Uspokojenie").build();
        Tag tag2 = Tag.builder().name("Wege").build();
        Tag tag3 = Tag.builder().name("Wyglad").build();
        Tag tag4 = Tag.builder().name("Shake").build();

        recipe1.setTags(of(tag1,tag2).collect(Collectors.toSet()));
        recipe1.setTags(of(tag3,tag4).collect(Collectors.toSet()));

        tag1.setPosts(new HashSet<>(asList(recipe1)));
        tag2.setPosts(new HashSet<>(asList(recipe1)));
        tag3.setPosts(new HashSet<>(asList(recipe2)));
        tag4.setPosts(new HashSet<>(asList(recipe2)));

        return args -> repository.saveAll(asList(
                recipe1, recipe2
        ));
    }

    @Bean
    public String test(RecipeService recipeService, NewsletterService newsletterService){
//        recipeService.recipesFromLastMonth();
        newsletterService.sendRecipesNewsletter();
        return "";
    }

}
