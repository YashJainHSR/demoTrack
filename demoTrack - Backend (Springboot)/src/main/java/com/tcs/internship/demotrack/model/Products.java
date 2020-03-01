package com.tcs.internship.demotrack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank(message = "Product Group cannot be blank")
	private String productGroup;

	@NotNull
	@NotBlank(message = "Product Line cannot be blank")
	private String productLine;

	@NotNull
	@NotBlank(message = "Product cannot be blank")
	private String product;

	public Products(Long id, String productGroup, String productLine, String product) {
		super();
		this.id = id;
		this.productGroup = productGroup;
		this.productLine = productLine;
		this.product = product;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public String getProductLine() {
		return productLine;
	}
	public String getProduct() {
		return product;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Products))
			return false;
		Products products = (Products) o;
		return id.equals(products.id);
	}
}
