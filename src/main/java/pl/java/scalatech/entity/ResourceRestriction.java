package pl.java.scalatech.entity;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Slf4j
//TODO make value object
public class ResourceRestriction {

    private float quota;

    private float rateByDay;

    private float quotaByDay;

    private float rateByWeek;

    private float quotaByWeek;

    private float rateByMount;

    private float quotaByMount;

    private int countResource = 0;

    
    public ResourceRestriction usedNow(float documentSize,Optional<ResourceRestriction> rr){
        return rr.map(t -> calculateResourceRestriction(documentSize, t)).orElseGet(()-> firstResourceRestrictionBuilder(documentSize));
    }
    
    // @formatter:off
    private ResourceRestriction calculateResourceRestriction(float documentSize, ResourceRestriction t) {
        return ResourceRestriction.builder()               
                .quota(t.quota - documentSize)
                .rateByDay(getPercentage(t.quotaByDay-documentSize, t.quotaByDay))
                .quotaByDay(t.quotaByDay-documentSize)
                .rateByWeek(getPercentage(t.quotaByWeek-documentSize, t.quotaByWeek))
                .quotaByWeek(t.quotaByWeek- documentSize)
                .rateByMount(getPercentage(t.quotaByMount-documentSize, t.quotaByMount))
                .quotaByMount(t.quotaByMount-documentSize)        
                .countResource(t.countResource+1)
                .build();
    } 
    //  @formatter:on

   // @formatter:off
    private ResourceRestriction firstResourceRestrictionBuilder(float documentSize) {
        return ResourceRestriction.builder()               
      .quota(quota - documentSize)
      .rateByDay(getPercentage(quotaByDay-documentSize, quotaByDay))
      .quotaByDay(quotaByDay-documentSize)
      .rateByWeek(getPercentage(quotaByWeek-documentSize, quotaByWeek))
      .quotaByWeek(quotaByWeek- documentSize)
      .rateByMount(getPercentage(quotaByMount-documentSize, quotaByMount))
      .quotaByMount(quotaByMount-documentSize)        
      .countResource(1)
      .build();
    }
    // @formatter:on
    private static float getPercentage(float n, float total) {
        float proportion = n / total;
        return proportion * 100;
    }
}
