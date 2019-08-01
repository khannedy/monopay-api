package com.monopay.wallet;

import com.monopay.wallet.properties.AuthenticationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableConfigurationProperties({
  AuthenticationProperties.class
})
public class MonoPayApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonoPayApiApplication.class, args);
  }

}
