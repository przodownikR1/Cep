package pl.java.scalatech.assembler;

import java.security.Principal;

import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.java.scalatech.service.filestorage.pojo.FileData;

@Slf4j
public class FileDataAssembler extends ResourceAssemblerSupport<FileData, FileDataResource> {

    public FileDataAssembler(Class<?> controllerClass, Class<FileDataResource> resourceType) {
        super(controllerClass, resourceType);

    }

    @Override
    public FileDataResource toResource(FileData entity) {
        FileDataResource ur = new FileDataResource(entity.getFileName(),entity.getLength(),entity.getMd5(),entity.getType(),entity.getContentType());
        // TODO
        Principal p = new Principal() {

            @Override
            public String getName() {
                return "slawek";
            }
        };

       /* try {
            ur.add(linkTo(methodOn(ResourceController.class).getResource(ur.getMd5(), p)).withSelfRel());
        } catch (IOException e) {
            log.error("create link to resource error ", e);
        }*/

        return ur;

    }

}