package pl.java.scalatech.web.controller.quota;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.repository.UserResourceRepository;
import pl.java.scalatech.web.controller.generic.CepController;

@RestController
@RequestMapping(value = "/api/resource/quota")
@Slf4j
public class QoutaController extends CepController{
    private static final String NOT_EXISTS_QUOTA_FOR_LOGIN = "not exists quota for login : "; 
    @Autowired
    public QoutaController(UserResourceRepository userResourceRepository) {
        super(userResourceRepository);
    }

    @RequestMapping(value="/{login}",method=GET)  
    public ResponseEntity<QuotaContainer> getQuotaByUser(@PathVariable String login){         
        return ok(ofNullable(findUserByLogin(login).getResourceRestriction()).map(q-> new QuotaContainer(q)).orElseThrow(()-> new ResourceNotFoundException(NOT_EXISTS_QUOTA_FOR_LOGIN + login)));
    }
   
}
