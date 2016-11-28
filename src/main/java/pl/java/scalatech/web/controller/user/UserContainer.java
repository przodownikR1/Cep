package pl.java.scalatech.web.controller.user;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import pl.java.scalatech.entity.User;

public class UserContainer extends ResourceSupport{

    @Getter
    private final User user;
    
    @JsonCreator
    public UserContainer(User user) {
        this.user = user;
    }
    
}
