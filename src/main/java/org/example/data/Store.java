package org.example.data;

import java.util.ArrayList;

public class Store {
    private String name;
    private ArrayList<Product> products = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

}
