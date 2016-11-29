package com.viki.main;
import com.viki.core.DealDataCore;
import com.viki.core.SellingDataCore;
import com.viki.utils.HtmlUtils;


public class Main {
	public static void main(String args[]){
		//[1]初始化数据库，得到区域信息和街道信息
		HtmlUtils.initDB();
		//[2]抓取在售房源
////		//TODO://多线程
		SellingDataCore.go();
//		//[3]抓取3日内成交房源
		DealDataCore.go();
	}
}
