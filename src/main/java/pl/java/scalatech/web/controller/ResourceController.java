package pl.java.scalatech.web.controller;

import java.io.IOException;
import java.security.Principal;

import javax.activation.MimetypesFileTypeMap;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResourceController {
    private final @NonNull FileService fileService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getResource(@PathVariable("md5") String md5, Principal principal) throws IOException {
        FileData fileData = fileService.retrieveFileDataByMD5(md5, principal.getName());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentLength(fileData.getLength());
        responseHeaders.setContentDispositionFormData("attachment", fileData.getFileName());
        String type = new MimetypesFileTypeMap().getContentType(fileData.getType());
        responseHeaders.setContentType(MediaType.parseMediaType(type));
        return new ResponseEntity<>(fileData.getContent(), responseHeaders, HttpStatus.CREATED);

    }
}
