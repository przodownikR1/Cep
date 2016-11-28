package pl.java.scalatech.entity;

import java.time.LocalDateTime;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;


@AllArgsConstructor
@Builder
@Value
@ToString
@JsonIgnoreProperties("FILE_NAME,FILE_SIZE,CONTENT_TYPE,UPLOAD_DATE,YYY_MM_DD_HH_MM_SS")
public class Resource {
            
    public static final String FILE_NAME = "fileName";
    public static final String FILE_SIZE = "fileSize";
    public static final String CONTENT_TYPE = "contentType";
    public static final String UPLOAD_DATE = "uploadDate";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private String resourceMd5;
    
    private String resourceName;
    
    private long size;
    
    private String type;
    
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime uploadDateTime;
    
}
