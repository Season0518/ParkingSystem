package com.xl.parking.utils;

import cn.dev33.satoken.context.SaHolder;
import com.alibaba.fastjson.JSON;
import com.xl.parking.domain.vo.IpAddress;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @author XL
 * 自定义工具类
 * @date 2022/1/12 10:13
 */
public class MyUtils {

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if(ipAddress!=null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 根据Ip获取地理位置
     */
    public static String getLocationByIp(String ip) {
        String ipUrl = "http://whois.pconline.com.cn/ipJson.jsp";
        // 异常地理位置
        String address = "XX XX";
        String result = "";
        try{

            URL url = new URL(ipUrl+"?ip="+ip+"&json=true");
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"GBK"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            httpConn.disconnect();
            result = buffer.toString();
            IpAddress ipAddress = JSON.parseObject(result, IpAddress.class);
            address = ipAddress.getAddr();
        }catch (Exception e){
            e.printStackTrace();
            return address;
        }
        return address;
    }

    /**
     *  获取request请求
     */
    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) SaHolder.getRequest().getSource();
    }


}
