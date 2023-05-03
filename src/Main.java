import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/* ! flatMap */

public class Main {
    public static void main(String[] args) {
        List<Customer> customerList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        customerList.add(new Customer(1264778975L, "Popescu Andrei", 3));
        customerList.add(new Customer(5254634224L, "Oprea Alina", 2));

        productList.add(new Product(4335466219L, "The Book Thief", "Books", 49.99));
        productList.add(new Product(7235466220L, "Nineteen Eighty-Four", "Books", 36.80));
        productList.add(new Product());
        productList.add(new Product(1785165221L, "The Complete Sherlock Holmes Collection", "Books", 224.00));
        productList.add(new Product(5689466229L, "General Relativity Coursebook", "Books", 324.99));
        productList.add(new Product(5335466300L, "Botany and Gardens in Early Modern Ireland", "Books", 292.99));
        productList.add(new Product(6335423362L, "Lost & Found", "Books", 188.99));
        productList.add(new Product(1379423565L, "Odyssey", "Books", 82.99));
        productList.add(new Product(4569846512L, "Pacifier", "Baby", 8.99));
        productList.add(new Product(6482312873L, "Feeding Bottle", "Baby", 12.60));
        productList.add(new Product(6482312873L, "Stroller", "Baby", 499.99));
        productList.add(new Product(6482312873L, "Crib", "Baby", 179.90));
        productList.add(new Product(6482312873L, "Stuffed Bear", "Toys", 22.30));
        productList.add(new Product(6482312873L, "Lego", "Toys", 49.80));
        productList.add(new Product(6482312873L, "Doll", "Toys", 9.39));

        Set<Product> firstProductSet = new HashSet<>();
        firstProductSet.add(new Product(6482312873L, "Doll", "Toys", 9.39));
        firstProductSet.add(new Product(6335423362L, "Lost & Found", "Books", 188.99));
        firstProductSet.add(new Product(4569846512L, "Pacifier", "Baby", 8.99));

        Set<Product> secondProductSet = new HashSet<>();
        secondProductSet.add(new Product(4335466219L, "The Book Thief", "Books", 49.99));

        orderList.add(new Order(1234512873L, LocalDate.of(2020, Month.SEPTEMBER, 17),
                LocalDate.of(2020, Month.SEPTEMBER, 19), "delivered",
                new Customer(1264778975L, "Popescu Andrei", 2), firstProductSet));
        orderList.add(new Order(1234512873L, LocalDate.of(2021, Month.MARCH, 14),
                LocalDate.of(2021, Month.MARCH, 19), "delivered",
                new Customer(1264778975L, "Popescu Andrei", 3), firstProductSet));
        orderList.add(new Order(1234512873L, LocalDate.of(2021, Month.MARCH, 14),
                LocalDate.of(2020, Month.MARCH, 18), "delivered",
                new Customer(1264778975L, "Oprea Alina", 2), secondProductSet));
        orderList.add(new Order(1234512873L, LocalDate.of(2021, Month.MARCH, 21),
                LocalDate.of(2021, Month.MARCH, 29), "delivered",
                new Customer(5254634224L, "Oprea Alina", 2), firstProductSet));
        orderList.add(new Order(1234512098L, LocalDate.of(2020, Month.NOVEMBER, 11),
                LocalDate.of(2020, Month.DECEMBER, 1), "delivered",
                new Customer(5254634224L, "Oprea Alina", 2), secondProductSet));
        orderList.add(new Order());

        // 1. Obtain a list of products belongs to category “Books” with price > 100
        List<Product> products = productList.stream()
                .filter(e -> e != null)
                .filter(e -> e.getCategory().equals("Books"))
                .filter(e -> e.getPrice() > 100)
                .collect(Collectors.toList());

        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();

        // 2. Obtain a list of orders with at least one product belonging to category “Baby”
        List<Order> atLeastOneBabyItem = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getProducts().stream()
                    .filter(e -> e != null)
                    .anyMatch(e -> e.getCategory().equals("Baby"))) {
                atLeastOneBabyItem.add(order);
            }
        }
        for (Order order : atLeastOneBabyItem) {
            System.out.println(order);
        }
        System.out.println();

        // 3. Obtain a list of products with category = “Toys” and then apply 10% discount
        List<Product> toys = productList.stream()
                .filter(e -> e != null)
                .filter(e -> e.getCategory().equals("Toys"))
                .collect(Collectors.toList());
        toys.forEach(e -> e.priceDiscount(10.0));

        for (Product product : toys) {
            System.out.println(product);
        }
        System.out.println();

        // 4. Obtain a list of products ordered by customers of tier 2 between 01-Feb-2021 and 01-Apr-2021
        List<Order> orders = orderList.stream()
                .filter(e -> e != null)
                .filter(e -> e.getCustomer().getTier() == 2)
                .filter(e -> e.getOrderDate().isAfter(LocalDate.of(2021, Month.FEBRUARY, 1))
                        && e.getOrderDate().isBefore(LocalDate.of(2021, Month.APRIL, 1)))
                .collect(Collectors.toList());
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println();

        // 5. Get the cheapest product of “Books” category
        Product cheapestBook = productList.stream()
                .filter(e -> e != null)
                .filter(e -> e.getCategory().equals("Books"))
                .min(Comparator.comparingDouble(Product::getPrice)).get();
        System.out.println(cheapestBook);
        System.out.println();

        // 6. Get the 3 most recent placed orders
        List<Order> newestOrders = orderList.stream()
                .filter(order -> order.getOrderDate() != null || order.getDeliveryDate() != null)
                .sorted((e1, e2) -> e1.getOrderDate().compareTo(e2.getOrderDate()))
                .limit(3)
                .collect(Collectors.toList());
        for (Order order : newestOrders) {
            System.out.println(order);
        }
        System.out.println();

        // 7. Get the order with the highest total price
        Double maximum = 0.0;
        Order highestOrder = new Order();
        for (Order order : orderList) {
            Double highestPrice = order.getProducts().stream()
                    .filter(e -> e != null)
                    .map(Product::getPrice)
                    .reduce(Double.valueOf(0.0), (a, b) -> a + b);

            if (maximum < highestPrice) {
                maximum = highestPrice;
                highestOrder = order;
            }
        }
        System.out.println(highestOrder);
        System.out.println();

        // 8. Calculate order average payment placed on 14-Mar-2021
        Long counter = orderList.stream()
                .filter(e -> e != null && e.getDeliveryDate() != null && e.getOrderDate() != null)
                .filter(e -> e.getOrderDate().equals(LocalDate.of(2021, Month.MARCH, 14)))
                .count();
        Double avgPayment = 0.0;
        for (Order order : orderList) {
            avgPayment += order.getProducts().stream()
                    .filter(e -> e != null && order.getDeliveryDate() != null && order.getOrderDate() != null)
                    .filter(e -> order.getOrderDate().equals(LocalDate.of(2021, Month.MARCH, 14)))
                    .map(Product::getPrice)
                    .reduce(Double.valueOf(0.0), (a, b) -> a + b);
        }
        System.out.println(avgPayment / counter);
        System.out.println();

        // 9. Get the most expensive product by category
        Set<String> categories = new HashSet<>();
        productList.forEach(e -> {
            if (!categories.contains(e)) {
                categories.add(e.getCategory());
            }
        });

        List<Product> mostExpensiveByCategory = new ArrayList<>();
        categories.forEach(c -> {
            mostExpensiveByCategory.add(productList.stream()
                    .filter(e -> e != null)
                    .filter(e -> e.getCategory().equals(c))
                    .max(Comparator.comparingDouble(Product::getPrice)).get());
        });
        System.out.println(mostExpensiveByCategory);

        //10. Get the product that was ordered the highest number of times
        Set<Product> allProducts = new HashSet<>();
        orderList.forEach(order -> {
            order.getProducts().forEach(product -> {
                if (!allProducts.contains(product)) {
                    allProducts.add(product);
                }
            });
        });

        Product highestNumberOfOrders = new Product();
        Long mxm = 0L;
        for (Product product : allProducts) {
           Long cnt = orderList.stream()
                    .map(Order::getProducts)
                    .filter(e -> e != null)
                    .filter(e -> e.contains(product))
                    .count();
            if (cnt > mxm) {
                mxm = cnt;
                highestNumberOfOrders = product;
                break;
            }
        }
        System.out.println(highestNumberOfOrders);
    }
}