package pl.java.scalatech.assembler;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import pl.java.scalatech.service.filestorage.pojo.FileData;
import pl.java.scalatech.web.controller.ResourceController;

@Slf4j
public class FileDataAssembler extends ResourceAssemblerSupport<FileData, FileDataResource> {

    public FileDataAssembler(Class<?> controllerClass, Class<FileDataResource> resourceType) {
        super(controllerClass, resourceType);

    }

    @Override
    public FileDataResource toResource(FileData entity) {
        FileDataResource ur = new FileDataResource(entity.getFileName(), entity.getLength(), entity.getMd5(), entity.getType(), entity.getContentType());
        // TODO !!!!! Principal problenm
        Principal p = new Principal() {

            @Override
            public String getName() {
                return "slawek";
            }
        };

        try {
            Optional<Principal> p1 = Optional.of(p);
            ur.add(linkTo(methodOn(ResourceController.class).getResource(ur.getMd5(),  p1)).withRel("resource"));
        } catch (IOException e) {
            log.error("create link to resource error ", e);
        }

        return ur;

    }

}