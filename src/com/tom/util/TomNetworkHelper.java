package com.tom.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class TomNetworkHelper
{
  public static void main(String... s) {}
  
  public static boolean isInnerIP(String ipAddress)
  {
    boolean isInnerIp = false;
    long ipNum = getIpNum(ipAddress);
    
    long aBegin = getIpNum("10.0.0.0");
    long aEnd = getIpNum("10.255.255.255");
    
    long bBegin = getIpNum("172.16.0.0");
    long bEnd = getIpNum("172.31.255.255");
    
    long cBegin = getIpNum("192.168.0.0");
    long cEnd = getIpNum("192.168.255.255");
    
    isInnerIp = (isInner(ipNum, aBegin, aEnd)) || 
      (isInner(ipNum, bBegin, bEnd)) || (isInner(ipNum, cBegin, cEnd)) || 
      (ipAddress.equals("127.0.0.1"));
    return isInnerIp;
  }
  
  private static long getIpNum(String ipAddress)
  {
    String[] ip = ipAddress.split("\\.");
    long a = Integer.parseInt(ip[0]);
    long b = Integer.parseInt(ip[1]);
    long c = Integer.parseInt(ip[2]);
    long d = Integer.parseInt(ip[3]);
    
    long ipNum = a * 256L * 256L * 256L + b * 256L * 256L + c * 256L + d;
    return ipNum;
  }
  
  private static boolean isInner(long userIp, long begin, long end)
  {
    return (userIp >= begin) && (userIp <= end);
  }
  
  public static String getIp()
  {
    String localip = null;
    String netip = null;
    try
    {
      Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
      InetAddress ip = null;
      boolean finded = false;
      do
      {
        NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
        Enumeration<InetAddress> address = ni.getInetAddresses();
        while (address.hasMoreElements())
        {
          ip = (InetAddress)address.nextElement();
          if ((!ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress()) && 
            (ip.getHostAddress().indexOf(":") == -1))
          {
            netip = ip.getHostAddress();
            finded = true;
            break;
          }
          if ((ip.isSiteLocalAddress()) && 
            (!ip.isLoopbackAddress()) && 
            (ip.getHostAddress().indexOf(":") == -1)) {
            localip = ip.getHostAddress();
          }
        }
        if (!netInterfaces.hasMoreElements()) {
          break;
        }
      } while (!finded);
    }
    catch (SocketException e)
    {
      e.printStackTrace();
    }
    if ((netip != null) && (!"".equals(netip))) {
      return netip;
    }
    return localip;
  }
}
