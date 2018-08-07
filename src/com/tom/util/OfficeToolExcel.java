package com.tom.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class OfficeToolExcel
{
  public static void makeExcel(String filepath, String title, List<Map<Object, Object>> list, int[] cell_length)
    throws Exception
  {
    WritableWorkbook wwb = Workbook.createWorkbook(new File(filepath));
    WritableSheet ws = wwb.createSheet("Sheet1", 0);
    if ((list == null) || (cell_length == null)) {
      throw new Exception("parameters is null");
    }
    ws.mergeCells(0, 0, cell_length.length - 1, 1);
    Label header = new Label(0, 0, title, getHeader());
    ws.addCell(header);
    
    int row = 2;
    for (Map<Object, Object> map : list)
    {
      int col = 0;
      for (Iterator localIterator2 = map.keySet().iterator(); localIterator2.hasNext();)
      {
        Object obj = localIterator2.next();
        if (row == 2)
        {
          Label label = new Label(col, row, String.valueOf(obj), getTitle());
          ws.addCell(label);
        }
        else
        {
          ws.addCell(new Label(col, row, String.valueOf(map.get(obj)), getNormolCell()));
        }
        ws.setColumnView(col, cell_length[col]);
        col++;
      }
      row++;
    }
    wwb.write();
    wwb.close();
  }
  
  public static List<Map> readExcel(File filename, String[] alisa)
    throws BiffException, IOException
  {
    List list = new ArrayList();
    Workbook wb = Workbook.getWorkbook(filename);
    Sheet s = wb.getSheet(0);
    Cell c = null;
    int row = s.getRows();
    int col = s.getColumns();
    for (int i = 0; i < row; i++)
    {
      Map map = new HashMap();
      for (int j = 0; j < col; j++)
      {
        c = s.getCell(j, i);
        map.put(alisa[j], c.getContents());
      }
      list.add(map);
    }
    return list;
  }
  
  private static WritableCellFormat getHeader()
  {
    WritableFont font = new WritableFont(WritableFont.TIMES, 11, 
      WritableFont.BOLD);
    try
    {
      font.setColour(Colour.BLACK);
    }
    catch (WriteException e1)
    {
      e1.printStackTrace();
    }
    WritableCellFormat format = new WritableCellFormat(font);
    try
    {
      format.setAlignment(Alignment.CENTRE);
      format.setVerticalAlignment(VerticalAlignment.CENTRE);
      format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
      format.setBackground(Colour.GRAY_50);
    }
    catch (WriteException e)
    {
      e.printStackTrace();
    }
    return format;
  }
  
  private static WritableCellFormat getTitle()
  {
    WritableFont font = new WritableFont(WritableFont.TIMES, 10, 
      WritableFont.BOLD);
    try
    {
      font.setColour(Colour.BLACK);
    }
    catch (WriteException e1)
    {
      e1.printStackTrace();
    }
    WritableCellFormat format = new WritableCellFormat(font);
    try
    {
      format.setAlignment(Alignment.CENTRE);
      format.setVerticalAlignment(VerticalAlignment.CENTRE);
      format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
      format.setBackground(Colour.GRAY_25);
    }
    catch (WriteException e)
    {
      e.printStackTrace();
    }
    return format;
  }
  
  private static WritableCellFormat getNormolCell()
  {
    WritableFont font = new WritableFont(WritableFont.TIMES, 10);
    WritableCellFormat format = new WritableCellFormat(font);
    try
    {
      format.setAlignment(Alignment.CENTRE);
      format.setVerticalAlignment(VerticalAlignment.CENTRE);
      format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
    }
    catch (WriteException e)
    {
      e.printStackTrace();
    }
    return format;
  }
  
  public static void main(String[] s)
  {
    try
    {
      List<Map> list = readExcel(new File("D:\\test.xls"), new String[] {
        "M_ID", "M_URL", "CTDATE", "M_DESC", "M_NAME" });
      for (Map map : list)
      {
        System.out.println(map.get("M_ID") + "," + map.get("M_URL") + 
          "," + map.get("CTDATE") + "," + map.get("M_DESC") + 
          "," + map.get("M_NAME"));
        System.out
          .println("\n================================================");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
