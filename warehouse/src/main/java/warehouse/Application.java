package warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import warehouse.storage.ProductStorage;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    private ProductStorage productStorage;

    public Application(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);//sa porneasca aplicatia de spring
    }

    //apeleaza metoda init dupa initializarea contextului de spring
    @PostConstruct
    public void init(){
        productStorage.init();
    }

}
