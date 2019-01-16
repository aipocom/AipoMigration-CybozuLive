# AipoMigration-CybozuLive

CybozuLive のデータを Aipo に移行するためのツールです。

- Aipo
  - [https://aipo.com](https://aipo.com/)

## 必須要件

**Java 8**

## 事前準備

### Jar ファイルのダウンロードと関連ディレクトリーの生成

以下のリンクから Jar ファイルをダウンロードしてください。

作業用ディレクトリーを作成します

    $ mkdir work
    $ cd work

データディレクトリーを作成します

    $ mkdir aipo-csv
    $ mkdir cybozulive-csv

### サイボウズ LIVE から CSV ファイルをエクスポート

1. [サイボウズ LIVE](https://cybozulive.com/login)にログインする
2. グループを選択し、「設定」を開く
3. 設定の項目の中から「エクスポート」を開く
4. Aipo でユーザーアカウントをインポートする場合は「メンバー名簿」、カレンダーの場合は「イベント」
   ＊Mac の場合は「形式と文字コード」を「標準」と「UTF-8」にしてダウンロードする。
   ＊Windows の場合は「標準」「Shift JIS」にしてダウンロードする。

5. サイボウズ LIVE でエクスポートしてきた CSV ファイルは作成した`cybozulive-csv`内に設置します。

### プログラムを実行

1.  カレントディレクトリーが`work`であること、work のディレクトリー内部に`aipo-migration-cybozuLive-[バージョン番号].jar`、`aipo-csv`、`cybozulive-csv`があり、`cybozulive-csv`内部に先ほどサイボウズ LIVE よりエクスポートした CSV ファイルが入っていることを確認する
    .
    ├── aipo-migration-cybozuLive-0.0.1.jar
    ├── aipo-csv
    └── cybozulive-csv
    ├── CalendarUtf-8.csv
    └── MemberUtf-8.csv

2.  以下のコマンドを実行する

        $ java -jar aipo-migration-cybozuLive-[バージョン番号].jar

3.  コマンドを実行すると、以下のような要求が出てくるので指示に従い該当するファイルの番号を入力する

        [1] CalendarUtf-8.csv
        [2] MemberUtf-8.csv
        [0] キャンセル
        CybozuLive から移行したいファイルを選んで番号を入力してください：

処理が完了すると`aipo-csv`ディレクトリー内に CSV ファイルが出力されます。

## 注意事項

### 実行できるユーザー

Aipo の**管理者権限**が必要になります。

### 生成されるファイルの数

Aipo では一度にインポートできるのが 50 件までとなっているので、サイボウズ LIVE 側でエクスポートされたものでレコードの数が 50 件を超えるファイルに関しては、Aipo にインポートするためにファイルが適宜分割されます。

### エクスポートする際の「文字コード」

サイボウズ LIVE より CSV ファイルをエクスポートする際の文字コードは Windows では「Shift JIS」、Mac では「UTF-8」でダウンロードしてください。ファイルを読み込み時に文字化け等が起こってしまい正常に変更がされません。
