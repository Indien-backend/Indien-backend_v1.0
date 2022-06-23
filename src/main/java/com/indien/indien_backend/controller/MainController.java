package com.indien.indien_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value="/main")
	public Map<String, String> home() {
		
		// 로그인 여부 확인해서 유저 정보 가져오기
		System.out.println("진입");
		// 영화 목록 가져오기
		Map<String, String> map = new HashMap<String, String>();
		map.put("res", "홈화면진입");
		
		return  map;
	}
	
	
}
