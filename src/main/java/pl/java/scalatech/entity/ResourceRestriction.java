package pl.java.scalatech.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@EqualsAndHashCode(callSuper=true)
public class ResourceRestriction extends AbstractDocument{
    
    private long quota;
    
    private int rateByDay;
    
    private long quotaByDay;
    
    private int rateByWeek;
    
    private long quotaByWeek;
    
    private int rateByMount;
    
    private long quotaByMount;
    
    
}
