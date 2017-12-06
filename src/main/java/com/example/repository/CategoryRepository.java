package com.example.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {
Category findByCategoryName(String categoryName);
List<Category> findAllByOrderByCategoryNameAsc();
}
