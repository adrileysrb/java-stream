package org.example.data;

public class Product {
    private String name;
    private Double valor;

    public Product() {
    }

    public Product(String name, Double valor) {
        this.name = name;
        this.valor = valor;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
