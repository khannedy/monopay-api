package com.monopay.wallet.filter;

import com.monopay.wallet.model.web.WebResponse;
import com.monopay.wallet.properties.AuthenticationProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends ZuulFilter {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AuthenticationProperties authenticationProperties;

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext context = RequestContext.getCurrentContext();
    String apiKey = context.getRequest().getHeader("API-KEY");

    if (apiKey != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("API-KEY", apiKey);

      HttpEntity<String> entity = new HttpEntity<>(null, headers);

      ResponseEntity<WebResponse> responseEntity = restTemplate.exchange(authenticationProperties.getVerifyUrl(), HttpMethod.POST, entity, WebResponse.class);
      WebResponse webResponse = responseEntity.getBody();

      if (webResponse != null && webResponse.getData() != null) {
        context.addZuulRequestHeader("X-MERCHANT-ID", webResponse.getData().getId());
        context.addZuulRequestHeader("X-MERCHANT-NAME", webResponse.getData().getName());

        return null;
      }
    }

    context.setResponseStatusCode(401);
    if (context.getResponseBody() == null) {
      context.setResponseBody("{\n" +
        "  \"code\": 401,\n" +
        "  \"status\": \"UNAUTHORIZED\",\n" +
        "  \"data\": null\n" +
        "}");
      context.setSendZuulResponse(false);
    }

    return null;
  }
}
