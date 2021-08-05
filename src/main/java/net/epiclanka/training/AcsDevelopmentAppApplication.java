package net.epiclanka.training;

import org.apache.log4j.BasicConfigurator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AcsDevelopmentAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcsDevelopmentAppApplication.class, args);
        BasicConfigurator.configure();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
