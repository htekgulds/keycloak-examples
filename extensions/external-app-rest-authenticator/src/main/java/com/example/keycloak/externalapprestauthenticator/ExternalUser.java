package com.example.keycloak.externalapprestauthenticator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExternalUser {
  private String username;
}
