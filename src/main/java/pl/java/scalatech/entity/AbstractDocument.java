package pl.java.scalatech.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class AbstractDocument {
	@Id
	private String id;
	
	//private LocalDateTime createDate= LocalDateTime.now();
}