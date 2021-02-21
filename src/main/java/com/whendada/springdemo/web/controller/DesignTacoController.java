package com.whendada.springdemo.web.controller;

import com.whendada.springdemo.web.domain.Order;
import com.whendada.springdemo.web.repository.IngredientRepository;
import com.whendada.springdemo.web.domain.Ingredient;
import com.whendada.springdemo.web.domain.Taco;
import com.whendada.springdemo.web.repository.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.whendada.springdemo.web.domain.Ingredient.Type;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private TacoRepository designRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepository) {
        this.ingredientRepo = ingredientRepo;
        this.designRepository = designRepository;

    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
            log.info(type.toString());
        }

        model.addAttribute("design", new Taco());

        return "design";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {

        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processDesign(@Valid Taco design, @ModelAttribute Order order,
                                Errors errors) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = designRepository.save(design);
        order.addDesign(design);

        log.info("Process design: " + design);

        return "redirect:/orders/current";
    }
}
