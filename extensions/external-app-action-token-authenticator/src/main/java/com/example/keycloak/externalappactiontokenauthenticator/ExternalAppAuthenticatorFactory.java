
package com.example.keycloak.externalappactiontokenauthenticator;

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

public class ExternalAppAuthenticatorFactory implements AuthenticatorFactory {

  private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
    AuthenticationExecutionModel.Requirement.REQUIRED,
    AuthenticationExecutionModel.Requirement.DISABLED
  };

  public static final String CONFIG_EXTERNAL_APP_URL = "external-application-url";
  public static final String ID = "external-application-action-token-authenticator";

  @Override
  public Authenticator create(KeycloakSession session) {
    return new ExternalAppAuthenticator();
  }

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public void init(Scope scope) {}

  @Override
  public void postInit(KeycloakSessionFactory sessionFactory) {}

  @Override
  public String getDisplayType() {
    return "External Application Authenticator";
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
  public List<ProviderConfigProperty> getConfigProperties() {
    ProviderConfigProperty rep1 = new ProviderConfigProperty(CONFIG_EXTERNAL_APP_URL, "External Application Url",
          "URL of the application to redirect to. It has to contain token position marked with \"{TOKEN}\" (without quotes).",
          ProviderConfigProperty.STRING_TYPE, ExternalAppAuthenticator.DEFAULT_EXTERNAL_APP_URL);

    return Arrays.asList(rep1);
  }

  @Override
  public String getHelpText() {
    return "External Application Authenticator Help";
  }

  @Override
  public void close() {}

}
