package com.example.keycloak.externalapprestauthenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class ExternalAppRestAuthenticator implements Authenticator {
  public static final String DEFAULT_EXTERNAL_APP_URL = "http://localhost:4000";

  @Override
  public void authenticate(AuthenticationFlowContext context) {
    log.info("Authenticating...");

    String code = Helper.getCode(context);

    if (code == null) {
      context.attempted();
      return;
    }

    String externalApplicationUrl = Helper.getConfig(
      context,
      ExternalAppRestAuthenticatorFactory.CONFIG_EXTERNAL_APP_URL,
      DEFAULT_EXTERNAL_APP_URL
    );

    try {
      ExternalService service = new ExternalService(externalApplicationUrl);
      ExternalUser obj = service.getUser(code);
      
      UserModel user = KeycloakModelUtils.findUserByNameOrEmail(
        context.getSession(),
        context.getRealm(),
        obj.getUsername()
      );
      context.setUser(user);
      context.success();
    } catch (Exception e) {
      log.error(e);
      context.attempted();
    }
  }

  @Override
  public void action(AuthenticationFlowContext context) {}

  @Override
  public boolean requiresUser() {
    return false;
  }

  @Override
  public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
    return true;
  }

  @Override
  public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}

  @Override
  public void close() {}

}
