package com.zz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AutoProduceScriptForEightDB {
	Configuration cnf = new Configuration();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	Map<String, Object> map = new HashMap<String, Object>();
	Writer write;

	private void doJob() throws IOException, TemplateException {
		this.putSomeParam();
		this.readFromTxt();
	}

	private void putSomeParam() {
		// ���ʱ��
		String time = sdf.format(new Date());
		map.put("logname", "log" + time);
		// û��ֻ����һ������
	}

	private void readFromTxt() throws IOException, TemplateException {
		// ��ȡ�����ļ�
		File file = new File("D:/autoexec8/db.txt");
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
		List<String> list = new ArrayList<String>();
		String i  =null ;
		while ((i=read.readLine())!= null) {
			list.add(i);
		}
		map.put("dblist", list);
		// ��ȡ�����
		write = new FileWriter(new File("D:/autoexec8/main.sql"));
		// ����ģ�������ļ�
		cnf = new Configuration();
		File file1 = new File("D:/autoexec8/");
		cnf.setDirectoryForTemplateLoading(file1);
		// ����ģ�������ļ�
		Template tmp = cnf.getTemplate("main.tpl");
		tmp.process(map, write);
	}

	public static void main(String[] args) {
		try {
			new AutoProduceScriptForEightDB().doJob();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

}
