using System.Web;
using DotnetWebExample;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication.OpenIdConnect;
using Microsoft.IdentityModel.Protocols.OpenIdConnect;
using Microsoft.IdentityModel.Tokens;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorPages(options =>
{
    options.Conventions.AuthorizeFolder("/Private");
});

builder.Services.AddAuthentication(OpenIdConnectDefaults.AuthenticationScheme)
    .AddCookie(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddOpenIdConnect(options =>
    {
        options.RequireHttpsMetadata = false;
        options.SignInScheme = CookieAuthenticationDefaults.AuthenticationScheme;
        options.SignOutScheme = OpenIdConnectDefaults.AuthenticationScheme;
        options.Authority = "http://localhost:8080/realms/demo";
        options.ClientId = "dotnet-web";
        options.ClientSecret = "HPzn3beLp0E8dsJ7RG3Z9L6BridumcxA";
        options.ResponseType = OpenIdConnectResponseType.Code;
        options.SaveTokens = true;
        options.Scope.Add("openid");
        options.TokenValidationParameters = new TokenValidationParameters
        {
            NameClaimType = "preferred_username",
            RoleClaimType = "roles"
        };

        options.Events.OnRedirectToIdentityProvider = async n =>
        {
            // TODO: only append 'code' param
            var query = n.HttpContext.Request.QueryString;
            n.ProtocolMessage.RedirectUri = n.ProtocolMessage.RedirectUri + query;
            await Task.CompletedTask;
        };
    });

builder.Services.AddHttpContextAccessor();
builder.Services.AddTransient<AuthorizationHandler>();     

builder.Services.AddHttpClient("default")
    .AddHttpMessageHandler<AuthorizationHandler>();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
}

app.UseStaticFiles();
app.UseRouting();

app.MapGet("/auth/login", () => TypedResults.Challenge(new AuthenticationProperties { RedirectUri = "/" }));

app.MapGet("/auth/logout", async (HttpContext context) =>
{
    var idToken = await context.GetTokenAsync("id_token");
    var redirectUri = HttpUtility.UrlEncode("http://localhost:5001");
    var logoutUri = $"http://localhost:8080/realms/demo/protocol/openid-connect/logout?post_logout_redirect_uri={redirectUri}&id_token_hint={idToken}";
    await context.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
    await context.SignOutAsync(OpenIdConnectDefaults.AuthenticationScheme, new AuthenticationProperties { RedirectUri = logoutUri });

    return Results.Redirect(logoutUri);
});

app.MapGet("/users/me", async (IHttpClientFactory clientFactory) =>
{
    var client = clientFactory.CreateClient("default");
    var res = await client.GetAsync("http://localhost:5000/users/me");

    if (!res.IsSuccessStatusCode)
    {
        return Results.StatusCode((int)res.StatusCode);
    }

    var result = await res.Content.ReadAsStringAsync();

    return Results.Content(result, "application/json");
}).RequireAuthorization();

app.UseAuthentication();
app.UseAuthorization();
app.MapRazorPages();

app.Run();
