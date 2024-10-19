package com.example.keycloak.externalapprestauthenticator;

import feign.Param;
import feign.RequestLine;

interface ExternalClient {

  @RequestLine("GET /auth/rest?code={code}")
  ExternalUser get(@Param("code") String code);
}
