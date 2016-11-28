package pl.java.scalatech.entity;

import java.io.Serializable;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.google.common.collect.Sets.newHashSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  11 wrz 2014 11:37:16
 
 */
@Document(collection = "userResources")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"resourceRestriction","resources"})
public class UserResource implements Serializable{
 
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    
    private User user;

    private Set<Resource> resources = newHashSet();

    private ResourceRestriction resourceRestriction;
    
}
