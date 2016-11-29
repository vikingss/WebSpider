package com.viki.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.viki.bean.DealHouseBean;
import com.viki.bean.StreetBean;
import com.viki.utils.DBUtils;
import com.viki.utils.HtmlUtils;
import com.viki.utils.RegexUtils;

public class DealDataCore{
	private static ArrayList<DealHouseBean> dealList = new ArrayList<DealHouseBean>();
	private static final String DEAL_DATE ="2016.11.01";
	private static boolean isContinue = true;
	public static void go() {
		getContentByStreet("utf-8");
		//[3]д�����ݿ�
		DBUtils.insertDeal(dealList);
//		//[4]д���ļ�
//		write();		
	}
	public static void getContentByStreet(String charSet){
		//[1]���ֵ�����
		String url = "";
		for(StreetBean s: HtmlUtils.streetList){	
			System.out.println("*******************");
			System.out.println("����ץȡ��" + s.getLocationName() + " " + s.getName());
			int totalPage = 0;
			int currentPage = 1;
			//[1.1]ƴ�ֵ�������������
			url = HtmlUtils.URL + HtmlUtils.PATH_DEAL + s.getValue() + "hu1/";

			//[1.2]��ʼ��ҳ����
			String result = "";
			do{
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				result = HtmlUtils.getHtmlData(url, charSet);
				//[1.4]����ץȡ
				String pa = RegexUtils.REGEX_DEAL_1;
				Pattern p1 = Pattern.compile(pa);
				Matcher m1 = p1.matcher(result);
				boolean find = m1.find();
				if(!find){
					System.out.println("ҳ����Ϣδ����");
					continue;
				}
				//[1.5]��׼ץȡ
				dealList.addAll(DealDataCore.regexAndCreateHouse(m1.group(),s.getLocationName(),s.getName()
						,RegexUtils.REGEX_DEAL));
				if(!isContinue)
					break;
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
				url = HtmlUtils.URL + HtmlUtils.PATH_DEAL + s.getValue() + "hu1pg" + (++currentPage) + "/";
			}while(currentPage <= totalPage);
		}
	}
	public static ArrayList<DealHouseBean> regexAndCreateHouse(String tarStr,String houseLocation,String street,String pattern){
		ArrayList<DealHouseBean> list = new ArrayList<DealHouseBean>();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(tarStr);
		boolean isFind = m.find();


		while(isFind){
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		    Date date1 = null;
		    Date date2 = null;
		    try{
		    	date1 = format.parse(DEAL_DATE);
		    	date2 = format.parse(m.group(3));
		    	}catch (ParseException e){
		    		e.printStackTrace();
		    	}
		    
		    if(date1.after(date2)){
		    	isContinue = false;
		    	System.out.println("�ɽ�����ʱ�䷶Χ!" + format.format(date2));
		    	return list;
			}
//		    System.out.println(m.group(1) + m.group(2).split(" ")[0] + houseLocation + m.group(5)+m.group(2).split(" ")[1]+m.group(2).split(" ")[2]
//		    		+m.group(4));
			DealHouseBean house = DealHouseBean.createDeal(m.group(1), m.group(2).split(" ")[0], 
					houseLocation, m.group(5), m.group(2).split(" ")[1]+m.group(2).split(" ")[2], m.group(4), m.group(6), " ",m.group(3),street);
			list.add(house);
			
			isFind = m.find();
			isContinue = true;
			System.out.println(house.getRegion());
		}
		return list;
	}
}
