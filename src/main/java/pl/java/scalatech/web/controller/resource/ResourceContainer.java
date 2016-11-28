package pl.java.scalatech.web.controller.resource;

import static java.time.LocalDateTime.parse;
import static pl.java.scalatech.entity.Resource.UPLOAD_DATE;
import static pl.java.scalatech.entity.Resource.YYYY_MM_DD_HH_MM_SS;

import java.time.format.DateTimeFormatter;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import pl.java.scalatech.entity.Resource;
import pl.java.scalatech.service.filestorage.pojo.FileData;

public class ResourceContainer extends ResourceSupport {

    @Getter
    private Resource resource;

    @JsonCreator
    public ResourceContainer(Resource resource) {
        this.resource = resource;

    }

    @JsonCreator
    public ResourceContainer(FileData fd) {
        resource = new Resource(fd.getMd5(), fd.getFileName(), fd.getLength(), fd.getType(),
                parse(fd.getExtra().get(UPLOAD_DATE), DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)));

    }
}
