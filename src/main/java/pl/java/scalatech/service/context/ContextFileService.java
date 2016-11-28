package pl.java.scalatech.service.context;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Optional.ofNullable;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Resource;
import pl.java.scalatech.entity.ResourceRestriction;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.entity.UserResource;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.repository.UserResourceRepository;
import pl.java.scalatech.service.filestorage.pojo.FileData;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContextFileService {
    
    private static final String EMAIL_IS_NECESSARY_WHEN_WE_HAVE_TO_CREATE_NEW_USER = "email is necessary when we have to create new User";

    private static final int MB = 1024 * 1024;

    private final UserRepository userRepository;
    
    private final UserResourceRepository userResourceRepository;
      
    private final ResourceRestriction start;
    
    public User getUserOrCreateNew(String princical,Optional<String> email){
        return userRepository.findByLogin(princical)
                .orElseGet(()->userRepository.save(User.builder().login(princical).email(email.orElseThrow(()->new RuntimeException(EMAIL_IS_NECESSARY_WHEN_WE_HAVE_TO_CREATE_NEW_USER))).build()));
    }
    
    public void createResourceAndAddToUserProfile(FileData fd,User user){
        preconditionChecker(fd, user);     
        UserResource userResource = userResourceRepository.findByUserLogin(user.getLogin()).orElseGet(()->UserResource.builder().id(user.getLogin()).user(user).build());                               
        setNewResourceToUserResource(userResource, fd);   
        setRestrictionToUserResource(fd, userResource);
        userResourceRepository.save(userResource);       
    }

    private void preconditionChecker(FileData fd, User user) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(fd);
    }

    private void setRestrictionToUserResource(FileData fd, UserResource userResource) {
        ResourceRestriction rrloaded = getRestriction(fd, userResource);
        userResource.setResourceRestriction(rrloaded);
    }

    private void setNewResourceToUserResource(UserResource userResource, FileData fd) {
        Resource loadedResource = getResource(fd, userResource);
        userResource.getResources().add(loadedResource);
    }

    private ResourceRestriction getRestriction(FileData fd, UserResource userResource) {
        float documentSize = ((float)fd.getLength()) / MB;
        log.info("++++++ document size in MB : orginal : {} after in MB :  {}",fd.getLength(),documentSize);        
        return createNewQoutaUsingCondition(userResource, documentSize);
    }

    private ResourceRestriction createNewQoutaUsingCondition(UserResource userResource, float documentSize) {
        ResourceRestriction rrloaded;
        if(userResource.getResourceRestriction() == null){            
             rrloaded = start.usedNow(documentSize, ofNullable(null));
        }
        else{           
            rrloaded = userResource.getResourceRestriction().usedNow(documentSize, ofNullable(userResource.getResourceRestriction()));                     
        }
        return rrloaded;
    }

    private Resource getResource(FileData fd, UserResource userResource) {
        Resource resource = Resource.builder().resourceMd5(fd.getMd5()).resourceName(fd.getFileName()).size(fd.getLength()).type(fd.getType()).build();
        if(userResource.getResources() == null){
            userResource.setResources(newHashSet());
        }
        return resource;
    }
    
    
}
