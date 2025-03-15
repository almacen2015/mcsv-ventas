package backend.mcsvventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class McsvVentasApplication {

    public static void main(String[] args) {
        SpringApplication.run(McsvVentasApplication.class, args);
    }

}
