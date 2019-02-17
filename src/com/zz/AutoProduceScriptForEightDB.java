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
		// 添加时间
		String time = sdf.format(new Date());
		map.put("logname", "log" + time);
		// 没了只有这一个参数
	}

	private void readFromTxt() throws IOException, TemplateException {
		// 读取参数文件
		File file = new File("D:/autoexec8/db.txt");
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
		List<String> list = new ArrayList<String>();
		String i  =null ;
		while ((i=read.readLine())!= null) {
			list.add(i);
		}
		map.put("dblist", list);
		// 获取输出流
		write = new FileWriter(new File("D:/autoexec8/main.sql"));
		// 设置模板配置文件
		cnf = new Configuration();
		File file1 = new File("D:/autoexec8/");
		cnf.setDirectoryForTemplateLoading(file1);
		// 设置模板配置文件
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
