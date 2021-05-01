package com.example.foodkitchen.unit;

import com.example.foodkitchen.data.entities.FoodCategory;
import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.recipe.RecipeFilterModel;
import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;
import com.example.foodkitchen.data.repositories.FoodCategoryRepository;
import com.example.foodkitchen.data.repositories.RecipeRepository;
import com.example.foodkitchen.data.repositories.UserRepository;
import com.example.foodkitchen.data.services.RecipeService;
import com.example.foodkitchen.data.services.impl.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeServiceUnitTest {

    RecipeService recipeService;

    RecipeRepository recipeRepository;
    UserRepository userRepository;
    FoodCategoryRepository foodCategoryRepository;
    ModelMapper modelMapper;

    List<Recipe> recipes;
    List<User> users;
    User principal;

    private static final String USER_USERNAME = "myUsername";
    private static final String MAIN_DISH_CATEGORY = "Main dishes";
    private static final String MEAT_CATEGORY = "Meat";
    private static final String DESSERTS_CATEGORY = "Desserts";
    private static final String DRINKS_CATEGORY = "Drinks";
    private static final String RECIPE_ID = "recipeId123";

    @BeforeEach
    public void init(){

        recipes = new ArrayList<>();
        users = new ArrayList<>();

        principal = new User();
        principal.setUsername(USER_USERNAME);

        recipeRepository = Mockito.mock(RecipeRepository.class);
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        Mockito.when(recipeRepository.saveAndFlush(Mockito.any(Recipe.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        userRepository = Mockito.mock(UserRepository.class);

        Mockito.when(userRepository.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        foodCategoryRepository = Mockito.mock(FoodCategoryRepository.class);

        Mockito.when(foodCategoryRepository.findByName(MAIN_DISH_CATEGORY))
                .thenReturn(new FoodCategory(MAIN_DISH_CATEGORY));

        Mockito.when(foodCategoryRepository.findByName(DESSERTS_CATEGORY))
                .thenReturn(new FoodCategory(DESSERTS_CATEGORY));

        Mockito.when(foodCategoryRepository.findByName(DRINKS_CATEGORY))
                .thenReturn(new FoodCategory(DRINKS_CATEGORY));

        modelMapper = new ModelMapper();

        recipeService = new RecipeServiceImpl(recipeRepository, userRepository,
                                                foodCategoryRepository, modelMapper);
    }

    @Test
    public void findAll_onSomeRecipes_shouldReturnTheirCount(){

        recipes.add(new Recipe());
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        assertEquals(recipes.size(), recipeService.findAll().size());
    }

    @Test
    public void findAll_onNoRecipes_shouldReturnZero(){
        assertEquals(0, recipeService.findAll().size());
    }

    @Test
    public void findByCategories_onNoSelectedCategories_shouldReturnAllRecipes(){

        recipes.add(new Recipe());
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        RecipeFilterModel model = new RecipeFilterModel();
        model.setCategories(new HashSet<>());

        assertEquals(recipes.size(), recipeService.findByCategories(model).size());
    }

    @Test
    public void findByCategories_onSelectedOneCategory_shouldReturnSizeCorrectly(){

        Recipe recipe = new Recipe();

        recipe.setCategories(new HashSet<>(){{
            add(new FoodCategory(MAIN_DISH_CATEGORY));
        }});

        Recipe recipe1 = new Recipe();

        recipe1.setCategories(new HashSet<>(){{
            add(new FoodCategory(DESSERTS_CATEGORY));
        }});

        recipes.add(recipe);
        recipes.add(recipe1);

        RecipeFilterModel model = new RecipeFilterModel();
        model.setCategories(new HashSet<>(){{
            add(new FoodCategoryServiceModel(MAIN_DISH_CATEGORY));
        }});

        assertEquals(1, recipeService.findByCategories(model).size());
    }

    @Test
    public void findByCategories_onSelectedMultipleCategories_shouldReturnSizeCorrectly(){

        Recipe recipe = new Recipe();

        recipe.setCategories(new HashSet<>(){{
            add(new FoodCategory(MAIN_DISH_CATEGORY));
        }});

        Recipe recipe1 = new Recipe();

        recipe1.setCategories(new HashSet<>(){{
            add(new FoodCategory(DESSERTS_CATEGORY));
        }});

        recipes.add(recipe);
        recipes.add(recipe1);

        RecipeFilterModel model = new RecipeFilterModel();
        model.setCategories(new HashSet<>(){{
            add(new FoodCategoryServiceModel(MAIN_DISH_CATEGORY));
            add(new FoodCategoryServiceModel(DESSERTS_CATEGORY));
        }});

        assertEquals(2, recipeService.findByCategories(model).size());
    }

    @Test
    public void findByCategories_onRecipesWithMultipleCategories_shouldReturnSizeCorrectly(){

        Recipe recipe = new Recipe();

        recipe.setCategories(new HashSet<>(){{
            add(new FoodCategory(MAIN_DISH_CATEGORY));
            add(new FoodCategory(MEAT_CATEGORY));
        }});

        Recipe recipe1 = new Recipe();

        recipe1.setCategories(new HashSet<>(){{
            add(new FoodCategory(DESSERTS_CATEGORY));
        }});

        recipes.add(recipe);
        recipes.add(recipe1);

        RecipeFilterModel model = new RecipeFilterModel();
        model.setCategories(new HashSet<>(){{
            add(new FoodCategoryServiceModel(MAIN_DISH_CATEGORY));
        }});

        assertEquals(1, recipeService.findByCategories(model).size());
    }

    @Test
    public void addRecipe_onUserEmptyRecipes_shouldAddItCorrectly(){

        Recipe recipe = new Recipe();
        recipe.setCategories(new HashSet<>(){{
            add(new FoodCategory(DRINKS_CATEGORY));
        }});

        users.add(principal);

        mockUserFindByUsername();

        recipeService.add(recipe, USER_USERNAME);

        assertEquals(1, principal.getRecipes().size());
    }

    @Test
    public void addRecipe_onUserWithRecipes_shouldAddItCorrectly(){

        Recipe recipe = new Recipe();

        recipe.setCategories(new HashSet<>(){{
            add(new FoodCategory(DRINKS_CATEGORY));
        }});

        Recipe recipe1 = new Recipe();

        recipe1.setCategories(new HashSet<>(){{
            add(new FoodCategory(MAIN_DISH_CATEGORY));
        }});

        users.add(principal);

        mockUserFindByUsername();

        recipeService.add(recipe, USER_USERNAME);
        recipeService.add(recipe1, USER_USERNAME);

        assertEquals(2, principal.getRecipes().size());
    }

    @Test
    public void findByID_onExistingRecipe_shouldReturnIt(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);

        mockRecipeFindById();

        assertNotNull(recipeService.findById(RECIPE_ID));
    }

    @Test
    public void findByID_onNonExistingRecipe_shouldReturnNull(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);

        mockRecipeFindById();

        assertNull(recipeService.findById("invalidID"));
    }

    @Test
    public void updateRating_onInvalidRecipe_shouldReturnNull(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);

        mockRecipeFindById();

        assertNull(recipeService.updateRating(new RecipeServiceModel(){{
            setId("invalidID");
        }}, principal));
    }

    @Test
    public void updateRating_onInvalidUser_shouldReturnNull(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();


        assertNull(recipeService.updateRating(new RecipeServiceModel(){{
            setId(recipe.getId());
        }}, new User(){{
            setUsername("invalidUsername");
        }}));
    }

    @Test
    public void updateRating_onInvalidRecipeAndUser_shouldReturnNull(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();


        assertNull(recipeService.updateRating(new RecipeServiceModel(){{
            setId("invalidId");
        }}, new User(){{
            setUsername("invalidUsername");
        }}));
    }

    @Test
    public void updateRating_onAlreadyVotedUser_shouldNotUpdateIt(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        principal.setRatedRecipes(new HashSet<>(){{
            add(recipe);
        }});


        double expectedRating = recipe.getRating();

        recipeService.updateRating(new RecipeServiceModel(){{
            setId(recipe.getId());
        }}, principal);

        assertEquals(expectedRating, recipe.getRating());
    }

    @Test
    public void updateRating_onRecipeWithNoRating_shouldUpdateIt(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        principal.setRatedRecipes(new HashSet<>(){{
            add(recipe);
        }});


        double expectedRating = recipe.getRating();

        RecipeServiceModel updated = recipeService.updateRating(new RecipeServiceModel() {{
            setId(recipe.getId());
        }}, principal);

        assertEquals(expectedRating, updated.getRating());
    }

    @Test
    public void updateRating_onRecipeWithRating_shouldUpdateIt(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);
        recipe.setRating(4);

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        principal.setRatedRecipes(new HashSet<>());


        double expectedRating = 4.5;

        RecipeServiceModel updated = recipeService.updateRating(new RecipeServiceModel() {{
            setId(recipe.getId());
            setRating(5);
        }}, principal);

        assertEquals(expectedRating, updated.getRating());
    }

    @Test
    public void addToFavorites_onInvalidRecipeOrUser_shouldReturnRecipe(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);
        recipe.setFans(new HashSet<>());

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        principal.setFavorites(new HashSet<>());

        recipeService.addToFavorites(new RecipeServiceModel(){{
            setId("invalidID");
        }}, new User(){{
            setUsername("invalidUsername");
        }});

        assertEquals(0, recipe.getFans().size());
    }

    @Test
    public void addToFavorites_onAlreadyAddedRecipe_shouldReturnRecipe(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);
        recipe.setFans(new HashSet<>(){{
            add(principal);
        }});

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        principal.setFavorites(new HashSet<>(){{
            add(recipe);
        }});

        recipeService.addToFavorites(new RecipeServiceModel(){{
            setId(recipe.getId());
        }}, principal);

        assertEquals(1, recipe.getFans().size());
    }

    @Test
    public void addToFavorites_onNotLikedRecipe_shouldUpdateFansCount(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        principal.setFavorites(new HashSet<>());

        recipeService.addToFavorites(new RecipeServiceModel(){{
            setId(recipe.getId());
        }}, principal);

        assertEquals(1, recipe.getFans().size());
    }

    @Test
    public void findFavoriteRecipes_onUserNotFound_shouldReturnNull(){
        assertNull(recipeService.findFavoriteRecipes("invalidUsername"));
    }

    @Test
    public void findFavoriteRecipes_onExistingUser_shouldReturnItsSize() {

        principal.setFavorites(new HashSet<>() {{
            add(new Recipe());
            add(new Recipe());
        }});

        users.add(principal);

        mockUserFindByUsername();

        assertEquals(2, recipeService.findFavoriteRecipes(USER_USERNAME).size());
    }

    @Test
    public void deleteFromFavorites_onInvalidRecipeOrUser_shouldReturnNull(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        assertNull(recipeService.deleteFromFavorites("invalidId", "invalidUsername"));
    }

    @Test
    public void deleteFromFavorites_onValidData_shouldUpdateIt(){

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setUser(principal);
        recipe.setFans(new HashSet<>(){{
            add(principal);
        }});

        recipes.add(recipe);
        users.add(principal);

        mockRecipeFindById();
        mockUserFindByUsername();

        principal.setFavorites(new HashSet<>(){{
            add(recipe);
        }});

        assertEquals(0,
                recipeService.deleteFromFavorites(recipe.getId(), principal.getUsername()).getFans().size());
    }

    private void mockRecipeFindById() {
        Mockito.when(recipeRepository.findById(RECIPE_ID))
                .thenReturn(recipes.stream()
                        .filter(r -> r.getId().equals(RECIPE_ID))
                        .findAny());
    }

    private void mockUserFindByUsername() {
        Mockito.when(userRepository.findByUsername(USER_USERNAME))
                .thenReturn(users.stream()
                        .filter(u -> u.getUsername().equals(USER_USERNAME))
                        .findFirst()
                        .orElse(null));
    }

}
