package com.example.keycloak.externalapprestauthenticator;

import java.util.Arrays;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class ExternalAppRestAuthenticatorFactory implements AuthenticatorFactory {

  private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
    AuthenticationExecutionModel.Requirement.REQUIRED,
    AuthenticationExecutionModel.Requirement.DISABLED,
    AuthenticationExecutionModel.Requirement.ALTERNATIVE
  };

  public static final String CONFIG_EXTERNAL_APP_URL = "external-application-url";
  public static final String ID = "external-app-rest-authenticator";

  @Override
  public Authenticator create(KeycloakSession session) {
    return new ExternalAppRestAuthenticator();
  }

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public String getDisplayType() {
    return "External Application Rest Authenticator";
  }

  @Override
  public String getReferenceCategory() {
    return null;
  }

  @Override
  public boolean isConfigurable() {
    return true;
  }

  @Override
  public Requirement[] getRequirementChoices() {
    return REQUIREMENT_CHOICES;
  }

  @Override
  public boolean isUserSetupAllowed() {
    return false;
  }

  @Override
  public String getHelpText() {
    return "External App Rest Authenticator Help";
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    ProviderConfigProperty rep1 = new ProviderConfigProperty(CONFIG_EXTERNAL_APP_URL, "External Application Url",
          "Base URL of the application to verify given code",
          ProviderConfigProperty.STRING_TYPE, ExternalAppRestAuthenticator.DEFAULT_EXTERNAL_APP_URL);

    return Arrays.asList(rep1);
  }

  @Override
  public void init(Scope config) {}

  @Override
  public void postInit(KeycloakSessionFactory factory) {}

  @Override
  public void close() {}

}
