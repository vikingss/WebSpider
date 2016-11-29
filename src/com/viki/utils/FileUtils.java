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
			System.out.println("��ʼ��Excel����ʧ�ܣ�--------------------");
			e.printStackTrace();
		}
	}
	private static void createFileName(){
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		FILE_NAME = "��Դ��Ϣ_" + retStrFormatNowDate + ".xlsx";
	}
	public static void write(ArrayList<HouseBean> list){
		System.out.println("*******************");
		System.out.println("����д���ļ���");
		HouseBean house = new HouseBean();
//		house.setId(1111);
//		house.setInfo("dsa");
//		house.setUpOrDown(0);
//		list.add(house);
		try{
			init();
			//дһ��Sheet��������
			XSSFSheet sheet = wb.createSheet("��Դ��Ϣ");
			createHeader();
			XSSFCellStyle greenStyle = createStyle(IndexedColors.BRIGHT_GREEN.getIndex());
			XSSFCellStyle redStyle = createStyle(IndexedColors.RED.getIndex());			
			XSSFCellStyle yellowStyle = createStyle(IndexedColors.YELLOW.getIndex());
			for(int i = 0; i < list.size(); ++i){
				XSSFRow r = sheet.createRow(i + 1);
				HouseBean h = list.get(i);			
				if(h.isNew()){//�·�Դ
					for(int j = 0;j <= 8; ++j){					
						createCell(r,j,yellowStyle);
					}										
				}else{
					if(h.getUpOrDown() > 0){//����
						for(int j = 0;j <= 8; ++j){
							createCell(r,j,redStyle);
						}	
					}else if(h.getUpOrDown() < 0){//�µ�
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
			System.out.println("д���ļ��ɹ���");
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void createHeader(){
		XSSFSheet sheet = wb.getSheet("��Դ��Ϣ");
		XSSFRow row = sheet.createRow(0);
		XSSFFont font = wb.createFont();
		font.setFontName("΢���ź�"); 
		XSSFCellStyle style = wb.createCellStyle();
		
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		for(int j = 0;j <= 8; ++j){					
			createCell(row,j,style);
		}	
		row.getCell(0).setCellValue("ID");
		row.getCell(1).setCellValue("С��");
		row.getCell(2).setCellValue("����");
		row.getCell(3).setCellValue("�ܼ�");
		row.getCell(4).setCellValue("����");
		row.getCell(5).setCellValue("������Ϣ");
		row.getCell(6).setCellValue("����");
		row.getCell(7).setCellValue("�ǵ��������գ�");
		row.getCell(8).setCellValue("���Ϸ�Դ");
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
