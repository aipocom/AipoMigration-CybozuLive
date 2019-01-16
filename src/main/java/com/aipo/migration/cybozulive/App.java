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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class App {

  /** 出力CSVファイル文字コード */
  private static final String OUTPUT_CSV_CHARSET = "Shift-JIS";

  /** 入力CSVファイル文字コード */
  private static final String INPUT_CSV_CHARSET = "UTF-8";

  /** パスワード */
  private static final String PASSWORD_CHARSET =
    "!0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  /** データフォルダ */
  private static final String CYBOZULIVE_DIR = "cybozuLive-csv";

  /** データフォルダ */
  private static final String AIPO_DIR = "aipo-csv";

  /** AipoユーザーCSVファイル名 */
  private static final String AIPO_USER_CSV_PREFIX = "Aipo_users";

  /** AipoカレンダーCSVファイル名 */
  private static final String AIPO_CALENDAR_CSV_PREFIX = "Aipo_calendar";

  private static final String FILE_SUFFIX = "yyyyMMddHHmm";

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
      // 出力先フォルダの生成
      File dir = new File(AIPO_DIR);
      if (!dir.exists()) {
        Files.createDirectory(dir.toPath());
        System.out.println(AIPO_DIR + "フォルダを生成しました。");
      }
    } catch (Exception e) {
      System.out.println("エラーが発生しました。");
      System.out.println(e);
    }
    try {
      result =
        new BufferedWriter(
          new OutputStreamWriter(
            new FileOutputStream(
              new File(AIPO_DIR + File.separator + fileName)),
            OUTPUT_CSV_CHARSET));
    } catch (Exception e) {
      System.out.println("エラーが発生しました。");
      System.out.println(e);
    }
    System.out.println(fileName + "ファイルを生成しました。");
    return result;
  }

  /**
   * フォルダ内のファイル一覧
   *
   * @return
   */
  public static HashMap<Integer, File> getCybozuLiveFiles() {
    File dir = new File(CYBOZULIVE_DIR);
    int count = 0;
    HashMap<Integer, File> hashMap = new HashMap<Integer, File>();

    // listFilesメソッドを使用して一覧を取得する
    File[] list = dir.listFiles();
    if (list != null) {
      for (int i = 0; i < list.length; i++) {
        if (list[i].isFile()) {
          count++;
          hashMap.put(count, list[i]);
        }
      }
    }
    return hashMap;
  }
  /**
   * 配列を指定行数で分割
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

  public static void calendarExport(List<List<CSVRecord>> divide, String format) throws IOException {
        // CSVファイルを出力
      int i = 0;
      if (divide != null && divide.size() > 0) {
        for (List<CSVRecord> fileList : divide) {
          CSVPrinter csvPrinter = null;
          try {
            i++;
            csvPrinter =
              CSVFormat.EXCEL
                .withQuote('"')
                .withHeader(
                  "開始日",
                  "開始時刻",
                  "終了日",
                  "終了時刻",
                  "場所",
                  "予定",
                  "内容",
                  "名前",
                  "メールアドレス")
                .print(
                  makeCsvFile(
                    AIPO_CALENDAR_CSV_PREFIX
                      + "_"
                      + format
                      + "_"
                      + i
                      + ".csv"));
            if (fileList != null && fileList.size() > 0) {
              for (CSVRecord s : fileList) {
                String startDate = s.get("開始日付");
                String startTime = s.get("開始時刻");
                String endDate = s.get("終了日付");
                String endTime = s.get("終了時刻");
                String title = s.get("タイトル");
                String memo = s.get("メモ");
                String userName = s.get("作成者");
                csvPrinter.printRecord(
                  startDate,
                  startTime,
                  endDate,
                  endTime,
                  "",
                  title,
                  memo,
                  userName,
                  "");
              }
            }
          } catch (Exception e) {
            System.out.println("エラーが発生しました。");
            System.out.println(e);
          } finally {
            if (csvPrinter != null) {
              csvPrinter.close();
            }
          }
        }
      }
  }

  public static void memberExport(List<List<CSVRecord>> divide, String format) throws IOException {
    // CSVファイルを出力
      int i = 0;
      if (divide != null && divide.size() > 0) {
        for (List<CSVRecord> fileList : divide) {
          CSVPrinter csvPrinter = null;
          try {
            i++;
            csvPrinter =
              CSVFormat.EXCEL
                .withQuote('"')
                .withHeader(
                  "ユーザー名（メールアドレス）",
                  "パスワード",
                  "名前（姓）",
                  "名前（名）",
                  "名前（姓・フリガナ）",
                  "名前（名・フリガナ）",
                  "電話番号（外線）",
                  "電話番号（内線）",
                  "電話番号（携帯）",
                  "携帯メールアドレス",
                  "部署名",
                  "役職",
                  "社員コード")
                .print(
                  makeCsvFile(
                    AIPO_USER_CSV_PREFIX
                      + "_"
                      + format
                      + "_"
                      + i
                      + ".csv"));
            if (fileList != null && fileList.size() > 0) {
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
                  kanaFirstName,
                  "",
                  "",
                  "",
                  "",
                  "",
                  "",
                  "");
              }
            }
          } catch (Exception e) {
            System.out.println("エラーが発生しました。");
            System.out.println(e);
          } finally {
            if (csvPrinter != null) {
              csvPrinter.close();
            }
          }
        }
      }
  }


  public static void main(String[] args) {
    try {
      HashMap<Integer, File> cybozuLiveFiles = getCybozuLiveFiles();
      if (cybozuLiveFiles != null && cybozuLiveFiles.size() > 0) {
        for (Entry<Integer, File> cybozuLiveFile : cybozuLiveFiles.entrySet()) {
          System.out.println(
            "["
              + cybozuLiveFile.getKey()
              + "] "
              + cybozuLiveFile.getValue().getName());
        }
      }
      System.out.println("[0] キャンセル");
      System.out.println("CybozuLiveから移行したいファイルを選んで番号を入力してください：");
      Scanner scan = new Scanner(System.in, INPUT_CSV_CHARSET);
      String nextLine = scan.nextLine();
      scan.close();
      int parseInt = Integer.parseInt(nextLine);
      if (parseInt > 0) {
        String fileName = cybozuLiveFiles.get(parseInt).getName();


        Reader in = null;
        try {
          SimpleDateFormat sdf = new SimpleDateFormat(FILE_SUFFIX);
          String format = sdf.format(new Date());

          // Aipo用に変換するCSVファイルを引数の記述する
          in =
            new InputStreamReader(
              new FileInputStream(CYBOZULIVE_DIR + File.separator + fileName),
              INPUT_CSV_CHARSET);

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

          // 読み込んだCSVファイルのヘッダーの情報を元に条件分岐
          if (parser.getHeaderMap().containsKey("開始日付")) {
            System.out.print("カレンダーCSVファイル");
            calendarExport(divide, format);
          } else if (parser.getHeaderMap().containsKey("姓")) {
            System.out.print("メンバー名簿CSVファイル");
            memberExport(divide, format);
          }

        } catch (Exception e) {
          System.out.println("エラーが発生しました。");
          System.out.println(e);
        } finally {
          if (in != null) {
            in.close();
          }
        }
      }
    } catch (Exception e) {
      System.out.println("エラーが発生しました。");
      System.out.println(e);
    }
    System.out.println("処理が完了しました。");
  }
}