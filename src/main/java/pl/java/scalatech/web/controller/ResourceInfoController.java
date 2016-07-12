package pl.java.scalatech.web.controller;

import static pl.java.scalatech.service.filestorage.pojo.FileData.URL_RESOURCE_MAPPING;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.repository.UserResourceRepository;
import pl.java.scalatech.service.filestorage.FileService;
import pl.java.scalatech.service.filestorage.pojo.FileData;
import pl.java.scalatech.web.controller.version.VersionApi;

@RestController
@RequestMapping(value="/api/resource",consumes={VersionApi.V1_MEDIA_TYPE_VALUE})//,headers={VersionApi.V1_MEDIA_TYPE_VALUE_HEADER}
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ResourceInfoController {
                
    private final UserResourceRepository userRepositoryRepository;
   
    private final FileService fs;
    
    @RequestMapping("user/content")
    List<String> getUserContent(){      
        return Lists.newArrayList("test");        
    }

    @RequestMapping("user/{login}")
    List<FileData> getUserResourceByLogin(String login,UriComponentsBuilder uri){        
        List<FileData> temp =  fs.retrieveFileDateByLogin(login);        
        return temp.stream().map(t -> {
            t.setResourceUrl(uri.path(URL_RESOURCE_MAPPING).buildAndExpand(t.getMd5()).toUriString());
            return t;
        }).collect(Collectors.toList());
    }
    
}
