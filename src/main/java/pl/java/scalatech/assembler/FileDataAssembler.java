package pl.java.scalatech.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.security.Principal;
import java.util.Optional;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.filestorage.pojo.FileData;
import pl.java.scalatech.web.controller.ResourceController;

@Slf4j
public class FileDataAssembler extends ResourceAssemblerSupport<FileData, FileDataResource> {

    public FileDataAssembler(Class<?> controllerClass, Class<FileDataResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public FileDataResource toResource(FileData entity) {
        FileDataResource ur = new FileDataResource(entity.getFileName(), entity.getLength(), entity.getMd5(), entity.getType());
        // TODO !!!!! Principal problenm
        Principal p = () -> "slawek";
        Optional<Principal> p1 = Optional.of(p);
        ur.add(linkTo(methodOn(ResourceController.class).getResource(ur.getMd5(),  p1)).withRel("resource"));
        return ur;
    }

}