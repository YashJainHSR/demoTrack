package com.tcs.internship.demotrack.controller;

import com.tcs.internship.demotrack.model.Products;
import com.tcs.internship.demotrack.repository.productsRepository;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/products")
public class ProductsController {

	@Autowired
	private productsRepository productsRepository;

	@GetMapping(path = "")
	public @ResponseBody Iterable<Products> getAllProducts() {
		return productsRepository.findAll();
	}

	@PostMapping(path = "")
	public @ResponseBody Products addProduct(@RequestBody @Valid Products product, BindingResult validationResult,
			HttpServletResponse response) throws IOException {
		if (validationResult.hasErrors()) {
			String err = "";
			List<FieldError> errors = validationResult.getFieldErrors();
			for (FieldError error : errors) {
				err += error.getDefaultMessage();
			}
			// response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(400, err);
			return null;
		}
		if (productsRepository.existsByProductGroupAndProductLineAndProduct(product.getProductGroup(),
				product.getProductLine(), product.getProduct())) {
			response.sendError(400, "User Already Exists");
			return null;
		} else {
			response.setStatus(201);
			return productsRepository.save(product);
		}
	}
}
