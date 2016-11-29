package com.viki.core;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.viki.bean.HouseBean;
import com.viki.bean.LocationBean;
import com.viki.bean.StreetBean;
import com.viki.utils.DBUtils;
import com.viki.utils.FileUtils;
import com.viki.utils.HtmlUtils;
import com.viki.utils.RegexUtils;

public class SellingDataCore{		

	private static ArrayList<HouseBean> newHouseList = new ArrayList<HouseBean>();
	private static ArrayList<HouseBean> oldHouseList = new ArrayList<HouseBean>();

	public static void go(){
		//[1]���ֵ���ȡ���ӷ��ʣ���װ���ս����
		getContentByStreet("utf-8");
//		HtmlUtils.getContentByStreet("utf-8", HtmlUtils.PATH_SELL, RegexUtils.REGEX_SELL_1, RegexUtils.REGEX_HOUSE,newHouseList);
		//[2]�õ��������
		analysis();
		//[3]д�����ݿ�
		insert();
		//[4]д���ļ�
		write();

	}

	
	
//	public static void getLastData(){
//		oldHouseList.addAll(DBUtils.getHouseData());
//	}
	
	public static void getContentByStreet(String charSet){
		//[1]���ֵ�����
		String url = "";
		
		for(StreetBean s: HtmlUtils.streetList){	
			System.out.println("*******************");
			System.out.println("����ץȡ��" + s.getLocationName() + " " + s.getName());
			int totalPage = 0;
			int currentPage = 1;
			//[1.1]ƴ�ֵ�������������
			url = HtmlUtils.URL + HtmlUtils.PATH_SELL + s.getValue() + "hu1/";

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
				String pa = RegexUtils.REGEX_SELL_1;
				Pattern p1 = Pattern.compile(pa);
				Matcher m1 = p1.matcher(result);
				boolean find = m1.find();
				if(!find){
					System.out.println("ҳ����Ϣδ����");
					continue;
				}
				//[1.5]��׼ץȡ
				newHouseList.addAll(SellingDataCore.regexAndCreateHouse(m1.group(),s.getLocationName(),s.getName()
						,RegexUtils.REGEX_HOUSE));
				//[2]�����¸�ҳ��
				if(totalPage == 0)
				{
					String pattern = "\"totalPage\":(.+?),\"curPage\":(.+?)}";
					Pattern p = Pattern.compile(pattern);
					Matcher m = p.matcher(result);			
					if(!m.find()){
						result = HtmlUtils.getHtmlData(url, charSet);
						m = p.matcher(result);
						m.find();
					}
					totalPage = Integer.parseInt(m.group(1));
					currentPage = Integer.parseInt(m.group(2));
				}
				System.out.println("total:" + totalPage + " " + "current:" + currentPage);
				url = HtmlUtils.URL + HtmlUtils.PATH_SELL+s.getValue() + "hu1pg" + (++currentPage) + "/";
			}while(currentPage <= totalPage);
		}
	}
	

	
	public static ArrayList<HouseBean> regexAndCreateHouse(String tarStr,String houseLocation,String street,
			String pattern){
		ArrayList<HouseBean> list = new ArrayList<HouseBean>();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(tarStr);
		boolean isFind = m.find();
		while(isFind){
			HouseBean house = HouseBean.createHouse(m.group(1), m.group(2), houseLocation,m.group(3), " ",
					m.group(4), m.group(5), m.group(6),street);
			list.add(house);
			isFind = m.find();
			System.out.println(house.getRegion());
		}
		return list;
	}
	public static ArrayList<HouseBean> getHouseList(){
		return  newHouseList;
	}
	private static void insert(){
		DBUtils.insert(newHouseList);
	}
	private static void write(){
		FileUtils.write(newHouseList);
	}
	public static void analysis(){
		System.out.println("*******************");
		System.out.println("���ڷ�����Դ��Ϣ��");
//		HouseBean test = HouseBean.createHouse("", "", "", "", "", "123", "101000370056", "12345", "");
//		int count = 1;		FileUtils.write(newHouseList);
//		newHouseList.add(test);
		for(HouseBean h: newHouseList){
//			System.out.println("���ڷ�����" + (++count) + "������");
			HouseBean oh = DBUtils.findHouse(h.getId());
			if(oh == null){
				h.setNew(true);
				h.setUpOrDown(0);//��ƽ
			}else{
				h.setNew(false);
				double margin = h.getTotalPrice() - oh.getTotalPrice();
				h.setUpOrDown(margin);
			}
			
		}
		System.out.println("������ɣ�");
	}
}
