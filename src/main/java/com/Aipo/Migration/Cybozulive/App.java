package com.Aipo.Migration.Cybozulive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;




public class App {
	//パスワード生成
	public static String generatePassword(int length) {
		Random rand = new Random(System.nanoTime());
		String charset = "!0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int pos = rand.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
        try {
        	//Aipo用に変換するファイルを引数の記述する
        	Reader in = new FileReader("utf-8.csv");

        	//読み込んだCSVファイルをフォーマット
		    CSVParser parser = CSVFormat
			        .EXCEL
			        .withIgnoreEmptyLines(true)
			        .withFirstRecordAsHeader()
			        .withIgnoreSurroundingSpaces(true)
			        .parse(in);

        	//読み込んだCSVをListに代入
	    	List<CSVRecord> recordList = parser.getRecords();

		    //ファイルの生成(if文で配列の要素数によってファイルを作成する)
			    BufferedWriter bw_50 = new BufferedWriter(
	                    new OutputStreamWriter(
	                            new FileOutputStream(
	                                    new File("Aipo用0~50.csv")), "Shift-JIS"));

			    BufferedWriter bw_100 = new BufferedWriter(
	                    new OutputStreamWriter(
	                            new FileOutputStream(
	                                    new File("Aipo用51~100.csv")), "Shift-JIS"));

			    BufferedWriter bw_150 = new BufferedWriter(
	                    new OutputStreamWriter(
	                            new FileOutputStream(
	                                    new File("Aipo用101~150.csv")), "Shift-JIS"));

			    BufferedWriter bw_200 = new BufferedWriter(
	                    new OutputStreamWriter(
	                            new FileOutputStream(
	                                    new File("Aipo用151~200.csv")), "Shift-JIS"));

			    BufferedWriter bw_250 = new BufferedWriter(
	                    new OutputStreamWriter(
	                            new FileOutputStream(
	                                    new File("Aipo用201~250.csv")), "Shift-JIS"));

			    BufferedWriter bw_300 = new BufferedWriter(
	                    new OutputStreamWriter(
	                            new FileOutputStream(
	                                    new File("Aipo用251~300.csv")), "Shift-JIS"));

		    //プリンターの生成
        	CSVPrinter printer_50 = CSVFormat.EXCEL.print(bw_50);
        	CSVPrinter printer_100 = CSVFormat.EXCEL.print(bw_100);
        	CSVPrinter printer_150 = CSVFormat.EXCEL.print(bw_150);
        	CSVPrinter printer_200 = CSVFormat.EXCEL.print(bw_200);
        	CSVPrinter printer_250 = CSVFormat.EXCEL.print(bw_250);
        	CSVPrinter printer_300 = CSVFormat.EXCEL.print(bw_300);

	    	//Aipoのインポート用に50件ずつListに代入
	    	List<CSVRecord> list_50 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_100 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_150 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_200 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_250 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_300 = new ArrayList<CSVRecord>();

	    	//インポートした要素を分割
		    for(CSVRecord s: recordList) {
		    	if (s.getRecordNumber() < 51) {
		    		list_50.add(s);
		    	} else if (s.getRecordNumber() > 50 && s.getRecordNumber() < 101) {
		    		list_100.add(s);
		    	} else if (s.getRecordNumber() > 100 && s.getRecordNumber() < 151) {
		    		list_150.add(s);
		    	} else if (s.getRecordNumber() > 150 && s.getRecordNumber() < 201) {
		    		list_200.add(s);
		    	} else if (s.getRecordNumber() > 200 && s.getRecordNumber() < 251) {
		    		list_250.add(s);
		    	} else if (s.getRecordNumber() > 250 && s.getRecordNumber() < 301) {
		    		list_300.add(s);
		    	}
		    }

		    if (list_50.size() > 0) {
				  for(CSVRecord s: list_50) {
					    String lastName = s.get("姓");
					    String firstName = s.get("名");
					    String kanaLastName = s.get("よみがな姓");
					    String kanaFirstName = s.get("よみがな名");
					    String email = s.get("メールアドレス");
					    printer_50.printRecord(email, generatePassword(8), lastName, firstName, kanaLastName, kanaFirstName);
				  }
				  printer_50.close();
		    }

		    if (list_100.size() > 0) {
				  for(CSVRecord s: list_100) {
					    String lastName = s.get("姓");
					    String firstName = s.get("名");
					    String kanaLastName = s.get("よみがな姓");
					    String kanaFirstName = s.get("よみがな名");
					    String email = s.get("メールアドレス");
					    printer_100.printRecord(email, generatePassword(8), lastName, firstName, kanaLastName, kanaFirstName);
				  }
				  printer_100.close();
		    }

		    if (list_150.size() > 0) {
				  for(CSVRecord s: list_150) {
					    String lastName = s.get("姓");
					    String firstName = s.get("名");
					    String kanaLastName = s.get("よみがな姓");
					    String kanaFirstName = s.get("よみがな名");
					    String email = s.get("メールアドレス");
					    printer_150.printRecord(email, generatePassword(8), lastName, firstName, kanaLastName, kanaFirstName);
				  }
				  printer_150.close();
		    }

		    if (list_200.size() > 0) {
				  for(CSVRecord s: list_200) {
					    String lastName = s.get("姓");
					    String firstName = s.get("名");
					    String kanaLastName = s.get("よみがな姓");
					    String kanaFirstName = s.get("よみがな名");
					    String email = s.get("メールアドレス");
					    printer_200.printRecord(email, generatePassword(8), lastName, firstName, kanaLastName, kanaFirstName);
				  }
				  printer_200.close();
		    }

		    if (list_250.size() > 0) {
				  for(CSVRecord s: list_250) {
					    String lastName = s.get("姓");
					    String firstName = s.get("名");
					    String kanaLastName = s.get("よみがな姓");
					    String kanaFirstName = s.get("よみがな名");
					    String email = s.get("メールアドレス");
					    printer_250.printRecord(email, generatePassword(8), lastName, firstName, kanaLastName, kanaFirstName);
				  }
				  printer_250.close();
		    }

		    if (list_300.size() > 0) {
				  for(CSVRecord s: list_300) {
					    String lastName = s.get("姓");
					    String firstName = s.get("名");
					    String kanaLastName = s.get("よみがな姓");
					    String kanaFirstName = s.get("よみがな名");
					    String email = s.get("メールアドレス");
					    printer_300.printRecord(email, generatePassword(8), lastName, firstName, kanaLastName, kanaFirstName);
				  }
				  printer_300.close();
		    }



			    System.out.println(list_50.size());
			    System.out.println(list_100.size());
			    System.out.println(list_150.size());
			    System.out.println(list_200.size());
			    System.out.println(list_250.size());
			    System.out.println(list_300.size());

		} catch (IOException e) {
			System.out.println(e);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Done");
	}
}