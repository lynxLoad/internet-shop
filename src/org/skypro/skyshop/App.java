package org.skypro.skyshop;

import org.skypro.skyshop.exception.BestResultNotFound;
import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.FixPriceProduct;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.article.Article;
import org.skypro.skyshop.engine.SearchEngine;

import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws BestResultNotFound {
        Product apple = new SimpleProduct("Яблоко", 50);
        Product bread = new SimpleProduct("Хлеб", 35);
        Product milk = new DiscountedProduct("Молоко", 80, 15);
        Product cheese = new DiscountedProduct("Сыр", 120, 10);
        Product juice = new FixPriceProduct("Сок");

        ProductBasket basket = new ProductBasket();

        System.out.println("\n=== Добавление продукта в корзину ===");
        basket.addProduct(apple);
        basket.addProduct(bread);
        basket.addProduct(milk);
        System.out.println("Добавлено 3 товара.");

        System.out.println("\n=== Печать содержимого корзины с несколькими товарами ===");
        basket.printBasket();

        System.out.println("\n=== Получение стоимости корзины с несколькими товарами ===");
        int total = basket.getTotalCost();
        System.out.println("Общая стоимость: " + total);

        System.out.println("\n=== Поиск товара, который есть в корзине ===");
        String searchName1 = "Молоко";
        boolean found1 = basket.checkProductByName(searchName1);
        System.out.println("Товар '" + searchName1 + "' найден в корзине? " + found1);

        System.out.println("\n=== Поиск товара, которого нет в корзине ===");
        String searchName2 = "Торт";
        boolean found2 = basket.checkProductByName(searchName2);
        System.out.println("Товар '" + searchName2 + "' найден в корзине? " + found2);

        System.out.println("\n=== Очистка корзины ===");
        basket.clearBasket();
        System.out.println("Корзина очищена.");

        System.out.println("\n=== Печать содержимого пустой корзины ===");
        basket.printBasket();

        System.out.println("\n=== Получение стоимости пустой корзины ===");
        int emptyTotal = basket.getTotalCost();
        System.out.println("Стоимость пустой корзины: " + emptyTotal);

        System.out.println("\n=== Поиск товара по имени в пустой корзине ===");
        boolean found3 = basket.checkProductByName("Яблоко");
        System.out.println("Товар 'Яблоко' найден в пустой корзине? " + found3);

        SearchEngine searchEngine = new SearchEngine(10);

        System.out.println("\n=== Добавление товаров в поисковый движок ===");
        searchEngine.add(apple);
        System.out.println("Добавлен: " + apple.getStringRepresentation());
        searchEngine.add(bread);
        System.out.println("Добавлен: " + bread.getStringRepresentation());
        searchEngine.add(milk);
        System.out.println("Добавлен: " + milk.getStringRepresentation());
        searchEngine.add(cheese);
        System.out.println("Добавлен: " + cheese.getStringRepresentation());
        searchEngine.add(juice);
        System.out.println("Добавлен: " + juice.getStringRepresentation());

        System.out.println("\n=== Добавление статей в поисковый движок ===");
        Article article1 = new Article("Как выбрать яблоки", "Статья о том, как правильно выбирать свежие яблоки в магазине");
        Article article2 = new Article("Польза молока", "Молоко содержит кальций и полезно для костей");
        Article article3 = new Article("Рецепт яблочного сока", "Как приготовить свежий яблочный сок в домашних условиях");

        searchEngine.add(article1);
        System.out.println("Добавлена статья: " + article1.getName());
        searchEngine.add(article2);
        System.out.println("Добавлена статья: " + article2.getName());
        searchEngine.add(article3);
        System.out.println("Добавлена статья: " + article3.getName());

        System.out.println("\n=== Поиск ===");
        Map<String, Searchable> results1 = searchEngine.search("яблоко");
        printSearchResults(results1);

        Map<String, Searchable> results2 = searchEngine.search("молоко");
        printSearchResults(results2);

        Map<String, Searchable> results3 = searchEngine.search("сок");
        printSearchResults(results3);

        Map<String, Searchable> results4 = searchEngine.search("хлеб");
        printSearchResults(results4);

        Map<String, Searchable> results5 = searchEngine.search("шоколад");
        printSearchResults(results5);

        Map<String, Searchable> results6 = searchEngine.search("как");
        printSearchResults(results6);

        System.out.println("\n=== Создание некорректных товаров ===");
        try {
            SimpleProduct emptyProduct = new SimpleProduct("   ", 100);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            SimpleProduct freeBeer = new SimpleProduct("Бесплатное пиво", 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            DiscountedProduct croutons = new DiscountedProduct("Сухарики", 0, 101);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            DiscountedProduct chips = new DiscountedProduct("Чипсы", 74, 101);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            FixPriceProduct invalidFix = new FixPriceProduct(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=== Поиск наиболее подходящего товара ===");

        try {
            Searchable bestMatch = searchEngine.searchMostSuitable("яблоко");
            System.out.println("Наиболее подходящий: " + bestMatch.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println(e.getMessage());
        }

        try {
            Searchable notFound = searchEngine.searchMostSuitable("ананас");
            System.out.println(notFound);
        } catch (BestResultNotFound e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=== Проверка метода удаления продукта по имени из корзины ===");

        basket.addProduct(apple);
        basket.addProduct(apple);
        basket.addProduct(bread);
        basket.addProduct(milk);

        System.out.println(">>> До удаления:");
        basket.printBasket();
        System.out.println(">>> После удаления:");
        List<Product> removedProducts = basket.removeProductsByName("яблоко");
        basket.printBasket();
        System.out.println(">>> Удаленные товары:");
        System.out.println(removedProducts);
        System.out.println(">>> Удаление несуществующего товара:");
        List<Product> removedProducts2 = basket.removeProductsByName("персик");
        if (removedProducts2.isEmpty()) System.out.println("Список пуст");
    }

    private static void printSearchResults(Map<String, Searchable> results) {
        if (results.isEmpty()) {
            System.out.println("Ничего не найдено.");
            return;
        }

        for (Map.Entry<String, Searchable> entry : results.entrySet()) {
            System.out.println("Найдено: " + entry.getValue().getStringRepresentation());
        }
        System.out.println("Всего найдено: " + results.size());
    }
}