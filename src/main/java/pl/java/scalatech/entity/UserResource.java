package pl.java.scalatech.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  11 wrz 2014 11:37:16
 
 */
@Document(collection = "userResources")
@Data
@EqualsAndHashCode(callSuper=true)
public class UserResource extends AbstractDocument{
    
    @DBRef
    private User user;
    
    private String resourceMd5;
    
    private String resourceName;
    
    private int resourceCount;
    
    private long capacityOwner;
}
