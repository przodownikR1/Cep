package pl.java.scalatech.web.controller;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(httpMethod = "GET", value = "Car observe", response = Car.class)
    public Car car() {
        return new Car("bmw", "10");
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
