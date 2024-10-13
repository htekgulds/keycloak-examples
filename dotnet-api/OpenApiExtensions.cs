using System;
using Microsoft.OpenApi.Models;

namespace DotnetApiExample;

public static class OpenApiExtensions
{
  public static IServiceCollection AddSwaggerGenWithAuth(this IServiceCollection services, IConfiguration configuration)
  {
    services.AddSwaggerGen(o =>
    {
      o.CustomSchemaIds(id => id.FullName!.Replace('+', '-'));
      o.AddSecurityDefinition("Keycloak", new OpenApiSecurityScheme
      {
        Type = SecuritySchemeType.OAuth2,
        Flows = new OpenApiOAuthFlows
        {
          Implicit = new OpenApiOAuthFlow
          {
            AuthorizationUrl = new Uri(configuration["Auth:Url"]!),
            Scopes = new Dictionary<string, string>
            {
              { "openid", "openid" },
              { "profile", "profile" }
            }
          }
        }
      });

      o.AddSecurityRequirement(new OpenApiSecurityRequirement
      {
        {
          new OpenApiSecurityScheme
          {
            Reference = new OpenApiReference
            {
              Id = "Keycloak",
              Type = ReferenceType.SecurityScheme
            },
            In = ParameterLocation.Header,
            Name = "Bearer",
            Scheme = "Bearer"
          },
          []
        }
      });
    });

    return services;

  }

}
