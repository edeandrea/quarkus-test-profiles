package com.example;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "fruits")
public class Fruit extends PanacheEntity {
	public String name;
	public String description;

	@Override
	public String toString() {
		return "Fruit{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", id=" + id + '}';
	}
}
