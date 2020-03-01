package com.tcs.internship.demotrack.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.tcs.internship.demotrack.model.Products;
import com.tcs.internship.demotrack.repository.productsRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductsControllerTest {

	@Mock
	productsRepository productsRepository;

	@Mock
	HttpServletResponse response;

	@Mock
	BindingResult result;

	@InjectMocks
	ProductsController productsController;

	@Test
	public void getAllProductsTest() {
		List<Products> productList = new ArrayList<Products>();
		productList.add(new Products((long) 1, "Group-A", "Line-A", "Product-A"));
		when(productsRepository.findAll()).thenReturn(productList);
		productsController.getAllProducts()
				.forEach((a) -> assertEquals(new Products((long) 1, "Group-A", "Line-A", "Product-A"), a));
	}

	@Test
	public void addProductTest1() throws IOException {
		Products product = new Products((long) 1, "Group-A", "Line-A", "Product-A");
		when(productsRepository.existsByProductGroupAndProductLineAndProduct("Group-A", "Line-A", "Product-A"))
				.thenReturn(false);
		when(result.hasErrors()).thenReturn(false);
		when(productsRepository.save(product)).thenReturn(product);
		assertEquals(product, productsController.addProduct(product, result, response));
	}

	@Test
	public void addProductTest2() throws IOException {
		Products product = new Products((long) 1, "Group-A", "Line-A", "Product-A");
		when(result.hasErrors()).thenReturn(true);
		List<FieldError> errors = new ArrayList<FieldError>();
		errors.add(new FieldError("error", "id", "sampleError"));
		when(result.getFieldErrors()).thenReturn(errors);
		assertEquals(null, productsController.addProduct(product, result, response));
	}

	@Test
	public void addProductTest3() throws IOException {
		Products product = new Products((long) 1, "Group-A", "Line-A", "Product-A");
		when(productsRepository.existsByProductGroupAndProductLineAndProduct("Group-A", "Line-A", "Product-A"))
				.thenReturn(true);
		when(result.hasErrors()).thenReturn(false);
		assertEquals(null, productsController.addProduct(product, result, response));
	}
}
