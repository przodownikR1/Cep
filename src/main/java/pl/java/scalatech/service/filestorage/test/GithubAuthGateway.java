package pl.java.scalatech.service.filestorage.test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("auth")
public class GithubAuthGateway {

  @Autowired
  @Qualifier("githubRestTemplate")
  private OAuth2RestTemplate githubRestTemplate;


  public String getGithubEmail() {
    final GithubEmail[] githubEmails = githubRestTemplate.getForObject("https://api.github.com/user/emails", GithubEmail[].class);
    final GithubEmail primaryGithubEmail = Arrays.stream(githubEmails).filter(githubEmail -> githubEmail.isPrimary()).findFirst().get();

    return primaryGithubEmail.getEmail();
  }

  @SuppressWarnings("unused")
  public static class GithubEmail {
    private String email;
    private boolean primary;
    private boolean verified;

    public GithubEmail() {
      // required for json mapping
    }

    public GithubEmail(String email, boolean primary, boolean verified) {
      this.email = email;
      this.primary = primary;
      this.verified = verified;
    }

    public String getEmail() {
      return email;
    }

    public boolean isPrimary() {
      return primary;
    }

  }
}