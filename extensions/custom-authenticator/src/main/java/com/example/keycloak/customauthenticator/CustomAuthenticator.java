package com.example.keycloak.customauthenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class CustomAuthenticator implements Authenticator {
  private static final Logger logger = Logger.getLogger(CustomAuthenticator.class);

  @Override
  public void authenticate(AuthenticationFlowContext context) {
    logger.info("Authenticating...");
    context.success();
  }

  @Override
  public void action(AuthenticationFlowContext context) {}

  @Override
  public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
    return true;
  }

  @Override
  public boolean requiresUser() {
    return true;
  }

  @Override
  public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}


  @Override
  public void close() {}

}
