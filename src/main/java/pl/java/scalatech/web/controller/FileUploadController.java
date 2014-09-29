package pl.java.scalatech.web.controller;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

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

import pl.java.scalatech.assembler.FileDataAssembler;
import pl.java.scalatech.assembler.FileDataResource;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;

import com.google.common.collect.Maps;

@Controller
@Slf4j
public class FileUploadController {

    private final static String FD = "fd";
    private final FileDataAssembler fileDataAssembler;
    private final FileService fileService;

    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileDataAssembler = new FileDataAssembler(this.getClass(), FileDataResource.class);
        this.fileService = fileService;
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    String redirect() {
        return "fileUpload";
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    HttpEntity<?> importParse(@RequestParam("myFile") MultipartFile myFile, RedirectAttributes ra, UriComponentsBuilder uri, Optional<Principal> principal)
            throws IOException {
        ra.addFlashAttribute("message", "Successfully upload..");
        Map<String, String> extraInfo = Maps.newHashMap();
        extraInfo.put("fileName", myFile.getOriginalFilename());
        extraInfo.put("fileSize", Long.toString(myFile.getSize()));
        extraInfo.put("contentType", myFile.getContentType());
        log.info("+++ extraInfo  {}", extraInfo);
        log.info("+++ fileName  {}", myFile.getOriginalFilename());
        log.info("+++ fileSize  {}", myFile.getSize());
        log.info("+++ contentType  {}", myFile.getContentType());
        FileData fd = null;
        //TODO
        fd = fileService.put(new FileData(myFile.getOriginalFilename(), myFile.getBytes(), myFile.getContentType(), "slawek", extraInfo), "slawek");
        fd.setContent(myFile.getBytes());

        log.info("fd -> {}", fd);
        HttpHeaders httpHeaders = new HttpHeaders();
        String url = uri.path("/api/resource/{md5}").buildAndExpand(fd.getMd5()).toUriString();
  
        String newResourceLink = fileDataAssembler.toResource(fd).getLink("resource").getHref();
        httpHeaders.setLocation(URI.create(newResourceLink));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);

    }

}
