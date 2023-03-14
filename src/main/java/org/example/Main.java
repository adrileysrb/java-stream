package org.example;

import org.example.data.People;
import org.example.data.Product;
import org.example.data.Store;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        //Config store one
        Product prodStoreOne1 = new Product("Store One Prod 1", 3.12);
        Product prodStoreOne2 = new Product("Store One Prod 2", 1.82);
        Product prodStoreOne3 = new Product("Store One Prod 3", 5.22);

        Store storeOne = new Store();
        storeOne.setName("Store Two");
        storeOne.addProduct(prodStoreOne1);
        storeOne.addProduct(prodStoreOne2);
        storeOne.addProduct(prodStoreOne3);


        //Config store two
        Product prodStoreTwo1 = new Product("Store Two Prod 1", 3.12);
        Product prodStoreTwo2 = new Product("Store Two Prod 2", 1.82);
        Product prodStoreTwo3 = new Product("Store Two Prod 3", 5.22);

        Store storeTwo = new Store();
        storeTwo.setName("Store Two");
        storeTwo.addProduct(prodStoreTwo1);
        storeTwo.addProduct(prodStoreTwo2);
        storeTwo.addProduct(prodStoreTwo3);

        List<Store> stores = new ArrayList<>();
        stores.add(storeOne);
        stores.add(storeTwo);

        //Bad code
        for(Store store: stores){
            List<Product> products = store.getProducts();
            for(Product product : products){
                if(product.getName().contains("Prod 2")){
                    System.out.println(product.getName());
                }
            }
        }

        System.out.println("-------------------------------");
        //Better way
        stores.stream()
                .flatMap(store -> store.getProducts().stream())
                .map(product -> product.getName())
                .peek(product -> System.out.println("product: " + product))
                .filter(productName -> productName.contains("Prod 2"))
                .forEach(System.out::println);


        Product prod1 = new Product("Laranja", 3.12);
        Product prod2 = new Product("Ameixa", 1.82);
        Product prod3 = new Product("Xurupi", 5.22);


        System.out.println("-------------------------------");

        List<Product> prods = new ArrayList<>();
        prods.add(prod1);
        prods.add(prod2);
        prods.add(prod3);

        prods = prods.stream()
                .sorted((productA, productB )-> productA.getName().compareTo(productB.getName()))
                .collect(Collectors.toList());

        prods.stream().map(product -> product.getName()).forEach(System.out::println);

        System.out.println("-------------------------------");

        Product max = prods.stream()
                .max(Comparator.comparing(Product::getValor))
                .orElseThrow(NoSuchElementException::new);
        System.out.println(max.getValor());

        Product min = prods.stream()
                .min(Comparator.comparing(Product::getValor))
                .orElseThrow(NoSuchElementException::new);
        System.out.println(min.getValor());

        Stream<String> s = Stream.of("Geeks",
                "for",
                "Geeks",
                "GeeksforGeeks",
                "Geeks Classes");

        // using Collectors toSet() function
        Set<String> mySet = s.collect(Collectors.toSet());

        // printing the elements
        System.out.println(mySet);

        Stream<String> stream = Stream.of("Geeks",
                "for",
                "Geeks",
                "GeeksforGeeks",
                "Geeks Classes");

        // using Collectors toSet() function
        Set<String> myList = stream.collect(Collectors.toCollection(HashSet::new));
        System.out.println(myList);


        DoubleSummaryStatistics sumary  = prods.stream().collect(Collectors.summarizingDouble(Product::getValor));
        System.out.println(sumary.getCount());
        System.out.println(sumary.getSum());
        System.out.println(sumary.getAverage());
        System.out.println(sumary.getMin());
        System.out.println(sumary.getMax());

        System.out.println("-------------------------------");

        List<People> peopleList = new ArrayList<>();
        peopleList.add(new People("Zero", 23));
        peopleList.add(new People("Maya", 21));
        peopleList.add(new People("Tians", 129));
        peopleList.add(new People("Tina", 14));
        peopleList.add(new People("Thanos", 999));
        peopleList.add(new People("Roland", 32));

        sumary = peopleList.stream().mapToDouble(People::getAge).summaryStatistics();

        System.out.println(sumary.getCount());
        System.out.println(sumary.getSum());
        System.out.println(sumary.getAverage());
        System.out.println(sumary.getMin());
        System.out.println(sumary.getMax());

        System.out.println("-------------------------------");

        List<Integer> intergerList = Arrays.asList(1, 2, 3, 4, 5);
        Map<Boolean, List<Integer>> isEven = intergerList.stream().collect(Collectors.partitioningBy(i -> i % 2 == 0));
        System.out.println(isEven.get(true));
        System.out.println("------");
        System.out.println(isEven.get(false));

        Map<Character, List<People>> groupByAlphabet = peopleList.stream()
                .collect(Collectors.groupingBy(e -> e.getName().charAt(0)));

        groupByAlphabet.get('T').stream().map(people -> people.getName()).forEach(System.out::println);

      Map<Character, List<Integer>> ageGroupByAlphabet = peopleList.stream().collect(
          Collectors.groupingBy(e -> e.getName().charAt(0), Collectors.mapping(People::getAge, Collectors.toList())));

        ageGroupByAlphabet.get('T').stream().forEach(System.out::println);


        List<Integer> list = Arrays.asList(1, 2, 2, 3, 4);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        System.out.println("------");

        Comparator<People> byNameLength = Comparator.comparing(People::getName);

        Map<Character, Optional<People>> logestName = peopleList.stream().collect(
                Collectors.groupingBy(e -> Character.valueOf(e.getName().charAt(0)),
                Collectors.reducing(BinaryOperator.maxBy(byNameLength.reversed()))
        ));
        System.out.println(logestName.get('T').get().getName());

        System.out.println("------");


        Comparator<People> byAge = Comparator.comparing(People::getAge);
        Map<Character, Optional<People>> groupByAge = peopleList.stream()
                .collect(
                        Collectors.groupingBy(
                                e -> e.getName().charAt(0),
                                Collectors.reducing(BinaryOperator.maxBy(byAge))
                        )
                );

        Map<Character, Optional<Integer>> groupByAgeV2 = groupByAge.entrySet().stream()
                .collect(
                    Collectors.toMap(
                            y -> y.getKey(),
                            (v) -> v.getValue().map(x -> x.getAge()),
                            (v1, v2) -> v2
                    )
                );
       System.out.println(groupByAgeV2.get('T').get());

       peopleList.stream().parallel().map(e -> "Name: " +e.getName()).forEach(System.out::println);

    }

}