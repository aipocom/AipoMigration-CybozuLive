package com.Aipo.Migration.Cybozulive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;




public class App {
	private static final String charset =
			"!0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String generatePassword(int length) {
		Random rand = new Random(System.nanoTime());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int pos = rand.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
        try {
        	BufferedWriter bw_50 = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    new File("Aipo用0~50.csv")), "shift-jis"));

        	//Aipo用に変換するファイルを引数の記述する
        	Reader in = new FileReader("utf-8.csv");
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
            CSVPrinter printer = CSVFormat.EXCEL.print(bw_50);
			for (CSVRecord record : records) {

				String lastName = record.get("姓");
			    String firstName = record.get("名");
			    String kanaLastName = record.get("よみがな姓");
			    String kanaFirstName = record.get("よみがな名");
			    String email = record.get("メールアドレス");

			    //Aipoインポート必須項目
			    //メールアドレス、パスワード、姓、名、かな姓、かな名

			    //csv書き込みテスト
			    printer.printRecord(email, generatePassword(8), lastName, firstName, kanaLastName, kanaFirstName);

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