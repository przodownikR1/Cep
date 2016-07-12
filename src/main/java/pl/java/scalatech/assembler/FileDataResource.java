package pl.java.scalatech.assembler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.hateoas.ResourceSupport;
@NoArgsConstructor
@AllArgsConstructor
public class FileDataResource extends  ResourceSupport{
    @Getter @Setter
    private String fileName ;
    @Getter @Setter
    private long length;
    @Getter @Setter
    private String md5;
    @Getter @Setter
    private String type;
        
    
}
