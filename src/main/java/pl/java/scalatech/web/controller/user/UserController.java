package pl.java.scalatech.web.controller.user;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.repository.UserRepository;

@RestController
@RequestMapping(value = "/api/resource/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private static final String NOT_EXISTS_USER_FOR_LOGIN = "not exists user for login : ";     
    private final @NonNull UserRepository userRepository;
       
    @RequestMapping(value="/{login}",method=GET)
    public ResponseEntity<UserContainer> getUserByLogin(@PathVariable String login){
        User user = userRepository.findByLogin(login).orElseThrow(()-> new ResourceNotFoundException(NOT_EXISTS_USER_FOR_LOGIN+login));
        return ok(ofNullable(user).map(u-> new UserContainer(u)).orElseThrow(()-> new ResourceNotFoundException(NOT_EXISTS_USER_FOR_LOGIN+login)));
    }
    
    @GetMapping("/count")
    ResponseEntity<Long> getCountAllUser(){     
        return ok(userRepository.count());
    }
    
    @GetMapping("/principal")
    ResponseEntity<Principal> getPrincipal(Principal principal){
        return ResponseEntity.ok(principal);
    }
        
}
