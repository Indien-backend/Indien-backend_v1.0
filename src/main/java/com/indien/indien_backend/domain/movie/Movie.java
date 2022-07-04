package com.indien.indien_backend.domain.movie;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.indien.indien_backend.utils.serializer.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="MOVIE")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String code;
	
	private String title;
	
	private String prdYear; 
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime openDate;
	
	private String nation;
	
	private Set<String> posters;
	
	private Set<String> steelCut;
	
	private String synopsis;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime created;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updated;
	
	private BigDecimal runningTm;
	
	public enum Audit{
		
		// 연령제한 등급
		
	}
	
}
