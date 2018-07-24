package com.yyp.demo;

import com.yyp.utils.JsonUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class Test {
	public static void main(String[] args) {
		Person person = new Person("110", "yyp", "123");
		/*String p = JsonUtil.object2json(person);
		System.out.println(p);*/
		/**
		 * 去除不想要的Json 字段
		 */
		JsonConfig config = JsonUtil.configJson(new String[]{"id","class"});
		JSONObject json = JSONObject.fromObject(person, config);
		System.out.println(json);
	}
}
