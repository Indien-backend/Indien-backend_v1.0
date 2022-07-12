package com.indien.indien_backend.domain.movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.indien.indien_backend.utils.serializer.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="PERSON")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Long id;
	
	private String name;
	
	@Column(nullable = false, unique = true)
	private String code;
	
	private String img;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime created;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updated;
	
	@OneToMany(mappedBy="PERSON")
	private List<MoviePerson> movies = new ArrayList<>();
	
	public enum Part{
		// 파트
	}
	
}
