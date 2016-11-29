package com.viki.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.viki.bean.DealHouseBean;
import com.viki.bean.HouseBean;
import com.viki.bean.LocationBean;
import com.viki.bean.StreetBean;
import com.viki.core.SellingDataCore;

public class HtmlUtils {
	public static ArrayList<LocationBean> areaList = new ArrayList<LocationBean>();
	public static ArrayList<StreetBean> streetList = new ArrayList<StreetBean>();
	//���������������
	public static final String URL = "http://bj.lianjia.com";
	public static final String PATH_SELL = "/ershoufang/";
	public static final String PATH_DEAL = "/chengjiao/";
	public static void initDB(){
		//[1]�õ�������Ϣ
		System.out.println("*******************");
		System.out.println("���ڸ���������Ϣ��");
		areaList = DBUtils.getAreaData();
		System.out.println("����������Ϣ�ɹ���");	
		//[2]��������ץȡ�ֵ���Ϣ
		System.out.println("*******************");
		System.out.println("����ץȡ�ֵ���Ϣ��");

		for(LocationBean l: areaList){
			//[2.1]����ץȡ��С��Χ
			String url = URL + PATH_SELL + l.getValue() + "/";;
			String result = HtmlUtils.getHtmlData(url, "utf-8");	
			String pattern = RegexUtils.REGEX_STREET_1;
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(result);
//			System.out.println(result);
			if(!m.find()){
				System.out.println("ץȡ�ֵ���Ϣʧ��!ϵͳ�����ݿ��ж�ȡ��");
				streetList.addAll(DBUtils.getStreet());
				return;
			}else{
				String str = m.group();
				//[2.2]��ȷץȡ
				Pattern p1 = Pattern.compile(RegexUtils.REGEX_STREET_2);
				Matcher m1 = p1.matcher(str);
				while(m1.find()){
					StreetBean s = new StreetBean();
					s.setName(m1.group(2));
					s.setValue(m1.group(1));
					s.setLocationName(l.getName());
					streetList.add(s);		
				}
			}
			
		}		
		System.out.println("ץȡ�ֵ���Ϣ�ɹ���");		
		//[3]д�����ݿ�
		System.out.println("*******************");
		System.out.println("����д�����ݿ⣡");
		DBUtils.clearStreet();
		if(DBUtils.insertStreet(streetList)){			
			System.out.println("д�����ݳɹ���");
		}
	}
	
	
	
	
	public static String getHtmlData(String url,String charSet){
		URLConnection conn = null;

		conn = DBUtils.initConn(url);

		String result = "";
		BufferedReader in = null;
		try {			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charSet));
			String line;
			while((line = in.readLine()) != null){
				result += line;
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (Exception e) {					
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void getContentByStreet(String charSet,String linkContent,String regex1,String regex2,ArrayList list){
		//[1]���ֵ�����
		String url = "";
		for(StreetBean s: streetList){	
			System.out.println("*******************");
			System.out.println("����ץȡ��" + s.getLocationName() + " " + s.getName());
			int totalPage = 0;
			int currentPage = 1;
			//[1.1]ƴ�ֵ�������������
			url = HtmlUtils.URL + linkContent + s.getValue() + "hu1/";	
			//[1.2]��ʼ��ҳ����
			String result = "";
			do{
//						try {
//							Thread.currentThread().sleep(300);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
				result = HtmlUtils.getHtmlData(url, charSet);
				//[1.4]����ץȡ
//				String pa = regex;
				Pattern p1 = Pattern.compile(regex1);
				Matcher m1 = p1.matcher(result);
				boolean find = m1.find();
				if(!find){
					System.out.println("ҳ����Ϣδ����");
					continue;
				}
				//[1.5]��׼ץȡ
//				list.addAll(SellingDataCore.regexAndCreateHouse(m1.group(),s.getLocationName(),regex2));
				//[2]�����¸�ҳ��
				if(totalPage == 0)
				{
					String pattern = "\"totalPage\":(.+?),\"curPage\":(.+?)}";
					Pattern p = Pattern.compile(pattern);
					Matcher m = p.matcher(result);			
					m.find();
					totalPage = Integer.parseInt(m.group(1));
					currentPage = Integer.parseInt(m.group(2));
				}
				System.out.println("total:" + totalPage + " " + "current:" + currentPage);
				url = HtmlUtils.URL + linkContent + s.getValue() + "hu1pg" + (++currentPage) + "/";
			}while(currentPage <= totalPage);
		}
	}
	
	public static ArrayList<HouseBean> regexAndCreateHouse(String tarStr,String houseLocation,
			String pattern){
		ArrayList<HouseBean> list = new ArrayList<HouseBean>();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(tarStr);
		boolean isFind = m.find();
//		while(isFind){
//			HouseBean house = HouseBean.createHouse(m.group(1), m.group(2), houseLocation,m.group(3), " ",
//					m.group(4), m.group(5), m.group(6));
//			list.add(house);
//			isFind = m.find();
//			System.out.println(house.getRegion());
//		}
		return list;
	}
	
	public static ArrayList<DealHouseBean> regexAndCreateDeal(String tarStr,String houseLocation,
			String pattern){
		ArrayList<DealHouseBean> list = new ArrayList<DealHouseBean>();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(tarStr);
		boolean isFind = m.find();
		while(isFind){
//			DealHouseBean house = DealHouseBean.createDeal(m.group(1), m.group(2), houseLocation,m.group(3), " ",
//					m.group(4), m.group(5), m.group(6), " ");
//			list.add(house);
//			isFind = m.find();
//			System.out.println(house.getRegion());
		}
		return list;
	
	}








	
	

}
