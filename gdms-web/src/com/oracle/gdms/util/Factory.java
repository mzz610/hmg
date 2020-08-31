package com.oracle.gdms.util;

import java.util.ResourceBundle;

public class Factory {
	
	private static ResourceBundle rb;//����һ����Դ�󶨶���
	
	static {
		rb = ResourceBundle.getBundle("config/application");
	}
	
	private Factory() {	}
	
	private static Factory fac;
	
	public static Factory getInstance() {
		fac = fac == null ? new Factory() : fac;
		return fac;
		
	}
	public Object getObject(String key) {
		//��ȡ�����ļ����������ļ����ҵ�Key��Ӧ��Class·��������
		String classname = rb.getString(key);
		Object o = null;
		try {
			o = Class.forName(classname).newInstance();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return o;
	}
}