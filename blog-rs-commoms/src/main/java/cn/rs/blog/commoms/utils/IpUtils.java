package cn.rs.blog.commoms.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Ip
 */
public class IpUtils {
    private static final Logger logger = LogManager.getLogger(IpUtils.class.getName());

    /**
     * 在Servlet里，获取客户端的IP地址的方法是：request.getRemoteAddr()，
     * 这种方法在大部分情况下都是有效的。但是在通过了Apache，Squid，Nginx等反向代理软件就不能
     * 获取到客户端的真实IP地址了。
     * 如果使用了反向代理软件，例如将http://192.168.101.88:80/ 的URL反向代理为http://pay.kedou.com/ 的URL时，用request.getRemoteAddr()
     * 方法获取的IP地址是：127.0.0.1　或192.168.101.88，而并不是客户端的真实IP。
     * 如下图，原来是client端直接请求服务端，走A路线请求，这时候通过request.getRemoteAddr()
     * 方法可以准备的获取客户端的IP。但是做了代理之后呢，client端不是直接请求服务端，
     * 而是走B线路请求代理服务器，由代理器去请求服务端，这时候服务端通过request.getRemoteAddr()
     * 方法拿到的理所当然是代理服务器的地址了。
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");


        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();

            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }


}
