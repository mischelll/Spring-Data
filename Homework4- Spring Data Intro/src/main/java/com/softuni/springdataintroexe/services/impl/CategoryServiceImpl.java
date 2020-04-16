package com.softuni.springdataintroexe.services.impl;

import com.softuni.springdataintroexe.domain.entities.Category;
import com.softuni.springdataintroexe.repositories.CategoryRepository;
import com.softuni.springdataintroexe.services.interfaces.CategoryService;
import com.softuni.springdataintroexe.utils.FileReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.softuni.springdataintroexe.constants.GlobalConstants.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileReader fileReader;

    public CategoryServiceImpl(CategoryRepository categoryRepository, FileReader fileReader) {
        this.categoryRepository = categoryRepository;
        this.fileReader = fileReader;
    }

    @Override
    public void seedCategories() throws IOException {
        String[] fileContent = fileReader.readFileContent(CATEGORIES_FILE_PATH);
        if (categoryRepository.count() != 0) {
            List<Category> all = categoryRepository.findAll();
            List<String> categoryNames = new ArrayList<String>();

            all.forEach(c -> categoryNames.add(c.getName()));

            for (String name : fileContent) {
                if (!categoryNames.contains(name)) {
                    Category category = new Category(name);
                    this.categoryRepository.saveAndFlush(category);
                }
            }
        }else {
            Arrays.stream(fileContent).forEach(c -> {
                Category category = new Category(c);
                this.categoryRepository.saveAndFlush(category);
            });
        }
    }

    @Override
    public Category getCategoryById(long id) {
        return this.categoryRepository.getOne(id);
    }
}
