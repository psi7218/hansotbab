package com.b209.hansotbab.food;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonDataUtils {
    static String[] processed = {"베이컨류", "조미김", "만두피", "어묵"};
    static List<String> processedList = Arrays.asList(processed);
    static String[] sidedish = {"구이류", "국 및 탕류", "볶음류", "튀김류", "찌개 및 전골류", "찜류", "묵류",
            "생채및 무침류", "장아찌 및 절임류", "전.적 및 부침류", "젓갈", "양념젓갈",
            "김치", "조림류", "젓갈류", "죽 및 스프류", "김치류", "숙채류", "회류", "밥류",
            "영/유아용 이유식", "영아용 조제식", "성장기용 조제식", "두부", "가공두부"};
    static List<String> sidedishList = Arrays.asList(sidedish);
    static String[] dairy = {"농후발효유", "가공유", "강화우유", "가공치즈", "가공버터", "자연치즈", "우유",
            "발효유", "마가린", "성장기용 조제유", "영아용 조제유", "유당분해우유", "전지분유", "탈지분유",
            "버터", "가공연유", "가공유크림", "크림발효유", "농후크림발효유", "혼합분유", "산양유", "탈지농축우유",
            "가공두유", "원액두유", "식물성크림"};
    static List<String> dairyList = Arrays.asList(dairy);
    static String[] sauce = {"참기름", "콩기름(대두유)", "기타식물성유지", "소스", "마요네즈", "잼", "카레(커리)",
            "들기름", "향미유", "물엿", "설탕", "기타설탕", "해바라기유", "올리브유", "토마토케첩",
            "올리고당", "채종유(유채유 또는 카놀라유)", "밀가루", "야자유", "벌꿀", "미강유(현미유)",
            "고추씨기름", "옥수수기름(옥배유)", "혼합간장", "양조간장", "고추장", "된장", "발효식초",
            "혼합식용유", "가공유지", "식용돈지", "혼합장", "기타장류", "춘장", "산분해간장", "쇼트닝",
            "개량메주", "기타잼", "고춧가루"};
    static List<String> sauceList = Arrays.asList(sauce);
    static String[] snack = {"곡류 및 서류", "빵류", "과자류", "아이스크림류", "과자", "캔디류", "빙과", "추잉껌", "밀크초콜릿",
            "초콜릿", "떡류", "준초콜릿", "아이스크림", "아이스밀크", "샤베트", "시리얼류",
            "비유지방아이스크림", "화이트초콜릿", "저지방아이스크림"};
    static List<String> snackList = Arrays.asList(snack);

    public void getJsonData() {
        String fileDir = "src/main/resources/files";
        String foodFile = fileDir + "/food_info.json";

        try {
            String foodBody = readFileAsString(foodFile).replace("\uFEFF", "");
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(foodBody);
            FileWriter file = new FileWriter(fileDir + "/food_result.json");
            JSONObject writeObj = new JSONObject();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tempObj = (JSONObject) jsonArray.get(i);
                JSONObject index = new JSONObject();
                //ES의 index명
                index.put("_index", "auto_test");
                index.put("_id", tempObj.get("_id"));
                writeObj.put("index", index);
                file.write(writeObj.toJSONString());
                file.write('\n');

                JSONObject info = new JSONObject();
                info.put("code", tempObj.get("code"));
                info.put("name", tempObj.get("name"));
                String cat1 = (String) tempObj.get("cat1");
                String cat2 = (String) tempObj.get("cat2");
                String cat = foodCategory(cat1, cat2);
                info.put("cat", cat);
                info.put("cat1", cat1);
                info.put("cat2", cat2);
                file.write(info.toJSONString());
                file.write('\n');
            }
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public String foodCategory(String cat1, String cat2) {
        if (cat1.contains("품") || cat1.contains("면") || cat1.contains("햄") || cat1.contains("소시지") || processedList.contains(cat1))
            return "가공식품"; // 가공식품

        if (cat1.contains("음료") || cat1.equals("과/채주스") || cat1.equals("커피") || cat1.equals("탄산수") || cat1.equals("액상차") || cat1.equals("과실주"))
            return "음료"; // 음료

        if (sidedishList.contains(cat1))
            return "밥/반찬류"; // 밥/반찬류

        if (dairyList.contains(cat1))
            return "유제품"; // 유제품

        if (sauceList.contains(cat1))
            return "소스/조미료"; // 소스/조미료

        if (snackList.contains(cat1))
            return "간식류"; // 간식류

        if ((cat1.equals("농축산물") && (cat2.equals("육류") || cat2.equals("난류"))) || cat1.equals("양념육"))
            return "축산/계란"; // 축산/계란

        if (cat1.equals("농축산물") && (cat2.equals("채소류") || cat2.equals("버섯류")))
            return "채소"; // 채소

        if ((cat1.equals("농축산물") && cat2.equals("과실류")) || cat1.equals("농축과/채즙(또는 과/채분)"))
            return "과일"; // 과일

        //else
        return "기타"; // 기타
    }
}
