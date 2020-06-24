package com.famiao.search.vo.form;

import java.io.Serializable;

import lombok.Data;

/**
 * @author zengbin
 * @since 2019-06-18 18:48
 */
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 3297890061660354238L;
    private String name;
    private Double price;
    private Integer count;
    private String category;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Double getPrice() {
	return price;
    }

    public void setPrice(Double price) {
	this.price = price;
    }

    public Integer getCount() {
	return count;
    }

    public void setCount(Integer count) {
	this.count = count;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }
}
