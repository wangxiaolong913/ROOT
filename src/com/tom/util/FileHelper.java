package com.tom.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileHelper
{
  public static boolean doWriteFile(String path, String content)
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(path);
      OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
      osw.write(content);
      osw.flush();
      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
 
}
