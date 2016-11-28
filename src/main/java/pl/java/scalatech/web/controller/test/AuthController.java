package pl.java.scalatech.web.controller.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.service.filestorage.test.AuthService;

@RestController
@RequestMapping("/auth")
@Profile("auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @RequestMapping(method = RequestMethod.POST, value = "github")
  public OAuth2AccessToken authorizeWithGithub(@RequestBody Map<String, String> requestBody) {
    return authService.authorizeGithubUser(requestBody.get("code"), requestBody.get("redirectUri"));
  }

  @RequestMapping(method = RequestMethod.GET, value = "github")
  public OAuth2AccessToken getGithubAuthorization() {
    return authService.authorizeGithubUser(null, null);
  }

}