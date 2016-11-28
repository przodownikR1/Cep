package pl.java.scalatech.web.controller.userResources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import pl.java.scalatech.entity.UserResource;
import pl.java.scalatech.web.controller.quota.QoutaController;
import pl.java.scalatech.web.controller.resource.ResourceController;
import pl.java.scalatech.web.controller.user.UserController;

public class UserResourceContainer extends ResourceSupport {
    private static final String RESOURCE = "resource";
    private static final String OWNER = "owner";
    private static final String RESTRICTION = "restriction";
    @Getter
    private final UserResource userResource;

    @JsonCreator
    public UserResourceContainer(final UserResource userResource) {
        super();
        this.userResource = userResource;
        userResource.getResources().forEach(t -> {
            add(linkTo(methodOn(ResourceController.class).getResourceByMD5(t.getResourceMd5())).withRel(t.getResourceName()));
            add(linkTo(methodOn(ResourceController.class).getResourceInfoByMd5(t.getResourceMd5())).withRel(RESOURCE));
        }

        );
        add(linkTo(methodOn(QoutaController.class).getQuotaByUser(userResource.getUser().getLogin())).withRel(RESTRICTION));
        add(linkTo(methodOn(UserController.class).getUserByLogin(userResource.getUser().getLogin())).withRel(OWNER));
      
    }

}
