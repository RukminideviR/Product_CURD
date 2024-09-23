package com.mouritech.product.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Products")

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "Product_Id")
	private Long productId;

	@Column(name = "Product_Name")
	private String productName;

	@Column(name = "Product_Price")
	private double productPrice;

	@Column(name = "Product_Description")
	private String productDescription;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Product(Long productId, String productName, double productPrice, String productDescription) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
	}

	public Product() {
		super();
	}

}
