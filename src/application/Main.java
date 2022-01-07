package application;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = scanner.nextLine();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            List<Product> productList = new ArrayList<>();
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] fields = line.split(";");
                Product product = new Product(fields[0], Double.parseDouble(fields[1]));
                productList.add(product);
                line = bufferedReader.readLine();
            }

            double average = productList.stream().map(Product::getPrice).reduce(0.0, Double::sum) / productList.size();

            Comparator<String> stringComparator = Comparator.comparing(String::toUpperCase);

            List<String> names = productList.stream().filter(x -> x.getPrice() < average).map(Product::getName).sorted(stringComparator.reversed()).toList();

            System.out.println("Average: " + String.format("R$%.2f", average));
            names.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();

        }
        scanner.close();
    }
}
