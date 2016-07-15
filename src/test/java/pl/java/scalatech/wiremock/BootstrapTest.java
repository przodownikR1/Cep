package pl.java.scalatech.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BootstrapTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(2876).usingFilesUnderClasspath("schema-repo-stub"));
       
   
    private RestTemplate restTemplate = new RestTemplate();
    
    @Test
    public void shouldBootstrap(){
      
    }
    @Test
    public void exampleTest() {
        WireMock.configureFor("localhost", 9999);

        
    }
    @Test
    public void exampleTest2() {
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>noodles burger cakes strudles</response>")));

    }
    @Test
    public void exampleTest3() {
        
        stubFor(get(urlEqualTo("/my/resource2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"id\":1, \"data\": \"same date\"}")));


        
//        assertEquals(200, planetSpaceStorageManager.testMethod());
    }
    String resourceUrl = "http://localhost:2876/api/resource";

    String getResource() {
      RestTemplate restTemplate = new RestTemplate();
      return restTemplate.getForObject(resourceUrl, String.class);
    }

    @Test
    public void shouldReadJson() throws IOException{
        String response = StreamUtils.copyToString(new ClassPathResource("json/my.json").getInputStream(), Charset.forName("UTF-8"));
        log.info("my.json :  {}",response);
    }
    
  
    

}
