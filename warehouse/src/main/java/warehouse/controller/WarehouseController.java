package warehouse.controller;

import domain.Product;
import domain.Products;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.storage.ProductStorage;

@RestController
public class WarehouseController {

    private ProductStorage productStorage;

    public WarehouseController(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    @GetMapping("/products")
    public Products products() {
        return productStorage.getProducts();
    }

    @PostMapping("/incrementQuantity")
    public void incrementQuantity(String name, int quantity) {
        productStorage.incrementQuantity(name, quantity);
    }

    @PostMapping("/updatePrice")
    public void updatePrice(String name, int price) {
        productStorage.updatePrice(name, price);
    }

    @PostMapping("/addProduct")
    public void addProduct(Product product) {
        System.out.println(product);

        productStorage.addProduct(product);
    }
}
//controller-ul face disponibile metodele de request