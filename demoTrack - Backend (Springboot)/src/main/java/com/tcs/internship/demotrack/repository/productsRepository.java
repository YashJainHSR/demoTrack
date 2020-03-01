package com.tcs.internship.demotrack.repository;

import com.tcs.internship.demotrack.model.Products;
import org.springframework.data.repository.CrudRepository;

public interface productsRepository extends CrudRepository<Products, Long> {

	Boolean existsByProductGroupAndProductLineAndProduct(String productGroup, String productLine, String product);
}
