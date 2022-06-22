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
		
		// ȸ������ ���� �ʿ���
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("res", "�α���api");
		
		return  map;
	}
	
	@PostMapping(value="/login/oauth")
	public Map<String, Object> kakaoLogin(@RequestBody Map<String, Object> data) {
		
		// īī�� ������ �ΰ��ڵ� ������ ��ū �޴� ���� �ʿ���
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("res", "īī���α���api");
		
		return  map;
	}

}
