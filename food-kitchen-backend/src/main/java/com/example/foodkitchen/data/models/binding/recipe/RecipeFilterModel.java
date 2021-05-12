package com.example.foodkitchen.data.models.binding.recipe;

import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFilterModel {

    private Set<FoodCategoryServiceModel> categories;
    private int limit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeFilterModel model = (RecipeFilterModel) o;
        return Objects.equals(categories, model.categories);
    }

    @Override
    public int hashCode() {

        if (categories.size() == 0){
            return Objects.hash(0);
        }

        int hashCode = categories.stream()
                .map(FoodCategoryServiceModel::getName)
                .map(Objects::hashCode).reduce(Integer::sum).get();

        return Objects.hash(hashCode);
    }
}
