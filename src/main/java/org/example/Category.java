package org.example;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Subcategory> subcategories;

    public Category(String name) {
        this.name = name;
        this.subcategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void addSubcategory(Subcategory subcategory) {
        subcategories.add(subcategory);
    }

    public void removeSubcategory(Subcategory subcategory) {
        subcategories.remove(subcategory);
    }


    @Override
    public String toString() {
        return "Category{" +
                "name ='" + name + '\'' +
                ", subcategories =" + subcategories +
                '}';
    }
}