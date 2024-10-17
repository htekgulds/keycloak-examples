# External App Authenticator

External application action token example

## Content

1. Authenticator
2. AuthenticatorFactory
3. DefaultActionToken
4. AbstractActionTokenHandler
5. META-INF/services

## Setup

1. run `mvn clean package` inside folder `extensions`
2. run `docker compose up -d` in the root folder
3. log into Keycloak
4. go to "Authentication" page
5. under "Flows" tab, click browser flow and then athe right top corner click Actions -> Duplicate
6. under browser step click `+` button and add new step. choose External App Authenticator
7. set its requirement to `REQUIRED`

try logging in and check the keycloak logs for "Authenticating..."