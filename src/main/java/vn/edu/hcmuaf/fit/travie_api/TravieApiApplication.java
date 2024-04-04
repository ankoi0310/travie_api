package vn.edu.hcmuaf.fit.travie_api;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@Log4j2
@EnableAsync
@SpringBootApplication
public class TravieApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravieApiApplication.class, args);
    }

}
