package pl.java.scalatech.web.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Slf4j
@Api(value = "hello api", description = "sample test")
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public class HelloController {
    Random rand = new Random();
    
    @RequestMapping("/hello")
    @ApiOperation(httpMethod = "GET", value = "Say Hello")
    public String hello() {
        return "hello ...";
    }
    
    @RequestMapping("/car")
    @ApiOperation(httpMethod = "GET", value = "Car observe",response=Car.class)
    public Car car() {
        return new Car("bmw","10");
    }
    
    @RequestMapping("/carMetrics")
    @Timed
    @ApiOperation(httpMethod = "GET", value = "Car metrics",response=Car.class)
    public Car carMetrics() throws InterruptedException {
      
        int r = rand.nextInt(1000);
        Thread.sleep(TimeUnit.MILLISECONDS.toMillis(new Long(r)));
        return new Car("bmw","10");
    }
    
    @RequestMapping("/carException")
    @Timed
    @ExceptionMetered
    @ApiOperation(httpMethod = "GET", value = "Car metrics exception test",response=Car.class)
    public Car carMetricsException() throws InterruptedException {
           throw new IllegalStateException("attempt error situation invoke...");
    }

    @Data
    @AllArgsConstructor
    @ApiModel("Car Entry")
    public class Car {
        @ApiModelProperty(value = "name", required = true)
        private String name;
        @ApiModelProperty(value = "age", required = true)
        private String age;
    }
}
