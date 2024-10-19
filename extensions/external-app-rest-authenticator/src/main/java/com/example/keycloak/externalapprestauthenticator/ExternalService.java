package com.example.keycloak.externalapprestauthenticator;

import java.util.concurrent.TimeUnit;

import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class ExternalService {
  private final ExternalClient client;

  public ExternalService(String baseUrl) {
    client = Feign.builder()
        .decoder(new JacksonDecoder())
        .options(new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true))
        .target(ExternalClient.class, baseUrl);
  }

  public ExternalUser getUser(String code) {
    log.infof("code %s", code);
    return client.get(code);
  }

}
