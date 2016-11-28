package pl.java.scalatech.web.controller.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

  private String id;
  private String name;
  private int age;
  public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }
}