package pl.java.scalatech.web.controller;

import static pl.java.scalatech.service.filestorage.pojo.FileData.URL_RESOURCE_MAPPING;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;

@RestController
@Profile("test")
@RequestMapping(value = "/api/resource/fs")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FsController {
    private final @NonNull GridFsTemplate gridFsTemplate;
    private final @NonNull FileService fs;     
    
    
    
    @GetMapping("/login/")
    List<FileData> geDataByLogin(){
        return fs.retrieveFileDateByLogin("default");
    }
    
    @GetMapping("/file/{name}")
    GridFsResource getGridFsResource(@PathVariable String name){
        return gridFsTemplate.getResource(name);        
    }
    @GetMapping("/{login}/count")
    ResponseEntity<List<FileData>> getCountResoureByLogin(@PathVariable String login,UriComponentsBuilder uri){
            
        List<FileData> temp =  fs.findAll();        
        temp =  temp.stream().map(t -> {
            t.setResourceUrl(uri.path(URL_RESOURCE_MAPPING).buildAndExpand(t.getMd5()).toUriString());
            return t;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(temp);
    }
    

}
