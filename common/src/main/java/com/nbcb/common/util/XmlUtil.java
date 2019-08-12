package com.nbcb.common.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {
	private static String itemStr = "Item";

	public static String encode(Object o, String encode, String rootKey) {
		Document d = DocumentHelper.createDocument();
		d.setXMLEncoding(encode);
		Element root = encodeNode(rootKey, o);
		d.add(root);
		return d.asXML();
	}

	private static Element encodeNode(String strKey, Object o) {
		DocumentFactory factory = DocumentFactory.getInstance();
		Element e = factory.createElement(strKey);

		if (o instanceof Map) {
			Map m = (Map) o;
			Iterator i = m.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry en = (Map.Entry) i.next();
				String key = (String) en.getKey();
				Object value = (Object) en.getValue();
				e.add(encodeNode(key, value));
			}
			return e;
		} else if (o instanceof List) {
			List list = (List) o;
			for (int i = 0; i < list.size(); i++) {
				e.add(encodeNode(itemStr, list.get(i)));
			}
			return e;
		} else if (o instanceof Integer) {
			e.setText(String.valueOf(o));
			return e;
		} else if (o instanceof String) {
			e.setText(String.valueOf(o));
			// e.addCDATA((String) o);
			return e;
		} else {
			return null;
		}
	}

	public static Map decode(String str) throws DocumentException {
		Document doc = null;
		SAXReader sr = new SAXReader();
		String FEATURE1 = "http://apache.org/xml/features/disallow-doctype-decl";		      
		String FEATURE2 = "http://xml.org/sax/features/external-general-entities";
		String FEATURE3 = "http://xml.org/sax/features/external-parameter-entities";
		String FEATURE4 = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
		try {
			sr.setFeature(FEATURE1, true);
			sr.setFeature(FEATURE2, false);
			sr.setFeature(FEATURE3, false);
			sr.setFeature(FEATURE4, false);
			sr.setValidation(false);
			doc = sr.read(new ByteArrayInputStream(str.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new DocumentException("关闭失败!");
		}
		Element rootElt = doc.getRootElement(); // 获取根节点

		if (allItem(rootElt)) {
			throw new DocumentException("根节点下不允许有item元素");
		}

		return (Map) decodeNode(rootElt);

	}

	private static Object decodeNode(Element e) throws DocumentException {
		if (!hasChild(e)) {
			return e.getTextTrim();
		}
		boolean bAllItem = allItem(e);

		String key = e.getName();

		if (bAllItem) {
			List list = new ArrayList();
			Iterator i = e.elementIterator();
			while (i.hasNext()) {
				list.add(decodeNode((Element) i.next()));
			}
			return list;
		} else {
			Map tmpM = new HashMap();
			Iterator i = e.elementIterator();
			while (i.hasNext()) {
				Element tmpE = (Element) i.next();
				String tmpKey = tmpE.getName();
				tmpM.put(tmpKey, decodeNode(tmpE));
			}
			return tmpM;
		}
	}

	private static boolean hasChild(Element e) {
		Iterator i = e.elementIterator();
		if (i.hasNext())
			return true;
		return false;
	}

	private static boolean allItem(Element e) throws DocumentException {
		Iterator i = e.elementIterator();
		int total = e.elements().size();
		int item = 0;
		while (i.hasNext()) {
			Element tmpE = (Element) i.next();
			if (tmpE.getName().equals(itemStr))
				item++;
		}
		if (item == 0) {
			return false;
		}
		if (item < total) {
			throw new DocumentException("元素中即有ITEM元素，又有其他字段的属性");
		}
		return true;
	}
	public static void main(String [] args){
		String str= "<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str><openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid><out_trade_no><![CDATA[1409811653]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign><sub_mch_id><![CDATA[10000100]]></sub_mch_id><time_end><![CDATA[20140903131540]]></time_end><total_fee>1</total_fee><coupon_fee><![CDATA[10]]></coupon_fee><coupon_count><![CDATA[1]]></coupon_count><coupon_type><![CDATA[CASH]]></coupon_type><coupon_id><![CDATA[10000]]></coupon_id><coupon_fee><![CDATA[100]]></coupon_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id></xml>";
		try {
			System.out.println(XmlUtil.decode(str));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
