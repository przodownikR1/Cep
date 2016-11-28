package pl.java.scalatech.web.controller.userResources;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.counting;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.repository.UserResourceRepository;
import pl.java.scalatech.web.controller.generic.CepController;

@RestController
@RequestMapping(value = "/api/userResource")
public class UserResourceController extends CepController {

    private static final String RESOURCE_NOT_FOUND = "resource not found ";
    @Autowired
    public UserResourceController(UserResourceRepository userResourceRepository) {
        super(userResourceRepository);
    }
    
    @GetMapping("/{login}")
    ResponseEntity<UserResourceContainer> getUserResourceByUser(@PathVariable String login){        
        return ok(ofNullable(findUserByLogin(login)).map(ur-> new UserResourceContainer(ur)).orElseThrow(()-> new ResourceNotFoundException(RESOURCE_NOT_FOUND+ login)));
    }
    @GetMapping("/count")
    ResponseEntity<Long> getCountAllResource(){
        return ok(userResourceRepository.findAll().stream().map(t -> t.getResources().size()).collect(counting()));
    }

}
