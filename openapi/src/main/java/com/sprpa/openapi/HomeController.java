package com.sprpa.openapi;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sprpa.openapi.vo.DataVO;
import com.sprpa.openapi.vo.dao.DataDAO;

@Controller
public class HomeController {
	@Autowired
	DataDAO dao;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@PostMapping("/insert")
	public String insert(HttpServletRequest request, String startYears, String startMonths, String endYears, String endMonths) throws IOException, ParseException, SAXException, ParserConfigurationException {
		Areacode ac = new Areacode();
		Map<String, String> areacode = ac.map; // <지역코드, 변환주소>
		int startYear = Integer.parseInt(startYears);
		int startMonth = Integer.parseInt(startMonths);
		int endYear = Integer.parseInt(endYears);
		int endMonth = Integer.parseInt(endMonths);
		System.out.println(areacode.size());
		for (String key : areacode.keySet()) {
			int year = startYear, month = startMonth;
			while (year != endYear || month != endMonth) {
				// 날짜(yyyymm) 세팅
				String date = null;
				if (month > 12) {
					year++;
					month = 1;
				}
				if (month < 10) {
					date = Integer.toString(year) + "0" + Integer.toString(month);
				} else {
					date = Integer.toString(year) + Integer.toString(month);
				}
				System.out.println(date);
				ApiExplorer apiData = new ApiExplorer(key, date); // openAPI로 받은 xml(지역코드, 거래월로 조회)
				String data = getBodyTag(apiData.xml); // 결과 xml의 <body> 부분
				if(data != null) {
					// XML Document 객체 생성
					InputSource is = new InputSource(new StringReader(data));
					Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
	
					// root tag
					doc.getDocumentElement().normalize();
					System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
	
					// 파싱할 tag
					NodeList nList = doc.getElementsByTagName("item");
					System.out.println("파싱할 리스트 수 : " + nList.getLength()); // 파싱할 리스트 수
					Connection conn = null;
					Statement stmt = null;
					for (int temp = 0; temp < nList.getLength(); temp++) {
						Node nNode = nList.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							String address = areacode.get(getTagValue("지역코드", eElement)) + " "
									+ getTagValue("법정동", eElement) + " " + getTagValue("법정동본번코드", eElement);
							if (!getTagValue("법정동부번코드", eElement).equals("0"))
								address += ("-" + getTagValue("법정동부번코드", eElement));
							String name = getTagValue("아파트", eElement);
							int floor = Integer.parseInt(getTagValue("층", eElement));
							String dd = null;
							if (Integer.parseInt(getTagValue("일", eElement)) < 10)
								dd = "0" + getTagValue("일", eElement);
							else
								dd = getTagValue("일", eElement);
							String dateStr = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + dd;
							double area = Double.parseDouble(getTagValue("전용면적", eElement));
							String price = getTagValue("거래금액", eElement).replace(",", "");
							Geocoder gc = new Geocoder(address);
							double epsgX = 0.0;
							double epsgY = 0.0;
							if(gc.epsgX != null && gc.epsgY != null) {
								epsgX = Double.parseDouble(gc.epsgX);
								epsgY = Double.parseDouble(gc.epsgY);
							}
	
							DataVO dataVO = new DataVO(address, name, dateStr, price, floor, area, epsgX, epsgY, areacode.get(getTagValue("지역코드", eElement)));
							
							dao.insertData(dataVO);
						}
					}
					month++;
				}
			}
		}
		System.out.println("################ 완료 ##################");
		return "home";
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return ltrim(nValue.getNodeValue().trim());
	}

	private static String ltrim(String source) {
		if (source == null)
			return null;
		for (int i = 0; i < source.length(); i++) {
			if (source.charAt(i) == '0') {
				continue;
			} else {
				return source.substring(i, source.length());
			}
		}
		return "0";
	}

	private static String getBodyTag(String apiData) {
		int startIdx = apiData.indexOf("<body>");
		int endIdx = apiData.indexOf("</body>");
		if(startIdx == -1) return null;
		else {
			String data = apiData.substring(startIdx, endIdx + 7);
			return data;
		}
	}
}
