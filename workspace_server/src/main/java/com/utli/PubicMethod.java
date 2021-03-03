package com.utli;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PubicMethod {
	public static Map<String,Object> countPage(int index,int limit,Long count){
		//不满足一页数
		Long page=0L;
		if(count!=0)
		{
			page = count/limit;
			if(count%limit!=0) {
				page++;
			}
		}else{
			page = 0L;
		}

		Map<String,Object> result = new HashMap<>();
		result.put("count",count);
		result.put("page",page);
		result.put("index",index);
		result.put("limit",limit);
		return result;
	}
	public static String getAcademeCode() {
		Random random = new Random();
		String AcademeCode = "";
		StringBuffer sb = new StringBuffer();
		long time = System.currentTimeMillis();
		long randonvalue = 0;
		String str = "";
		for (int i = 0; i < 5; i++) {
			int casevalue = PubicMethod.getCase(random.nextInt(3) % 4);
			switch (casevalue) {
			case 1:
				/* A-Z 的 ASCII 码值[65,90] */
				str = (char) (Math.random() * 26 + 'A') + "";
				sb.append(str);
				break;
			case 2:
				/* a-z 的 ASCII 码值[97,122] */
				str = (char) (Math.random() * 26 + 'a') + "";
				sb.append(str);
				break;
			default:
				break;
			}
		}
		AcademeCode = String.valueOf(time) + sb;
		return AcademeCode;
	}

	public static int getCase(int casevalue) {
		Random random = new Random();
		return casevalue == 0 ? getCase(random.nextInt(3) % 4) : casevalue;
	}
}
