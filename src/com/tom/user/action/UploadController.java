package com.tom.user.action;

import com.tom.util.BaseUtil;
import com.tom.util.Constants;
import com.tom.web.message.MessageHelper;
import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController
{
  @RequestMapping(value={"/common/upload.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  public void uploadFiles(@RequestParam MultipartFile file, HttpServletRequest request, PrintWriter out, ModelMap map)
  {
    boolean isok = false;
    String new_filename = "";
    String separator = System.getProperty("file.separator");
    DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String basepath = separator + "upload" + separator + "files" + separator + sdf.format(Long.valueOf(System.currentTimeMillis())) + separator;
    String filepath = Constants.getPhysicalPath() + basepath;
    if (!new File(filepath).exists())
    {
      File dir = new File(filepath);
      dir.mkdirs();
    }
    try
    {
      byte[] bytes = file.getBytes();
      String filename = file.getOriginalFilename();
      String extname = filename.substring(filename.lastIndexOf("."));
      if (BaseUtil.isFileTypeAllowed(extname))
      {
        new_filename = UUID.randomUUID().toString() + extname;
        
        File uploadedFile = new File(filepath + new_filename);
        FileCopyUtils.copy(bytes, uploadedFile);
        isok = true;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    String code = isok ? "y" : "n";
    String fullpath = isok ? (basepath + new_filename).replace("\\", "/") : "";
    JSONObject json = new JSONObject();
    json.put("code", code);
    json.put("name", fullpath);
    out.write(json.toString());
  }
  
  @RequestMapping(value={"/common/upload_courseware.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  public void uploadCourseWares(@RequestParam MultipartFile file, HttpServletRequest request, PrintWriter out, ModelMap map)
  {
    boolean isok = false;
    String new_filename = "";
    String separator = System.getProperty("file.separator");
    DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String basepath = separator + "upload" + separator + "courseware" + separator + sdf.format(Long.valueOf(System.currentTimeMillis())) + separator;
    String filepath = Constants.getPhysicalPath() + basepath;
    if (!new File(filepath).exists())
    {
      File dir = new File(filepath);
      dir.mkdirs();
    }
    try
    {
      byte[] bytes = file.getBytes();
      String filename = file.getOriginalFilename();
      String extname = filename.substring(filename.lastIndexOf("."));
      if (BaseUtil.isFileTypeAllowed(extname))
      {
        new_filename = UUID.randomUUID().toString() + extname;
        
        File uploadedFile = new File(filepath + new_filename);
        FileCopyUtils.copy(bytes, uploadedFile);
        isok = true;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    String code = isok ? "y" : "n";
    String fullpath = isok ? (basepath + new_filename).replace("\\", "/") : "";
    JSONObject json = new JSONObject();
    json.put("code", code);
    json.put("name", fullpath);
    out.write(json.toString());
  }
  
  @RequestMapping(value={"/common/ckupload.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  public void uploadCKFiles(@RequestParam("upload") MultipartFile file, HttpServletRequest request, PrintWriter out, ModelMap map)
  {
    String callback = request.getParameter("CKEditorFuncNum");
    
    String new_filename = "";
    String separator = System.getProperty("file.separator");
    DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String basepath = separator + "upload" + separator + "editor" + separator + sdf.format(Long.valueOf(System.currentTimeMillis())) + separator;
    String filepath = Constants.getPhysicalPath() + basepath;
    if (!new File(filepath).exists())
    {
      File dir = new File(filepath);
      dir.mkdirs();
    }
    try
    {
      byte[] bytes = file.getBytes();
      String filename = file.getOriginalFilename();
      String extname = filename.substring(filename.lastIndexOf("."));
      if (BaseUtil.isFileTypeAllowed(extname))
      {
        new_filename = UUID.randomUUID().toString() + extname;
        
        File uploadedFile = new File(filepath + new_filename);
        FileCopyUtils.copy(bytes, uploadedFile);
        
        String fullpath = request.getContextPath() + new StringBuilder(String.valueOf(basepath)).append(new_filename).toString().replace("\\", "/");
        StringBuffer sb = new StringBuffer(256);
        sb.append("<script type=\"text/javascript\">");
        sb.append("window.parent.CKEDITOR.tools.callFunction('" + callback + "','" + fullpath + "','');");
        sb.append("</script>");
        out.write(sb.toString());
      }
      else
      {
        StringBuffer sb = new StringBuffer(256);
        sb.append("<script type=\"text/javascript\">");
        if ("en".equals(MessageHelper.getLang())) {
          sb.append("window.parent.CKEDITOR.tools.callFunction('" + callback + "',''," + "'File format is not correct (must be.Jpg/.gif/.png file)');");
        } else {
          sb.append("window.parent.CKEDITOR.tools.callFunction('" + callback + "',''," + "'����������������������.jpg/.gif/.png������');");
        }
        sb.append("</script>");
        out.write(sb.toString());
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
