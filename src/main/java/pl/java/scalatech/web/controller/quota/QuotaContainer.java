package pl.java.scalatech.web.controller.quota;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import pl.java.scalatech.entity.ResourceRestriction;

public class QuotaContainer extends ResourceSupport {
    @Getter
    private final ResourceRestriction resourceRestriction;

    @JsonCreator
    public QuotaContainer(ResourceRestriction resourceRestriction) {
        super();
        this.resourceRestriction = resourceRestriction;
    }

}
