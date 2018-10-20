package com.qugq.http;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequest {

    /**
     * 向指定URL发送请求 默认用户名"admin",密码"admin"
     * 
     * @param url
     *            发送请求的URL
     */
    public static String send(String url) {
	return send(url, "admin", "admin");
    }

    /**
     * 向指定URL发送携带参数的请求 默认用户名"admin",密码"admin"
     * 
     * @param url
     *            发送请求的URL
     * @param data
     *            JSON格式的参数
     */
    public static String send(String url, String param) {
	return send(url, param, "admin", "admin");
    }

    /**
     * 向指定URL发送请求
     * 
     * @param url
     *            发送请求的URL
     * @param UserName
     *            URL认证用户名
     * @param Password
     *            URL认证密码
     * @return
     */
    public static String send(String url, String UserName, String Password) {
	StringBuilder result = new StringBuilder();
	String userPassword = UserName + ":" + Password;
	String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
	BufferedReader in = null;
	try {
	    URL urlObject = new URL(url);
	    URLConnection uc = urlObject.openConnection();
	    uc.setRequestProperty("Authorization", "Basic " + encoding);

	    in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
	    String inputLine = null;
	    while ((inputLine = in.readLine()) != null) {
		result.append(inputLine);
	    }
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}
	    } catch (Exception e2) {
		e2.printStackTrace();
	    }
	}
	return result.toString();
    }

    /**
     * 向指定URL发送携带参数的请求
     * 
     * @param url
     *            发送请求的URL
     * @param data
     *            JSON格式的参数
     * @param UserName
     *            URL认证用户名
     * @param Password
     *            URL认证密码
     * @return
     */
    public static String send(String url, String param, String UserName, String Password) {
	StringBuilder result = new StringBuilder();
	String userPassword = UserName + ":" + Password;
	String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
	OutputStreamWriter writer = null;
	BufferedReader in = null;
	try {
	    URL urlObject = new URL(url);

	    URLConnection uc = urlObject.openConnection();
	    uc.setRequestProperty("Authorization", "Basic " + encoding);
	    uc.setRequestProperty("Content-Type", "application/json");

	    // 是否允许输入输出
	    uc.setDoInput(true);
	    uc.setDoOutput(true);

	    // 设备发现操作时设定超时时间5s
	    if (param.contains("\"devicediscovery:device-action\": \"insert\"")) {
		uc.setReadTimeout(10000);
	    }

	    writer = new OutputStreamWriter(uc.getOutputStream());
	    // 发送参数
	    writer.write(param);
	    // 清理当前编辑器的左右缓冲区，并使缓冲区数据写入基础流
	    writer.flush();

	    in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
	    String inputLine = null;
	    while ((inputLine = in.readLine()) != null) {
		result.append(inputLine);
	    }
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	} finally {
	    try {
		if (in != null) {
		    writer.close();
		    in.close();
		}
	    } catch (Exception e2) {
		e2.printStackTrace();
	    }
	}
	return result.toString();
    }
}