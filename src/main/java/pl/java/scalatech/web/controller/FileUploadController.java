package pl.java.scalatech.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String provideUploadInfo() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public  String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) { return "You failed to upload " + name + " because the file was empty"; }
        try {
            
           //TODO save to store
            return "Successfully uploaded " + name;
        } catch (Exception ex) {
            return "You failed to upload " + name + " => " + ex.getMessage();
        }
    }
    
    
}
