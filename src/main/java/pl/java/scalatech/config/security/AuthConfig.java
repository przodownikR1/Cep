package pl.java.scalatech.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@Profile("auth")
public class AuthConfig {
    
    @Configuration
    @Profile("auth")
    @EnableResourceServer
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

      @Override
      public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.GET, "/api/projects").permitAll()
          .antMatchers(HttpMethod.GET, "/api/technologies").permitAll()
          .antMatchers("/api/**").authenticated();

      }
    }

    @Configuration
    @Profile("auth")
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

      @Autowired
      private AuthenticationManager authenticationManager;
      @Autowired
      private OAuth2ClientProperties clientProperties;
      @Autowired
      private TokenStore tokenStore;
      @Bean
      public TokenStore tokenStore() {
          return new InMemoryTokenStore();
      }

      @Override
      public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
          .inMemory()
          .withClient(clientProperties.getClientId())
          .secret(clientProperties.getClientSecret())
          .authorizedGrantTypes("password", "refresh_token", "authorization_code")
          .scopes("read", "write")
          .authorities("ROLE_USER");
      }

      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
          .tokenStore(tokenStore)      
          .authenticationManager(authenticationManager);
      }
  }
}
