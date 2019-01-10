package com.Aipo.Migration.Cybozulive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

	//csvファイル生成の関数
	public static BufferedWriter makeCsvFile(String fileName) {
		BufferedWriter result = null;
		try {
			result = new BufferedWriter(
			        new OutputStreamWriter(
			                new FileOutputStream(
			                        new File("aipo-csv/" + fileName)), "Shift-JIS"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
        try {
        	System.out.println("CybozuLiveからエクスポートしたファイル名を入力してください");
        	Scanner scan = new Scanner(System.in);
        	String fileName = scan.nextLine();
        	scan.close();

        	//Aipo用に変換するCSVファイルを引数の記述する
        	Reader in = new FileReader("cybozuLive-csv/" + fileName);

        	//読み込んだCSVファイルをフォーマット
		    CSVParser parser = CSVFormat
			        .EXCEL
			        .withIgnoreEmptyLines(true)
			        .withFirstRecordAsHeader()
			        .withIgnoreSurroundingSpaces(true)
			        .parse(in);

        	//読み込んだCSVをListに代入
	    	List<CSVRecord> recordList = parser.getRecords();

		    //読み込んだCSVのレコード数によって、作成するファイル数を条件分岐
	    	CSVPrinter printer_50 = null;
	    	CSVPrinter printer_100 = null;
	    	CSVPrinter printer_150 = null;
	    	CSVPrinter printer_200 = null;
	    	CSVPrinter printer_250 = null;
	    	CSVPrinter printer_300 = null;

	    	if(recordList.size() < 51) {
	    		printer_50 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用50.csv"));
	    	} else if (recordList.size() > 50 && recordList.size() < 101) {
	    		printer_50 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用50.csv"));
	    		printer_100 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用51~100.csv"));
	    	} else if (recordList.size() > 100 && recordList.size() < 151) {
	    		printer_50 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用50.csv"));
	    		printer_100 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用51~100.csv"));
	    		printer_150 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用101~150.csv"));
	    	} else if (recordList.size() > 150 && recordList.size() < 201) {
	    		printer_50 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用50.csv"));
	    		printer_100 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用51~100.csv"));
	    		printer_150 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用101~150.csv"));
	    		printer_200 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用151~200.csv"));
	    	} else if (recordList.size() > 200 && recordList.size() < 251) {
	    		printer_50 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用50.csv"));
	    		printer_100 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用51~100.csv"));
	    		printer_150 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用101~150.csv"));
	    		printer_200 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用151~200.csv"));
	    		printer_250 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用201~250.csv"));
	    	} else if (recordList.size() > 250 && recordList.size() < 301) {
	    		printer_50 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用50.csv"));
	    		printer_100 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用51~100.csv"));
	    		printer_150 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用101~150.csv"));
	    		printer_200 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用151~200.csv"));
	    		printer_250 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用201~250.csv"));
	    		printer_300 = CSVFormat.EXCEL.print(makeCsvFile("Aipo用251~300.csv"));
	    	}

	    	//インポートCSVを分割（Aipoが50件ずつしかインポートできないため）
	    	List<CSVRecord> list_50 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_100 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_150 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_200 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_250 = new ArrayList<CSVRecord>();
	    	List<CSVRecord> list_300 = new ArrayList<CSVRecord>();

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

		    //各要素数が0以上だった場合書き込みを行う
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

		} catch (IOException e) {
			System.out.println(e);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Done");
	}
}