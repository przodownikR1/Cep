package pl.java.scalatech.web.controller.resource;

import static com.google.common.collect.Maps.newHashMap;
import static java.net.URI.create;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static pl.java.scalatech.entity.Resource.CONTENT_TYPE;
import static pl.java.scalatech.entity.Resource.FILE_NAME;
import static pl.java.scalatech.entity.Resource.FILE_SIZE;
import static pl.java.scalatech.entity.Resource.UPLOAD_DATE;
import static pl.java.scalatech.entity.Resource.YYYY_MM_DD_HH_MM_SS;
import static pl.java.scalatech.service.filestorage.pojo.FileData.URL_RESOURCE_MAPPING;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.context.ContextFileService;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;

@RestController
@Slf4j
@RequestMapping(value="/api/resource"/*,consumes={V1_MEDIA_TYPE_VALUE},*/,produces = APPLICATION_JSON_VALUE)//headers={VersionApi.V1_MEDIA_TYPE_VALUE_HEADER}
public class FileUploadController {
    public static final String LOGIN = "login";
    private static final String DEFAULT_USER_LOGIN = "default";
    private static final String MY_FILE = "myFile";    
    private final FileService fileService;
    private final ContextFileService contextFileService;

    public FileUploadController(FileService fileService, ContextFileService cfs) {       
        this.fileService = fileService;
        this.contextFileService = cfs;
    }
  
    @PostMapping("/fileUpload")
    @ResponseBody
    HttpEntity<?> importParse(@RequestParam(MY_FILE) MultipartFile myFile, RedirectAttributes ra, UriComponentsBuilder uri, Principal principal)
            throws IOException {
        log.info("+++++++++++++++++++++++++++++++++++++++++   {}",principal);
        ra.addFlashAttribute("message", "Successfully upload..");
        Map<String, String> extraInfo = newHashMap();
        populateExtraInfoMap(myFile, principal, extraInfo);              
        FileData fileData = new FileData(myFile.getOriginalFilename(), myFile.getBytes(), myFile.getContentType(), retrieveLogin(principal), extraInfo);       
        FileData fd = fileService.put(fileData, retrieveLogin(principal));
        fd.setContent(myFile.getBytes());
        //TODO principal logic
        contextFileService.createResourceAndAddToUserProfile(fd, contextFileService.getUserOrCreateNew(retrieveLogin(principal), ofNullable("new")));
        log.info("fd -> {}", fd);        
        return created(create(uri.path(URL_RESOURCE_MAPPING).buildAndExpand(fd.getMd5()).toUriString())).eTag(fd.getMd5()).build();
        
    }


    private void populateExtraInfoMap(MultipartFile myFile, Principal principal, Map<String, String> extraInfo) {
        extraInfo.put(LOGIN, retrieveLogin(principal));
        extraInfo.put(FILE_NAME, myFile.getOriginalFilename());
        extraInfo.put(FILE_SIZE, Long.toString(myFile.getSize()));
        extraInfo.put(CONTENT_TYPE, myFile.getContentType());
        extraInfo.put(UPLOAD_DATE, now().format(ofPattern(YYYY_MM_DD_HH_MM_SS)));
        log.debug("extraInfo  {}", extraInfo);
        log.debug("fileName  {}", myFile.getOriginalFilename());
        log.debug("fileSize  {}", myFile.getSize());
        log.debug("contentType  {}", myFile.getContentType());
    }

    private String retrieveLogin(Principal principal) {
        String login = DEFAULT_USER_LOGIN;
        if(principal != null){
           login = principal.getName(); 
        }
        return login;
    }

}
