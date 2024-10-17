package com.example.keycloak.externalappactiontokenauthenticator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.common.util.Time;
import org.keycloak.models.Constants;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.Urls;
import org.keycloak.sessions.AuthenticationSessionModel;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class ExternalAppAuthenticator implements Authenticator {
  private static final Logger logger = Logger.getLogger(ExternalAppAuthenticator.class);
  public static final String DEFAULT_EXTERNAL_APP_URL = "http://localhost:4000/auth/action?token={TOKEN}";

  @Override
  public void authenticate(AuthenticationFlowContext context) {
    logger.info("Authenticating...");
    String externalApplicationUrl = null;
    if (context.getAuthenticatorConfig() != null) {
      externalApplicationUrl = context.getAuthenticatorConfig().getConfig().get(ExternalAppAuthenticatorFactory.CONFIG_EXTERNAL_APP_URL);
    }
    if (externalApplicationUrl == null) {
      externalApplicationUrl = DEFAULT_EXTERNAL_APP_URL;
    }

    int validityInSecs = context.getRealm().getActionTokenGeneratedByUserLifespan();
    int absoluteExpirationInSecs = Time.currentTime() + validityInSecs;
    final AuthenticationSessionModel authSession = context.getAuthenticationSession();
    final String clientId = authSession.getClient().getClientId();

    // Create a token used to return back to the current authentication flow
    String token = new ExternalAppActionToken(
      context.getUser().getId(),
      absoluteExpirationInSecs,
      authSession.getParentSession().getId()
    ).serialize(
      context.getSession(),
      context.getRealm(),
      context.getUriInfo()
    );

    String submitActionTokenUrl = Urls
          .actionTokenBuilder(context.getUriInfo().getBaseUri(), token, clientId, authSession.getTabId(), "")
          .queryParam(Constants.EXECUTION, context.getExecution().getId())
          .queryParam(ExternalAppActionTokenHandler.QUERY_PARAM_APP_TOKEN, "{tokenParameterName}")
          .build(context.getRealm().getName(), "{APP_TOKEN}")
          .toString();
    
    try {
      Response challenge = Response
              .status(Status.FOUND)
              .header("Location", externalApplicationUrl.replace("{TOKEN}", URLEncoder.encode(submitActionTokenUrl, "UTF-8")))
              .build();
      
      context.challenge(challenge);
    } catch (UnsupportedEncodingException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void action(AuthenticationFlowContext context) {
    logger.info("Action...");
    String appTokenString = context.getUriInfo().getQueryParameters().getFirst(ExternalAppActionTokenHandler.QUERY_PARAM_APP_TOKEN);
    logger.info("Token: " + appTokenString);
    context.success();
  }

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
