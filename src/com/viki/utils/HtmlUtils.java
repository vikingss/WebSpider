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
	//定义访问种子链接
	public static final String URL = "http://bj.lianjia.com";
	public static final String PATH_SELL = "/ershoufang/";
	public static final String PATH_DEAL = "/chengjiao/";
	public static void initDB(){
		//[1]得到区域信息
		System.out.println("*******************");
		System.out.println("正在更新区域信息！");
		areaList = DBUtils.getAreaData();
		System.out.println("更新区域信息成功！");	
		//[2]根据区域抓取街道信息
		System.out.println("*******************");
		System.out.println("正在抓取街道信息！");

		for(LocationBean l: areaList){
			//[2.1]初步抓取缩小范围
			String url = URL + PATH_SELL + l.getValue() + "/";;
			String result = HtmlUtils.getHtmlData(url, "utf-8");	
			String pattern = RegexUtils.REGEX_STREET_1;
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(result);
//			System.out.println(result);
			if(!m.find()){
				System.out.println("抓取街道信息失败!系统从数据库中读取！");
				streetList.addAll(DBUtils.getStreet());
				return;
			}else{
				String str = m.group();
				//[2.2]精确抓取
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
		System.out.println("抓取街道信息成功！");		
		//[3]写入数据库
		System.out.println("*******************");
		System.out.println("正在写入数据库！");
		DBUtils.clearStreet();
		if(DBUtils.insertStreet(streetList)){			
			System.out.println("写入数据成功！");
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
			System.out.println("发送GET请求出现异常！" + e);
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
		//[1]按街道访问
		String url = "";
		for(StreetBean s: streetList){	
			System.out.println("*******************");
			System.out.println("正在抓取：" + s.getLocationName() + " " + s.getName());
			int totalPage = 0;
			int currentPage = 1;
			//[1.1]拼街道访问种子链接
			url = HtmlUtils.URL + linkContent + s.getValue() + "hu1/";	
			//[1.2]开始按页访问
			String result = "";
			do{
//						try {
//							Thread.currentThread().sleep(300);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
				result = HtmlUtils.getHtmlData(url, charSet);
				//[1.4]粗略抓取
//				String pa = regex;
				Pattern p1 = Pattern.compile(regex1);
				Matcher m1 = p1.matcher(result);
				boolean find = m1.find();
				if(!find){
					System.out.println("页面信息未读出");
					continue;
				}
				//[1.5]精准抓取
//				list.addAll(SellingDataCore.regexAndCreateHouse(m1.group(),s.getLocationName(),regex2));
				//[2]跳到下个页面
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
