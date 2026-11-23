package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import javax.sound.sampled.Port;
import java.util.*;

public class ProductBasket {
    private final Map<String, List<Product>> products;

    public ProductBasket() {
        this.products = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);;
    }

    public void addProduct(Product product) {
        products
                .computeIfAbsent(product.getName(), k -> new LinkedList<>())
                .add(product);
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (List<Product> group : products.values()) {
            for (Product product : group) {
                totalCost += product.getPrice();
            }
        }
        return  totalCost;
    }

    private int printCountSpecialProducts() {
        int specialCount = 0;
        for (List<Product> group : products.values()) {
            for (Product product : group) {
                System.out.println(product.toString());
                if (product.isSpecial()) {
                    specialCount++;
                }
            }
        }
        return specialCount;
    }

    public void printBasket() {
        if (products.isEmpty()) {
            System.out.println("В корзине пусто");
        } else {
            System.out.println("Итого: " + getTotalCost());
            System.out.println("Специальных товаров: " + printCountSpecialProducts());
        }
    }

    public boolean checkProductByName(String name) {
        return products.containsKey(name);
    }

    public void clearBasket() {
        products.clear();
    }

    public List<Product> removeProductsByName(String name) {
        List<Product> removed = products.remove(name);
        return removed != null ? removed : new LinkedList<>();
    }
}

















