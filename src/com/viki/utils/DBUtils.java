package com.viki.utils;



import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.viki.bean.DealHouseBean;
import com.viki.bean.HouseBean;
import com.viki.bean.LocationBean;
import com.viki.bean.StreetBean;

public class DBUtils {
    public static final String DB_URL = "jdbc:mysql://127.0.0.1/LJHouseData";  
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";  
    public static final String DB_USER = "admin";  
    public static final String DB_PASSWORD = "admin"; 
    
    public static Connection conn = null;  
    private DBUtils(){
    	getConn();
    }  
    public static Connection getConn(){
    	if(conn == null){
    		try {
    			 Class.forName(DB_DRIVER);//ָ����������  
			     conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);//��ȡ����  
			     conn.setAutoCommit(false);  //��Ϊ�ֶ��ύ
			} catch (Exception e) {  
			     System.out.println("�������ݿ�����ʧ�ܣ�");
			     e.printStackTrace();  
			}
    	}
    	return conn;
    }
    public static void close(){
    	try{
    		conn.close();
    	}catch(Exception e){
		    System.out.println("���ӹر�ʧ�ܣ�");
		    e.printStackTrace();  
    	}
    } 
    public static boolean insertStreet(ArrayList<StreetBean> list){
    	for(StreetBean s: list){
	    	String sql = "INSERT INTO street (name,value,locationName) VALUES"
	    			+ "('"+s.getName()+"','"+s.getValue()+"',"+"'"+s.getLocationName()+"')";
	    	try {
				PreparedStatement pst = getConn().prepareStatement(sql);
				pst.execute();
				pst.close();
			} catch (SQLException e) {
				System.out.println("insertStreet-------�������ʧ�ܣ�");
				e.printStackTrace();
				
				return false;
			}
    	}
		return true;
    	
    }
    public static ArrayList<LocationBean> getAreaData(){
    	String sql = "SELECT * FROM location";
    	ArrayList<LocationBean> list = new ArrayList<LocationBean>();
    	try {
    		PreparedStatement pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				LocationBean l = new LocationBean();
				l.setId(rs.getLong("id"));
				l.setName(rs.getString("name"));
				l.setValue(rs.getString("value"));
				list.add(l);
			}
			rs.close();
			pst.close();
			
		} catch (SQLException e) {
			System.out.println("getAreaData-------�������ʧ�ܣ�");
			e.printStackTrace();
		}
    	return list;
    }
    public static void insertOrUpdate(HouseBean h){
    	String sql = "SELECT * FROM house WHERE id =" + h.getId();
		try{
			PreparedStatement pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if(!rs.next()){//�µ�
		    	String in = "INSERT INTO house (id,region,totalPrice,unitPrice,info,tag,link) "
		    			+ "VALUES ("+h.getId()+",'"+h.getRegion()+"',"+h.getTotalPrice()+","+h.getUnitPrice()+""
		    					+ ",'"+h.getInfo()+"','"+h.getTag()+"','"+h.getLink()+"')";
		    	pst = getConn().prepareStatement(in);
		    	pst.execute();
				}
			rs.close();
			pst.close();
			}catch (Exception e){
				System.out.println("���ݸ���ʧ�ܣ�");
				e.printStackTrace();
			}
    }
    public static void clearStreet(){
    	String sql = "DELETE FROM street where 1=1";
		try{
			PreparedStatement pst = getConn().prepareStatement(sql);
			pst = getConn().prepareStatement(sql);
			pst.execute();
			pst.close();
			}catch (Exception e){
				System.out.println("���street��ʧ�ܣ�");
				e.printStackTrace();
			}
    }
    public static void clearHouse(){
    	String sql = "DELETE FROM house where 1=1";
		try{
			PreparedStatement pst = getConn().prepareStatement(sql);
			pst = getConn().prepareStatement(sql);
			pst.execute();
			pst.close();
			}catch (Exception e){
				System.out.println("���street��ʧ�ܣ�");
				e.printStackTrace();
			}
    } 
    public static void insertDeal(ArrayList<DealHouseBean> list){
    	System.out.println("*******************");
		System.out.println("���ڽ��ɽ���Դ��Ϣд�����ݿ⣡");
//		clearDeal();
    	try {
			Statement stmt = getConn().createStatement();
			for(DealHouseBean h : list){
				
				stmt.addBatch("INSERT IGNORE INTO deal (region,location,totalPrice,unitPrice,info,year,tag,link,dealDate,street) "
		    			+ "VALUES ('"+h.getRegion()+"','"+h.getLocation()+"',"+h.getTotalPrice()+","+h.getUnitPrice()+""
    					+ ",'"+h.getInfo()+"','"+h.getYear()+"','"+h.getTag()+"','"+h.getLink()+"','"+h.getDate()+"','"+h.getStreet()+"')");

		    	stmt.executeBatch();
		    	conn.commit();
			}
			stmt.close();
			System.out.println("�ɽ���Ϣд��ɹ���");
		} catch (Exception e) {
			System.out.println("�ɽ���Ϣд��ʧ�ܣ�");
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
    }
    public static void insert(ArrayList<HouseBean> list){
		System.out.println("*******************");
		System.out.println("���ڽ���Դ��Ϣд�����ݿ⣡");
		clearHouse();
    	try {
			Statement stmt = conn.createStatement();
			for(HouseBean h : list){
				
				stmt.addBatch("INSERT IGNORE INTO house (id,region,location,totalPrice,unitPrice,info,tag,link,upOrDown,isNew,street) "
		    			+ "VALUES ("+h.getId()+",'"+h.getRegion()+"','"+h.getLocation()+"',"+h.getTotalPrice()+","+h.getUnitPrice()+""
    					+ ",'"+h.getInfo()+"','"+h.getTag()+"','"+h.getLink()+"',"+h.getUpOrDown()+","+h.isNew()+",'"+h.getStreet()+"')");

		    	stmt.executeBatch();
		    	conn.commit();
			}
			stmt.close();
			System.out.println("��Դ��Ϣд��ɹ���");
		} catch (Exception e) {
			System.out.println("��Դ��Ϣд��ʧ�ܣ�");
			e.printStackTrace();
		}finally{
//			try {
//				conn.close();
//			} catch (SQLException e) {
//
//				e.printStackTrace();
//			}
		}
    }
    static URLConnection initConn(String url){
    	URL rUrl;
    	URLConnection conn = null;
    	try {
    		rUrl = new URL(url);
    		// ��ʼ��һ�����ӵ��Ǹ�url������
    		conn = rUrl.openConnection();
    	} catch (Exception e) {
    		System.out.println("initConn��ʼ������ʧ�ܣ�");
    		e.printStackTrace();
    	}
    	return conn;
    }
    
    public static ArrayList<StreetBean> getStreet(){
		ArrayList<StreetBean> list = new ArrayList<StreetBean>();
		try{
			PreparedStatement pst = getConn().prepareStatement("SELECT * FROM street");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				StreetBean s = new StreetBean();
				s.setId(rs.getLong("id"));
				s.setName(rs.getString("name"));
				s.setValue(rs.getString("value"));
				s.setLocationName(rs.getString("locationName"));
				
				list.add(s);
				
				}
			rs.close();
			pst.close();
			}catch (Exception e){
				System.out.println("getStreet���ݸ���ʧ�ܣ�");
				e.printStackTrace();
			}
		return list;
    }
    
    public static ArrayList<HouseBean> getHouseData(){
    	System.out.println("*******************");
		System.out.println("���ڻ�ȡ���շ�Դ��Ϣ��");
		ArrayList<HouseBean> list = new ArrayList<HouseBean>();
		try{
			PreparedStatement pst = getConn().prepareStatement("SELECT * FROM house");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				HouseBean h = new HouseBean();
				h.setId(rs.getLong("id"));
				h.setRegion(rs.getString("region"));
				h.setLocation(rs.getString("location"));
				h.setTotalPrice(rs.getDouble("totalPrice"));
				h.setUnitPrice(rs.getDouble("unitPrice"));
				h.setInfo(rs.getString("info"));
				h.setLink(rs.getString("link"));
				list.add(h);
				
				}
			rs.close();
			pst.close();
			}catch (Exception e){
				System.out.println("getHouseData���ݸ���ʧ�ܣ�");
				e.printStackTrace();
			}
		return list;
    }

    public static HouseBean findHouse(long id){
    	try{
    		PreparedStatement pst = (PreparedStatement) getConn().prepareStatement("SELECT * FROM house WHERE id = " + id,
    				ResultSet.TYPE_FORWARD_ONLY);
    		pst.setFetchSize(Integer.MIN_VALUE);
    		pst.setFetchDirection(ResultSet.FETCH_REVERSE);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				HouseBean h = new HouseBean();
				h.setId(rs.getLong("id"));
				h.setRegion(rs.getString("region"));
				h.setLocation(rs.getString("location"));
				h.setTotalPrice(rs.getDouble("totalPrice"));
				h.setUnitPrice(rs.getDouble("unitPrice"));
				h.setInfo(rs.getString("info"));
				h.setLink(rs.getString("link"));
				rs.close();
				pst.close();
				return h;
				}else{
					rs.close();
					pst.close();
					return null;
				}
			}catch (Exception e){
				System.out.println("���ݸ���ʧ�ܣ�");
				e.printStackTrace();
				return null;
			}
    	
    }

}
