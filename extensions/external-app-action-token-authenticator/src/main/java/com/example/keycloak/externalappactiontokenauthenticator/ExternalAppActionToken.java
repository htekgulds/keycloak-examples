package com.example.keycloak.externalappactiontokenauthenticator;

import org.keycloak.authentication.actiontoken.DefaultActionToken;

public class ExternalAppActionToken extends DefaultActionToken {
  public static final String TOKEN_TYPE = "external-app-notification";

  public ExternalAppActionToken(String userId, int absoluteExpirationInSecs, String authenticationSessionId) {
      super(userId, TOKEN_TYPE, absoluteExpirationInSecs, null, authenticationSessionId);
  }  

  public ExternalAppActionToken() {}
}
