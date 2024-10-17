package com.example.keycloak.customauthenticator;

import java.util.Collections;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class CustomAuthenticatorFactory implements AuthenticatorFactory {

  private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
    AuthenticationExecutionModel.Requirement.REQUIRED,
    AuthenticationExecutionModel.Requirement.DISABLED
  };

  public static final String ID = "custom-authenticator";

  @Override
  public Authenticator create(KeycloakSession session) {
    return new CustomAuthenticator();
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
    return "Custom Authenticator";
  }

  @Override
  public String getReferenceCategory() {
    return null;
  }

  @Override
  public boolean isConfigurable() {
    return false;
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
    return Collections.emptyList();
  }

  @Override
  public String getHelpText() {
    return "Custom Authenticator Help Text";
  }

  @Override
  public void close() {}

}
