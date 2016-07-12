package pl.java.scalatech.entity;

import lombok.Data;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
@Data
public class AbstractDocument {
	@Id
	private String id;
	
	//private LocalDateTime createDate= LocalDateTime.now();
}