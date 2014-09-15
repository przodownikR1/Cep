package pl.java.scalatech.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  11 wrz 2014 11:37:16
 
 */
@Document(collection = "userResources")
@Data
public class UserResource {
    @Id
    private String id;
    @DBRef
    private User user;
    private String resourceMd5;
    private String resourceName;
}
