package com.indien.indien_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value="/main")
	public Map<String, String> home() {
		
		// �α��� ���� Ȯ���ؼ� ���� ���� ��������
		System.out.println("����");
		// ��ȭ ��� ��������
		Map<String, String> map = new HashMap<String, String>();
		map.put("res", "Ȩȭ������");
		
		return  map;
	}
	
	
}
