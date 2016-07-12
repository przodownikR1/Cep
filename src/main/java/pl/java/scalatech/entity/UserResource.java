package pl.java.scalatech.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  11 wrz 2014 11:37:16
 
 */
@Document(collection = "userResources")
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResource extends AbstractDocument{
    
    @DBRef
    private User user;
    
    private String resourceMd5;
    
    private String resourceName;
    
    private int resourceCount;
    
    private long capacityOwner;
    
    private String login;
}
