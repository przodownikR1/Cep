package pl.java.scalatech.service.filestorage.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.repository.UserRepository;

@Service
@Slf4j
@Profile("auth")
public class AuthService {


  @Autowired
  private GithubAuthGateway githubAuthGateway;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthorizationServerTokenServices authorizationServerTokenServices;
  @Autowired
  private OAuth2ClientContext clientContext;
  @Autowired
  private OAuth2ClientProperties clientProperties;

  @Transactional
  public OAuth2AccessToken authorizeGithubUser(String authorizationCode, String redirectURI) {
    log.info("starting github authorization with authCode: {}, redirectUri: {}", authorizationCode, redirectURI);
    enhanceOAuth2ClientContext(authorizationCode, redirectURI);

    final User user = getUser(getGithubUserEmail());

    return createAccessToken(user);
  }

  private void enhanceOAuth2ClientContext(String authorizationCode, String redirectURI) {
    if (StringUtils.hasLength(authorizationCode)) {
      final AccessTokenRequest accessTokenRequest = clientContext.getAccessTokenRequest();
      accessTokenRequest.setAuthorizationCode(authorizationCode);
      accessTokenRequest.setCurrentUri(redirectURI);
    }
  }

  private String getGithubUserEmail() {
    return githubAuthGateway.getGithubEmail();
  }

  private User getUser(String email) {
    return userRepository.findByEmail(email).orElseGet(() -> {
      log.info("creating user for email: {}", email);
      return userRepository.save(User.builder().email(email).build());
    });
  }

  private OAuth2AccessToken createAccessToken(User user) {
    log.info("creating token for user: {}", user);
    final List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    final OAuth2Request request = new OAuth2Request(null, clientProperties.getClientId(), null, true, null, null, null, null, null);
    final OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(request, new UsernamePasswordAuthenticationToken(user.getEmail(), "N/A", authorities));

    return authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
  }
}