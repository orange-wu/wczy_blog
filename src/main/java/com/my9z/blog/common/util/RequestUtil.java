package com.my9z.blog.util;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * @description: request解析工具类
 * @author: wczy9
 * @createTime: 2023-01-19  21:39
 */
public class RequestUtil {

    private static final String UN_KNOW = "unknown";

    /**
     * 获取请求过来的ip地址
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        //X-Forwarded-For：Squid 服务代理
        String ipAddress = request.getHeader("x-forwarded-for");
        //Proxy-Client-IP：apache 服务代理
        if (StrUtil.isEmpty(ipAddress) || StrUtil.equals(UN_KNOW, ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        //WL-Proxy-Client-IP：webLogic 服务代理
        if (StrUtil.isEmpty(ipAddress) || StrUtil.equals(UN_KNOW, ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        //HTTP_CLIENT_IP：代理服务器
        if (StrUtil.isEmpty(ipAddress) || StrUtil.equals(UN_KNOW, ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        //X-Real-IP：nginx服务代理
        if (StrUtil.isEmpty(ipAddress) || StrUtil.equals(UN_KNOW, ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (StrUtil.isEmpty(ipAddress) || StrUtil.equals(UN_KNOW, ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    return "";
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        return StrUtil.split(ipAddress, StrPool.COMMA).get(0);
    }

    /**
     * 根据请求获取发送请求的浏览器名称
     *
     * @param request 请求
     * @return 浏览器名称
     */
    public static String getBrowserName(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getBrowser().getName();
    }

    /**
     * 根据请求获取发送请求的操作系统名称
     *
     * @param request 请求
     * @return 操作系统名称
     */
    public static String getOperatingSystemName(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getOperatingSystem().getName();
    }

    /**
     * 根据ip地址获取真实地理位置
     *
     * @param ipAddress ip地址
     * @return 物理位置
     */
    public static String getIpSource(String ipAddress) {
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ipAddress + "&co=&resource_id=6006&oe=utf8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(result));
            JSONObject data = JSON.parseObject(jsonObject.get("data").toString());
            return String.valueOf(data.get("location"));
//            Map map = JSON.parseObject(result.toString(), Map.class);
//            List<Map<String, String>> data = (List) map.get("data");
//            return data.get(0).get("location");
        } catch (Exception e) {
            return "";
        }
    }
}