package com.sprpa.openapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

public class Areacode {
	Map<String, String> map = new HashMap<>(); // <지역코드, 변환주소>

	Areacode() {
		ClassPathResource resource = new ClassPathResource("areacode.txt");
		
		try {
		    Path path = Paths.get(resource.getURI());
		    List<String> content = Files.readAllLines(path);
		    String prev = null;
		    for(String ct : content) {
		    	String areacode = ct.substring(0, 5);
		    	if (prev != null && areacode.equals(prev))
					continue; // 지역코드 중복 제거
				String[] sLineSplit = ct.split("\t");
				if (sLineSplit[2].equals("존재")) // 현재 존재하는 지역만 뽑아냄
					map.put(areacode, sLineSplit[1]);
				prev = areacode;
		    }
		} catch (IOException e) {
		    
		}
	}
}
