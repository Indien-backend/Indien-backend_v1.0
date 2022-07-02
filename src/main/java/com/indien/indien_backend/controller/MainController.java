package com.indien.indien_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	@GetMapping(value="/main")
	public Map<String, String> home() {

		System.out.println("진입");
		// 정보 담아서 리턴
		Map<String, String> map = new HashMap<String, String>();
		map.put("res", "영화 정보 응답 완료");
		
		return  map;
	}
	
	
}
