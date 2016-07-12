package pl.java.scalatech.web.controller;

import static pl.java.scalatech.service.filestorage.pojo.FileData.URL_RESOURCE_MAPPING;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.assembler.FileDataAssembler;
import pl.java.scalatech.assembler.FileDataResource;
import pl.java.scalatech.entity.UserResource;
import pl.java.scalatech.repository.UserResourceRepository;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;

@Controller
@Slf4j
public class FileUploadController {
    private static final String DEFAULT_USER_LOGIN = "default";
    private static final String RESOURCE = "resource";
   
    private static final String MY_FILE = "myFile";    
    private final FileDataAssembler fileDataAssembler;
    private final FileService fileService;
    private final UserResourceRepository userResourceRepository;

    @Autowired
    public FileUploadController(FileService fileService, UserResourceRepository userResourceRepository) {
        this.fileDataAssembler = new FileDataAssembler(this.getClass(), FileDataResource.class);
        this.fileService = fileService;
        this.userResourceRepository = userResourceRepository;
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    String redirect() {
        return "fileUpload";
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    HttpEntity<?> importParse(@RequestParam(MY_FILE) MultipartFile myFile, RedirectAttributes ra, UriComponentsBuilder uri, Optional<Principal> principal)
            throws IOException {
        ra.addFlashAttribute("message", "Successfully upload..");
        Map<String, String> extraInfo = Maps.newHashMap();
        extraInfo.put("fileName", myFile.getOriginalFilename());
        extraInfo.put("fileSize", Long.toString(myFile.getSize()));
        extraInfo.put("contentType", myFile.getContentType());
        log.info("extraInfo  {}", extraInfo);
        log.info("fileName  {}", myFile.getOriginalFilename());
        log.info("fileSize  {}", myFile.getSize());
        log.info("contentType  {}", myFile.getContentType());              
        FileData fileData = new FileData(myFile.getOriginalFilename(), myFile.getBytes(), myFile.getContentType(), retrieveLogin(principal), extraInfo);
        log.info("+++  {}",fileData);
        userResourceRepository.save(UserResource.builder().login(retrieveLogin(principal)).build());
        FileData fd = fileService.put(fileData, retrieveLogin(principal));
        fd.setContent(myFile.getBytes());

        log.info("fd -> {}", fd);
        HttpHeaders httpHeaders = new HttpHeaders();
        String url = uri.path(URL_RESOURCE_MAPPING).buildAndExpand(fd.getMd5()).toUriString();
        log.info("URL :   {} ", url);        
        String newResourceLink = fileDataAssembler.toResource(fd).getLink(RESOURCE).getHref();
        httpHeaders.setLocation(URI.create(newResourceLink));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    private String retrieveLogin(Optional<Principal> principal) {
        String login = DEFAULT_USER_LOGIN;
        if(principal.isPresent()){
           login = principal.get().getName(); 
        }
        return login;
    }

}
