/*
 * This file is part of the com.aipo.migration.cybozulive package.
 * Copyright (C) 2004-2019 TOWN, Inc.
 * https://aipo.com
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.aipo.migration.cybozulive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class App {

  /** CSVファイル文字コード */
  private static final String CSV_CHARSET = "Shift-JIS";

  /** パスワード */
  private static final String PASSWORD_CHARSET =
    "!0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  /** データフォルダ */
  private static final String CYBOZULIVE_DIR = "cybozuLive-csv";

  /** データフォルダ */
  private static final String AIPO_DIR = "aipo-csv";

  /** AipoユーザーCSVファイル名 */
  private static final String AIPO_USER_CSV_PREFIX = "Aipo_users";

  /** CSVファイル最大行 */
  private static final int MAX_ROWS = 50;

  /**
   * パスワード生成
   *
   * @param length
   * @return
   */
  public static String generatePassword(int length) {
    Random rand = new Random(System.nanoTime());
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int pos = rand.nextInt(PASSWORD_CHARSET.length());
      sb.append(PASSWORD_CHARSET.charAt(pos));
    }
    return sb.toString();

  }

  /**
   * csvファイル生成の関数
   *
   * @param fileName
   * @return
   */
  public static BufferedWriter makeCsvFile(String fileName) {
    BufferedWriter result = null;
    try {
      result =
        new BufferedWriter(
          new OutputStreamWriter(
            new FileOutputStream(
              new File(AIPO_DIR + File.separator + fileName)),
            CSV_CHARSET));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * リスト分割
   *
   * @param origin
   * @param size
   * @return
   */
  public static <T> List<List<T>> divide(List<T> origin, int size) {
    if (origin == null || origin.isEmpty() || size <= 0) {
      return Collections.emptyList();
    }

    int block = origin.size() / size + (origin.size() % size > 0 ? 1 : 0);

    return IntStream.range(0, block).boxed().map(i -> {
      int start = i * size;
      int end = Math.min(start + size, origin.size());
      return origin.subList(start, end);
    }).collect(Collectors.toList());
  }

  public static void main(String[] args) {
    try {
      System.out.println("CybozuLiveからエクスポートしたファイル名を入力してください");
      Scanner scan = new Scanner(System.in);
      String fileName = scan.nextLine();
      scan.close();

      // Aipo用に変換するCSVファイルを引数の記述する
      Reader in = new FileReader(CYBOZULIVE_DIR + File.separator + fileName);

      // 読み込んだCSVファイルをフォーマット
      CSVParser parser =
        CSVFormat.EXCEL
          .withIgnoreEmptyLines(true)
          .withFirstRecordAsHeader()
          .withIgnoreSurroundingSpaces(true)
          .parse(in);

      // 読み込んだCSVをListに代入
      List<CSVRecord> recordList = parser.getRecords();

      // 読み込んだCSVを50行区切りに分割する
      List<List<CSVRecord>> divide = divide(recordList, MAX_ROWS);

      int i = 0;
      for (List<CSVRecord> fileList : divide) {
        i++;
        CSVPrinter csvPrinter =
          CSVFormat.EXCEL.print(
            makeCsvFile(AIPO_USER_CSV_PREFIX + "_" + i + ".csv"));
        for (CSVRecord s : fileList) {
          String lastName = s.get("姓");
          String firstName = s.get("名");
          String kanaLastName = s.get("よみがな姓");
          String kanaFirstName = s.get("よみがな名");
          String email = s.get("メールアドレス");
          csvPrinter.printRecord(
            email,
            generatePassword(8),
            lastName,
            firstName,
            kanaLastName,
            kanaFirstName);
        }
        csvPrinter.close();
      }

    } catch (

    IOException e) {
      System.out.println(e);
    } catch (

    Exception e) {
      System.out.println(e);
    }
    System.out.println("Done");
  }
}