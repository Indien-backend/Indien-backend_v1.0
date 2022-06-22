package com.indien.indien_backend.api.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
	
	@PostMapping(value="/login")
	public Map<String, Object> login(@RequestBody Map<String, Object> data) {
		
		// 회원인증 로직 필요함
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("res", "로그인api");
		
		return  map;
	}
	
	@PostMapping(value="/login/oauth")
	public Map<String, Object> kakaoLogin(@RequestBody Map<String, Object> data) {
		
		// 카카오 서버에 인가코드 날려서 토큰 받는 로직 필요함
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("res", "카카오로그인api");
		
		return  map;
	}

}
