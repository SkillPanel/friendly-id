package com.devskiller.friendly_id.sample.jpa;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.devskiller.friendly_id.type.FriendlyId;

/**
 * Product entity demonstrating FriendlyId usage with JPA.
 * <p>
 * The FriendlyId is automatically converted to/from UUID in the database
 * thanks to the FriendlyIdConverter with @Converter(autoApply = true).
 * </p>
 */
@Entity
@Table(name = "products")
public class Product {

	@Id
	private FriendlyId id;

	@Column(nullable = false)
	private String name;

	@Column(length = 1000)
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer stock;

	// Default constructor required by JPA
	protected Product() {
	}

	public Product(String name, String description, BigDecimal price, Integer stock) {
		this.id = FriendlyId.random();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	// Getters and setters

	public FriendlyId getId() {
		return id;
	}

	public void setId(FriendlyId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
