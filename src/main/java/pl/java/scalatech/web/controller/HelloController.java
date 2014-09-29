package pl.java.scalatech.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Slf4j
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public class HelloController {
    Random rand = new Random();

    List<Car> cars = Lists.newArrayList(new Car("bmw","10"),new Car("star","12"),new Car("opel","23"));
    
    @RequestMapping("/hello")
    String hello() {
        return "hello ...";
    }

    @RequestMapping("/car/name/{name}")
    Car getCarByName(@PathVariable("name")String name) {
        Optional<Car> opt = cars.stream().filter(car -> name.equals(car.name)).findFirst();
        if(opt.isPresent()){
            return opt.get();
        }
        throw new IllegalArgumentException("car with name "+ name+ " not exists");
    }
    
    @RequestMapping("/car")
    @ApiOperation(httpMethod = "GET", value = "Car observe", response = Car.class)
    Car car() {
        return new Car("bmw", "10");
    }

    @Data
    @AllArgsConstructor
    @ApiModel("Car Entry")
    class Car {
        @ApiModelProperty(value = "name", required = true)
        private String name;
        @ApiModelProperty(value = "age", required = true)
        private String age;
    }
}
