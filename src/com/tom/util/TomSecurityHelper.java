package com.tom.util;

import java.io.PrintStream;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import org.apache.commons.codec.binary.Base64;

public class TomSecurityHelper
{
  Key key;
  
  public TomSecurityHelper()
  {
    ka(Constants.SYS_IDENTIFICATION_CODE + "TOMEXAM3");
  }
  
  public void ka(String k)
  {
    try
    {
      KeyGenerator _generator = KeyGenerator.getInstance("DES");
      _generator.init(new SecureRandom(k.getBytes()));
      this.key = _generator.generateKey();
      _generator = null;
    }
    catch (Exception e)
    {
      System.out.println("TomSecurityHelper.ka()����������:" + e.getMessage());
    }
  }
  
  public String ges(String strMing)
  {
    try
    {
      byte[] result = Base64.encodeBase64(strMing.getBytes("UTF-8"));
      return new String(result, "UTF-8");
    }
    catch (Exception e)
    {
      System.out.println("TomSecurityHelper.ges()����������:" + e.getMessage());
    }
    return null;
  }
  
  public String gds(String strMi)
  {
    try
    {
      byte[] result = Base64.decodeBase64(strMi.getBytes("UTF-8"));
      return new String(result, "UTF-8");
    }
    catch (Exception e)
    {
      System.out.println("TomSecurityHelper.gds()����������:" + e.getMessage());
    }
    return null;
  }
  
  
}
