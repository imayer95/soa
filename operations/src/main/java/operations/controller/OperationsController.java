package operations.controller;

import domain.Product;
import domain.Products;
import operations.service.RequestService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//toate bin-urile sunt singleton
@Controller
public class OperationsController {

    private static String WAREHOUSE1_URL = "http://";
    private static String WAREHOUSE2_URL = "http://";
    private static String WAREHOUSE3_URL = "http://";
    private static final String WAREHOUSE1 = "warehouse1";
    private static final String WAREHOUSE2 = "warehouse2";
    private static final String WAREHOUSE3 = "warehouse3";
    private static final Map<String, String> WAREHOUSES = new HashMap<>();
    private String currentWarehouse;

    private final RequestService requestService;
    private final Environment environment;

    public OperationsController(RequestService requestService, Environment environment) {
        this.requestService = requestService;
        this.environment = environment;
    }

    @PostConstruct
    private void initWarehouses(){
        WAREHOUSE1_URL+=environment.getProperty("WAREHOUSE1_HOST")+":"+environment.getProperty("WAREHOUSE1_PORT");
        WAREHOUSE2_URL+=environment.getProperty("WAREHOUSE2_HOST")+":"+environment.getProperty("WAREHOUSE2_PORT");
        WAREHOUSE3_URL+=environment.getProperty("WAREHOUSE3_HOST")+":"+environment.getProperty("WAREHOUSE3_PORT");

        WAREHOUSES.put(WAREHOUSE1, WAREHOUSE1_URL);
        WAREHOUSES.put(WAREHOUSE2, WAREHOUSE2_URL);
        WAREHOUSES.put(WAREHOUSE3, WAREHOUSE3_URL);
    }

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "welcome";
    }

    @GetMapping("/products")
    public String products(Map<String, Object> model) {
        model.put("warehouses",WAREHOUSES);
        model.put("products", getProducts());
        return "products";//HTML page
    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        if(currentWarehouse!=null && !currentWarehouse.equals("All")){
            return getProducts(currentWarehouse);
        }
        WAREHOUSES.keySet()
                .stream()
                .map(this::getProducts)
                .forEach(products::addAll);
        return products;
    }

    private List<Product> getProducts(String warehouse){
        String URL = WAREHOUSES.get(warehouse);
        try {
            return requestService.sendGet(URL + "/products", Products.class).getProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Exception occurred during call to warehouse!");
    }

    @PostMapping("/product/{warehouse}")
    public String product(@PathVariable String warehouse, Model model) {
        currentWarehouse=warehouse;
        return "redirect:/products";//incarcare pagina de products
    }

    @PostMapping("/sum")
    public String sum(Map<String, Object> model) {
        int result = 0;
        for (Product product : getProducts()) {
            result += product.getPrice();
        }
        products(model);
        model.put("sum", result);
        return "products";
    }

    @PostMapping("/quantity")
    public String quantity(Map<String, Object> model) {
        int result = 0;
        for (Object product : getProducts()) {
            result += ((Product) product).getPrice() * (((Product) product).getQuantity());
        }
        products(model);
        model.put("quantity", result);
        return "products";
    }

    @GetMapping("/lowStock")
    public String lowStock(Map<String, Object> model) {
        List<Product> productsList = new ArrayList<>();
        for (Product product : getProducts()) {
            if (product.getQuantity() < 100) {
                productsList.add(product);
            }
        }
        model.put("products", productsList);
        return "lowStock";
    }

    @PostMapping("/prodLowStock")
    public String prodLowStock(Map<String, Object> model) {
        Product productLow = null;
        int quantity = 999999999;
        for (Product product : getProducts()) {
            if (product.getQuantity() < quantity) {
                productLow = product;
                quantity = product.getQuantity();
            }
        }
        products(model);
        model.put("minStock", productLow);
        return "products";
    }

    @PostMapping("/prodMaxStock")

    public String prodMaxStock(Map<String, Object> model) {
        Product productMax = null;
        int quantity = 0;
        for (Product product : getProducts()) {
            if (product.getQuantity() > quantity) {
                productMax = product;
                quantity = product.getQuantity();
            }
        }
        products(model);
        model.put("maxStock", productMax);
        return "products";
    }

    @PostMapping("/maxQuantity")
    public String maxQuantity(Map<String, Object> model) {
        int maxQuantity = 0;
        for (Product product : getProducts()) {
            if (product.getQuantity() > maxQuantity)
                maxQuantity = product.getQuantity();
        }
        products(model);
        model.put("maxQuantity", maxQuantity);
        return "products";
    }

    @PostMapping("/maxPrice")
    public String maxPrice(Map<String, Object> model) {
        int maxPrice = 0;
        for (Product product : getProducts()) {
            if (product.getPrice() > maxPrice)
                maxPrice = product.getPrice();
        }
        products(model);
        model.put("maxPrice", maxPrice);
        return "products";
    }

    @PostMapping("/minQuantity")
    public String minQuantity(Map<String, Object> model) {
        int minQuantity = 999999999;
        for (Product product : getProducts()) {
            if (product.getQuantity() < minQuantity)
                minQuantity = product.getQuantity();
        }
        products(model);
        model.put("minQuantity", minQuantity);
        return "products";
    }

    @PostMapping("/minPrice")
    public String minPrice(Map<String, Object> model) {
        int minPrice = 99999999;
        for (Product product : getProducts()) {
            if (product.getPrice() < minPrice)
                minPrice = product.getPrice();
        }
        products(model);
        model.put("minPrice", minPrice);
        return "products";
    }
}
