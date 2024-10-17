package com.example.keycloak.externalappauthenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationProcessor;
import org.keycloak.authentication.actiontoken.AbstractActionTokenHandler;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.events.Errors;
import org.keycloak.events.EventType;
import org.keycloak.services.messages.Messages;
import org.keycloak.services.resources.LoginActionsService;
import org.keycloak.sessions.AuthenticationSessionCompoundId;
import org.keycloak.sessions.AuthenticationSessionModel;

import jakarta.ws.rs.core.Response;

public class ExternalAppActionTokenHandler extends AbstractActionTokenHandler<ExternalAppActionToken> {
  public static final String QUERY_PARAM_APP_TOKEN = "app-token";
  public static final String INITIATED_BY_ACTION_TOKEN_EXT_APP = "INITIATED_BY_ACTION_TOKEN_EXT_APP";

  private static final Logger logger = Logger.getLogger(ExternalAppActionTokenHandler.class);

  public ExternalAppActionTokenHandler() {
    super(
      ExternalAppActionToken.TOKEN_TYPE,
      ExternalAppActionToken.class,
      Messages.INVALID_REQUEST,
      EventType.EXECUTE_ACTION_TOKEN,
      Errors.INVALID_REQUEST
    );
  }

  @Override
  public Response handleToken(ExternalAppActionToken token, ActionTokenContext<ExternalAppActionToken> tokenContext) {
    logger.info("Handling token...");
    return tokenContext.processFlow(true, LoginActionsService.AUTHENTICATE_PATH, tokenContext.getRealm().getBrowserFlow(), null, new AuthenticationProcessor());
  }

  @Override
  public String getAuthenticationSessionIdFromToken(ExternalAppActionToken token,
      ActionTokenContext<ExternalAppActionToken> tokenContext, AuthenticationSessionModel currentAuthSession) {
    // always join current authentication session
    final String id = currentAuthSession == null
    ? null
    : AuthenticationSessionCompoundId.fromAuthSession(currentAuthSession).getEncodedId();

    logger.infof("Returning %s", id);

    return id;
  }

  

}
