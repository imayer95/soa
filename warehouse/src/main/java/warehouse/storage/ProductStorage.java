package warehouse.storage;

import domain.Product;
import domain.Products;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Component
//bean de spring
public class ProductStorage {

    private Products products = new Products();
    private Environment environment;    //envinronment - for warehouse detection

    public ProductStorage(Environment environment) {
        this.environment = environment;
    }

    public void init() {
        List<Product> products = new ArrayList<>();
        String[] profilesArray = environment.getActiveProfiles();
        List<String> profiles = asList(profilesArray);
        if (profiles.contains("warehouse1")) {
            Product milk = Product.builder()
                    .name("milk")
                    .price(10)
                    .quantity(200)
                    .build();
            Product juice = Product.builder()
                    .name("juice")
                    .price(5)
                    .quantity(8)
                    .build();
            Product peanuts = Product.builder()
                    .name("peanuts")
                    .price(7)
                    .quantity(31)
                    .build();

            products.add(milk);
            products.add(juice);
            products.add(peanuts);
        }
        if (profiles.contains("warehouse2")) {
            Product honey = Product.builder()
                    .name("honey")
                    .price(40)
                    .quantity(78)
                    .build();
            Product yoghurt = Product.builder()
                    .name("yoghurt")
                    .price(3)
                    .quantity(99)
                    .build();
            Product chips = Product.builder()
                    .name("chips")
                    .price(4)
                    .quantity(78)
                    .build();

            products.add(honey);
            products.add(yoghurt);
            products.add(chips);

        }

        if (profiles.contains("warehouse3")) {
            Product bread = Product.builder()
                    .name("bread")
                    .price(3)
                    .quantity(30)
                    .build();
            Product butter = Product.builder()
                    .name("butter")
                    .price(5)
                    .quantity(80)
                    .build();
            Product cheese = Product.builder()
                    .name("cheese")
                    .price(6)
                    .quantity(60)
                    .build();

            products.add(bread);
            products.add(butter);
            products.add(cheese);
        }

        this.products.setProducts(products);
    }

    public Products getProducts() {
        return products;
    }

    public void incrementQuantity(String name, int quantity) {
        products.getProducts()
                .stream()
                .filter(product -> product.getName().equals(name))
                .forEach(product -> product.setQuantity(product.getQuantity() + quantity));
    }

    public void updatePrice(String name, int price) {
        products.getProducts()
                .stream()
                .filter(product -> product.getName().equals(name))
                .forEach(product -> product.setPrice(price));
    }

    public void addProduct(Product product) {
        products.getProducts()
                .add(product);
    }
}
