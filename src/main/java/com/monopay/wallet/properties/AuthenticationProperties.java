package com.monopay.wallet.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("authentication")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationProperties {

  private String verifyUrl;

}
