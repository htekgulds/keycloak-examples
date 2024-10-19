package com.example.keycloak.externalapprestauthenticator;

import java.net.URI;
import java.util.Optional;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.keycloak.authentication.AuthenticationFlowContext;

import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class Helper {

  public static String getConfig(AuthenticationFlowContext context, String configName, String defaultCOnfig) {
      if (context.getAuthenticatorConfig() == null) return null;

      String config = context.getAuthenticatorConfig().getConfig().get(configName);
      if (config != null) return config;
      else return defaultCOnfig;
  }

  public static String getCode(AuthenticationFlowContext context) {
    MultivaluedMap<String, String> params = context.getHttpRequest().getUri().getQueryParameters();
    String redirectUri = params.getFirst("redirect_uri");
    log.info(String.format("Redirect URI: %s", redirectUri));

    String code = null;
    try {
      Optional<NameValuePair> pair = new URIBuilder(redirectUri)
          .getQueryParams()
          .stream()
          .filter(t -> t.getName().equals("code"))
          .findFirst();
      
      if (!pair.isPresent()) {
        return null;
      }

      code = pair.get().getValue();
    } catch (Exception e) {
      log.error(e);
      return null;
    }
    log.info(String.format("Code: %s", code));
    return code;
  }
}
