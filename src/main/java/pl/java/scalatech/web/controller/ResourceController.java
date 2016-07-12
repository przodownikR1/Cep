package pl.java.scalatech.web.controller;

import static org.springframework.http.MediaType.parseMediaType;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.Random;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;
import pl.java.scalatech.web.controller.version.VersionApi;

@RestController
@RequestMapping(value="/api/resource",consumes={VersionApi.V1_MEDIA_TYPE_VALUE})//headers={VersionApi.V1_MEDIA_TYPE_VALUE_HEADER}
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResourceController {
    private static final String ATTACHMENT = "attachment";
    private static final String CONTENT_TYPE = "contentType";
    private static final String MD5 = "md5";
    private final @NonNull FileService fileService;
    Random r = new Random();

    @RequestMapping(value = "/{md5}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getResource(@PathVariable(MD5) String md5, Optional<Principal> principal) throws IOException {
        FileData fileData = null;
        if (principal.isPresent()) {
            fileData = fileService.retrieveFileDataByMD5(md5, principal.get().getName());
        } else {
            Principal p = () -> "" + r.nextInt(100);
            fileData = fileService.retrieveFileDataByMD5(md5, p.getName());

        }
        log.info("++++++++++++++++   {}", fileData);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentLength(fileData.getLength());

        String fdContentType = fileData.getExtra().get(CONTENT_TYPE);
        if (isSupportedFormatForImage(fdContentType)) {
            responseHeaders.setContentType(parseMediaType(fdContentType));
        } else {
            responseHeaders.setContentDispositionFormData(ATTACHMENT, fileData.getFileName());
            String type = new MimetypesFileTypeMap().getContentType(fileData.getType());
            responseHeaders.setContentType(parseMediaType(type));
        }
        return new ResponseEntity(fileData.getContent(), responseHeaders, HttpStatus.OK);

    }

    private boolean isSupportedFormatForImage(String contentType) {
        return contentType.equals("image/gif") || contentType.equals("image/jpeg") || contentType.equals("image/png");
    }

}
