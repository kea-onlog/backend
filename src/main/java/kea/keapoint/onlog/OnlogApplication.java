package kea.keapoint.onlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OnlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlogApplication.class, args);
    }

}
