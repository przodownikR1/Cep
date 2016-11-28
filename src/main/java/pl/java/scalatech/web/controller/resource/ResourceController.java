package pl.java.scalatech.web.controller.resource;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Random;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import pl.java.scalatech.repository.UserResourceRepository;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;
import pl.java.scalatech.web.controller.generic.CepController;

@RestController
@RequestMapping(value = "/api/resource")
public class ResourceController extends CepController {

    @Autowired
    public ResourceController(UserResourceRepository userResourceRepository, FileService fileService) {
        super(userResourceRepository);
        this.fileService = fileService;
    }

    private static final String IMAGE_PNG = "image/png";
    private static final String IMAGE_JPEG = "image/jpeg";
    private static final String IMAGE_GIF = "image/gif";
    private static final String ATTACHMENT = "attachment";
    private static final String CONTENT_TYPE = "contentType";
    private static final String MD5 = "md5";
    private final @NonNull FileService fileService;
    Random r = new Random();

    // @formatter:off       
    @RequestMapping(value = "/md5/{md5}",method=GET)
    public ResponseEntity<byte[]> getResourceByMD5(@PathVariable(MD5) String md5) {
        FileData fileData = retrieveDataFromStore(md5);
        HttpHeaders responseHeaders = setHeaders(fileData);
        handleRetrieveDependOnMediaType(fileData,responseHeaders, fileData.getExtra().get(CONTENT_TYPE));
        return new ResponseEntity<>(fileData.getContent(), responseHeaders, OK);
    }
    //  @formatter:on

    private void handleRetrieveDependOnMediaType(FileData fileData, HttpHeaders responseHeaders, String fdContentType) {
        if (isSupportedFormatForImage(fdContentType)) {
            handleImageMediaType(responseHeaders, fdContentType);
        } else {
            handleOtherMediaType(fileData, responseHeaders);
        }
    }

    private FileData retrieveDataFromStore(String md5) {
        FileData fileData = fileService.retrieveFileDataByMD5(md5);                
        return fileData;
    }

    private HttpHeaders setHeaders(FileData fileData) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setCacheControl(org.springframework.http.CacheControl.noCache().getHeaderValue());
        responseHeaders.setContentLength(fileData.getLength());
        return responseHeaders;
    }

    private void handleImageMediaType(HttpHeaders responseHeaders, String fdContentType) {
        MediaType media = parseMediaType(fdContentType);
        responseHeaders.setContentType(media);
    }

    private void handleOtherMediaType(FileData fileData, HttpHeaders responseHeaders) {
        responseHeaders.setContentDispositionFormData(ATTACHMENT, fileData.getFileName());
        String type = new MimetypesFileTypeMap().getContentType(fileData.getType());
        responseHeaders.setContentType(parseMediaType(type));
    }

    private boolean isSupportedFormatForImage(String contentType) {
        return contentType.equals(IMAGE_GIF) || contentType.equals(IMAGE_JPEG) || contentType.equals(IMAGE_PNG);
    }

    @GetMapping("/{login}")
    ResponseEntity<Set<ResourceContainer>> getResourceByUser(@PathVariable String login) {
        return ok(findUserByLogin(login).getResources().stream().map(resource -> new ResourceContainer(resource)).collect(toSet()));
    }

    @RequestMapping(value="/info/{md5}",method=GET)
    public ResponseEntity<ResourceContainer> getResourceInfoByMd5(@PathVariable String md5) {
        return ok(new ResourceContainer(fileService.retrieveFileDataByMD5(md5)));
    }
    
    @GetMapping("/count/{login}")
    ResponseEntity<Integer> getCountResourceByUser(@PathVariable String login) {
        int count = findUserByLogin(login).getResources().size();
        return ok(count);
    }
}
