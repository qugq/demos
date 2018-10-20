package com.qugq.main;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.qugq.file.FileUtil;
import com.qugq.http.HttpRequest;

/**
 * 
 * @author qugq
 *
 */
public class SHTool {
    private String url = "http://<IP>:8181/apidoc/apis";
    private String userName = "admin";
    private String password = "admin";

    public static void main(String[] args) {
	Scanner scan = new Scanner(System.in);
	SHTool tool = new SHTool();
	String IP = "";
	String APIString = "";
	String APIDataString = "";
	String path = "";
	String pathData = "";
	String regex = ".*config$";
	// 从键盘接收数据
	System.out.println("请输入IP地址：");// next方式接收字符串
	// 判断是否还有输入
	if (scan.hasNext()) {
	    IP = scan.next();
	    System.out.println("IP地址为：" + IP);
	}
	scan.close();
	APIString = tool.getApis(IP);
	JSONObject APIJson = new JSONObject(APIString);
	JSONArray APIArray = APIJson.getJSONArray("apis");
	for (int i = 0; i < APIArray.length(); i++) {
	    path = APIArray.getJSONObject(i).getString("path");
	    APIDataString = tool.getDate(path, IP);
	    JSONObject APIDataJson = new JSONObject(APIDataString);
	    String basePath = APIDataJson.getString("basePath");
	    JSONArray APIDataArray = APIDataJson.getJSONArray("apis");
	    for (int j = 0; j < APIDataArray.length(); j++) {
		pathData = APIDataArray.getJSONObject(j).getString("path");
		if (!((pathData.contains("{") && pathData.contains("}")) || pathData.matches(regex))) {
		    FileUtil.writeFile("echo " + basePath + pathData + "\n");
		    FileUtil.writeFile("curl -u admin:admin -o " + pathData.substring(1).replace("/", "_") + ".json"
			    + basePath + pathData + "\n");
		}
	    }
	}
	System.out.println("done");
    }

    private String getApis(String IP) {
	String apis = "";
	apis = HttpRequest.send(url.replace("<IP>", IP), userName, password);
	return apis;
    }

    private String getDate(String url, String IP) {
	String apis = "";
	apis = HttpRequest.send(url, userName, password);
	return apis;
    }
}
