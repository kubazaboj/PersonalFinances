package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryManager {
    private List<Category> categories;

    public CategoryManager() {
        this.categories = new ArrayList<>();
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category, SubcategoryManager subcategoryManager, ExpenseManager expenseManager) {
        List<Subcategory> subcategoriesToRemove = getAllCategorySubcategories(category);
        for (Subcategory subcategory: subcategoriesToRemove) {
            subcategoryManager.removeSubcategory(subcategory, expenseManager);
        }
        categories.remove(category);
    }

    public List<Category> getAllCategories() {
        return categories;
    }

    public List<Subcategory> getAllCategorySubcategories(Category category) {
        Set<Category> visitedCategories = new HashSet<>();
        List<Subcategory> allSubcategories = new ArrayList<>();
        collectSubcategories(category, allSubcategories, visitedCategories);
        return allSubcategories;
    }

    private void collectSubcategories(Category category, List<Subcategory> allSubcategories, Set<Category> visitedCategories) {
        if (visitedCategories.contains(category)) {
            return; // Stop processing if the category has already been visited
        }

        visitedCategories.add(category);

        for (Subcategory subcategory : category.getSubcategories()) {
            allSubcategories.add(subcategory);
            collectSubcategories(subcategory.getCategory(), allSubcategories, visitedCategories);
        }
    }
}