package pl.java.scalatech.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@EqualsAndHashCode(callSuper=true)
public class User extends AbstractDocument{
    
    
    @Field("login")
    @Indexed(unique=true)
    private String login;
    
    @Field("email")
    private String email;
    
    
    
  
}
