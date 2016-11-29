package com.viki.utils;

public class RegexUtils {

	public static final String REGEX_LINK = "<a[\\s]class=\"\"[\\s]href=\"(.+?)\"";
	public static final String REGEX_TOTAL_PRICE = "class=\"totalPrice\"><span>(.+?)</span>";
	public static final String REGEX_ID_AND_UNIT_PRICE = "class=\"unitPrice\".+?data-hid=\"(.+?)\".+?data-price=\"(.+?)\">";
	public static final String REGEX_REGION_AND_INFO = "data-el=\"region\">(.+?)</a>(.+?)</div>";
	public static final String REGEX_TAG = "class=\"taxfree\">(.+?)</span>";
	public static final String REGEX_HOUSE = RegexUtils.REGEX_LINK
			+ ".+?" + REGEX_REGION_AND_INFO + ".+?"  
			+ RegexUtils.REGEX_TOTAL_PRICE + ".+?" + RegexUtils.REGEX_ID_AND_UNIT_PRICE;
	public static final String REGEX_STREET_1 = "</b>(.+?)</div>";
	public static final String REGEX_STREET_2 = "<a.+?href=\"/ershoufang/(.+?)\".+?>(.+?)</a>";
	public static final String REGEX_SELL_1 = "<ul[\\s]class=\"sellListContent\"[\\s]log-mod=\"list\">(.+?)</ul>";
	public static final String REGEX_DEAL_1 = "<ul[\\s]class=\"listContent\"(.+?)</ul>";
	public static final String REGEX_DEAL_LINK ="info\"><div[\\s]class=\"title\"><a[\\s]href=\"(.+?)\".+?_blank\">(.+?)</a></div>";
	public static final String REGEX_DEAL_DATE = "dealDate\">(.+?)</div>";
	public static final String REGEX_DEAL_FLOOR = "span[\\s]class=\"positionIcon\"></span>(.+?)</div>";
	public static final String REGEX_DEAL_UNIT_PRICE = "unitPrice\"><span[\\s]class=\"number\">(.+?)</span>";
	public static final String REGEX_DEAL_TOTAL_PRICE = "<div[\\s]class=\"totalPrice\"><span[\\s]class=\'number\'>(.+?)</span>";
	public static final String REGEX_DEAL = REGEX_DEAL_LINK + ".+?" + REGEX_DEAL_DATE  + REGEX_DEAL_TOTAL_PRICE
			+ ".+?" + REGEX_DEAL_FLOOR + ".+?" + REGEX_DEAL_UNIT_PRICE;

	public static final String REGEX_IP = "data-title=\"IP\">(.+?)</td>.+?data-title=\"PORT\">(.+?)</td>";
}
