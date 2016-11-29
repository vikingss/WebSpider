package com.viki.utils;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.viki.bean.HouseBean;

public class FileUtils {
	private static final String FILE_PATH = "C:/Users/Administrator/Desktop/";
	private static String FILE_NAME;
	private static XSSFWorkbook wb;
	public static void init(){
		createFileName();
		try {
//			is = new FileInputStream(fileName); 
			wb = new XSSFWorkbook();
		} catch (Exception e) {
			System.out.println("初始化Excel处理失败！--------------------");
			e.printStackTrace();
		}
	}
	private static void createFileName(){
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		FILE_NAME = "房源信息_" + retStrFormatNowDate + ".xlsx";
	}
	public static void write(ArrayList<HouseBean> list){
		System.out.println("*******************");
		System.out.println("正在写入文件！");
		HouseBean house = new HouseBean();
//		house.setId(1111);
//		house.setInfo("dsa");
//		house.setUpOrDown(0);
//		list.add(house);
		try{
			init();
			//写一个Sheet保存数据
			XSSFSheet sheet = wb.createSheet("房源信息");
			createHeader();
			XSSFCellStyle greenStyle = createStyle(IndexedColors.BRIGHT_GREEN.getIndex());
			XSSFCellStyle redStyle = createStyle(IndexedColors.RED.getIndex());			
			XSSFCellStyle yellowStyle = createStyle(IndexedColors.YELLOW.getIndex());
			for(int i = 0; i < list.size(); ++i){
				XSSFRow r = sheet.createRow(i + 1);
				HouseBean h = list.get(i);			
				if(h.isNew()){//新房源
					for(int j = 0;j <= 8; ++j){					
						createCell(r,j,yellowStyle);
					}										
				}else{
					if(h.getUpOrDown() > 0){//上涨
						for(int j = 0;j <= 8; ++j){
							createCell(r,j,redStyle);
						}	
					}else if(h.getUpOrDown() < 0){//下跌
						for(int j = 0;j <= 8; ++j){
							createCell(r,j,greenStyle);
						}
					}else{
						for(int j = 0;j <= 8; ++j){
							createCell(r,j,null);
						}
					}
				}
				r.getCell(0).setCellValue(h.getId());
				r.getCell(1).setCellValue(h.getRegion());
				r.getCell(2).setCellValue(h.getLocation());
				r.getCell(3).setCellValue(h.getTotalPrice());
				r.getCell(4).setCellValue(h.getUnitPrice());
				r.getCell(5).setCellValue(h.getInfo());
				r.getCell(6).setCellValue(h.getLink());
				r.getCell(7).setCellValue(h.getUpOrDown());
				r.getCell(8).setCellValue(h.isNew());
				
			}

			FileOutputStream os = new FileOutputStream(FILE_PATH + FILE_NAME);  
		    wb.write(os);  
		    os.close();
			System.out.println("写入文件成功！");
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void createHeader(){
		XSSFSheet sheet = wb.getSheet("房源信息");
		XSSFRow row = sheet.createRow(0);
		XSSFFont font = wb.createFont();
		font.setFontName("微软雅黑"); 
		XSSFCellStyle style = wb.createCellStyle();
		
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		for(int j = 0;j <= 8; ++j){					
			createCell(row,j,style);
		}	
		row.getCell(0).setCellValue("ID");
		row.getCell(1).setCellValue("小区");
		row.getCell(2).setCellValue("区域");
		row.getCell(3).setCellValue("总价");
		row.getCell(4).setCellValue("单价");
		row.getCell(5).setCellValue("基本信息");
		row.getCell(6).setCellValue("链接");
		row.getCell(7).setCellValue("涨跌（较昨日）");
		row.getCell(8).setCellValue("新上房源");
	}
	public  static  XSSFCell createCell(XSSFRow row,int i,XSSFCellStyle style){
		XSSFCell cell = row.createCell(i);
		cell.setCellStyle(style);
		return cell;
	}
	public static XSSFCellStyle createStyle(short t){
		XSSFCellStyle style = wb.createCellStyle();
		style = wb.createCellStyle();
		style.setFillForegroundColor(t);
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		return style;
	}
}
