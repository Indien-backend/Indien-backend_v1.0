package com.indien.indien_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class IndienBackendApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(IndienBackendApplication.class, args);
    }

}
