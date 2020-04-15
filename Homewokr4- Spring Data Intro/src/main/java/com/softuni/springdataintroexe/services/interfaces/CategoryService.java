package com.softuni.springdataintroexe.services.interfaces;

import com.softuni.springdataintroexe.domain.entities.Category;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CategoryService {
    void seedCategories() throws IOException;

    Category getCategoryById(long id);
}
