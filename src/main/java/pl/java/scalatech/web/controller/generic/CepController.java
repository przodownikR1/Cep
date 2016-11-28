package pl.java.scalatech.web.controller.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.entity.UserResource;
import pl.java.scalatech.repository.UserResourceRepository;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class CepController {
    final @NonNull
    protected UserResourceRepository userResourceRepository;
    private static final String NOT_EXISTS_USER_RESOURCE_FOR_USER = "not exists userResource for user:";
    
    protected UserResource findUserByLogin(String login) {
        return userResourceRepository.findByUserLogin(login).orElseThrow(()-> new ResourceNotFoundException(NOT_EXISTS_USER_RESOURCE_FOR_USER+login));
    }
}
