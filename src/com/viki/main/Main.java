package com.viki.main;
import com.viki.core.DealDataCore;
import com.viki.core.SellingDataCore;
import com.viki.utils.HtmlUtils;


public class Main {
	public static void main(String args[]){
		//[1]��ʼ�����ݿ⣬�õ�������Ϣ�ͽֵ���Ϣ
		HtmlUtils.initDB();
		//[2]ץȡ���۷�Դ
////		//TODO://���߳�
		SellingDataCore.go();
//		//[3]ץȡ3���ڳɽ���Դ
		DealDataCore.go();
	}
}
