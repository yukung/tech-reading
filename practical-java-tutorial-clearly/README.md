第１章　文字列の操作
===================

* 文字列に対する操作（Stringクラス）の基本メソッドの使い方
     * 調査
          * equals, equalsIgnoreCase, length, isEmpty
     * 検索
          * contains, startsWith, endsWith, indexOf, lastIndexOf, matches
     * 切り出し
          * charAt, substring
     * 変換
          * toLowerCase, toUpperCase, trim, replace, replaceAll
     * 分割
          * split
     * 整形
          * format
          * 書式指定文字列 (%dなど)の扱い
               * %[,0-+][n.m],[dsfb]
* StringBuilder, StringBuffer と速度の違い
     * EverNote に参考URLをまとめた -> （string, idiom）で検索
* 正規表現の基本
     * 正規表現書き取りブックや、オライリーの正規表現本でさらに深堀りする
     * split メソッドと正規表現の組み合わせ

第２章　コレクション
===================

* コレクションフレームワークの種類と特徴
     * List, Set, Map
     * 実装の違いによる具象クラスの選択について
* さらに深堀り
* ユーティリティメソッド
     * java.util.Collections
     * java.util.Arrays
* サードパーティのコレクションライブラリ
     * Apache Commons Collections
     * Google Guava Collections

第３章　様々な種類のクラス
=========================

* 型安全について
     * メリット
* ジェネリクス
     * 定義、使い方
     * 仮型引数、実型引数
     * 受け入れる実型引数の制限<? extends SuperClass>
* 列挙型
     * 定義と使い方
* インナークラス
     * static メンバクラス
     * 非 static メンバクラス
     * ローカルクラス
     * 匿名（無名）クラス
* ジェネリクスと列挙型を使った鍵付き金庫クラスの実装

第４章　その他の基本機能
=======================

Object クラスの基本機能

* インスタンスの文字列表現（toString() メソッド）
* インスタンスの等価判定（equals() メソッド）
     * 等価（equals）と等値／同値（==）の違い
          * 等値／同値 はオブジェクト参照の比較
     * 同値は、何を持って同一のものとみなすかを定義する必要がある。それが equals() メソッド
     * 自分で定義したクラスをコレクションクラスに格納して、それを削除する際など、「同じ値かどうかを判定する」ため、equals() メソッドが定義されたいないと予期しない動作をする可能性がある。
* インスタンスの要約（hashCode() メソッド）
     * HashSet, HashMap のようなコレクションでは、最初にオブジェクトのハッシュ値で同じ要素かを判定し、一致した場合に equals() で厳密に同じオブジェクトかどうかを判定する。そのため、hashCode() が実装されていないと、最初のハッシュ値の突き合わせの際に正しく検索できない。
     * Java では「すべてのオブジェクトは自身のハッシュ値を計算できるべきである」という考えをとっている。
     * ハッシュ計算は、Eclipse を使っていれば自動生成できる。
* インスタンスの順序付け（compareTo() メソッド）
     * インスタンスを並び替える時、順序の定義がないとそもそも並び替えることができない。
     * 順序の定義付けをするために、Comparable インタフェースを実装し、compareTo() メソッドをオーバーライドする。
          * 自分自身が引数のインスタンスよりも小さい時 -> 負の数
          * 自分自身と引数のインスタンスが等しい時 -> 0
          * 自分自身が引数のインスタンスよりも大きい時 -> 正の数
     * TreeSet, TreeMap は、自然順序付けで並び替えながら要素を格納する。この時 Comparable#compareTo を呼びながら格納しているため、Comparable インタフェースを実装していないクラスのインスタンスは格納できない。
* インスタンスの複製（clone() メソッド）
     * 単純な = 演算子による代入では、参照（ポインタ）がコピー（値渡し）されるだけで、インスタンスそのものがコピーされる（２つになるわけではなく、指しているインスタンスは同じもの。
     * インスタンスそのものをコピーしたい場合は、インスタンスを新たに作り、中身のフィールドの内容を全てコピーするという手続きが必要。
     * それを簡単に行うために、Object#clone メソッドがある。
          * Clonable インタフェースを実装する。
               * 実装が必要なメソッドが1つもないマーカーインタフェースで、clone() による複製に対応していることを明示するためだけに存在する。
          * Object#clone() メソッドをオーバーライドして、自分と同じ新しいインスタンスを生成し、フィールドの内容を全てコピーするように処理を実装する。
               * Object#clone() メソッドは、protected スコープで外部から呼び出せないため、オーバーライドするときに public スコープに定義し直す。
               * サブクラスにおけるオーバーライド時のアクセス修飾は、スーパークラスのそれと同じか、より緩いアクセス修飾に限定される。
     * ディープコピーとシャローコピー
          * シャローコピーは、clone() メソッド内で参照型のフィールドのコピーを行う際に参照値（ポインタ）のみを代入演算子でコピーする。このため、clone 先のインスタンスのフィールド値を変更すると、clone 元のインスタンスのフィールド値も変更されてしまう。
          * ディープコピーは、参照型のフィールドのコピーをさらにclone() して別の新しいインスタンスを作ってそれをコピーとするため、clone先の変更がclone元の変更に影響しない。
* プログラムの終了
     * System.exit() メソッド
          * 引数は int 型で、この数値がプログラムの終了コードとしてOSに通知される。
          * 多くのOSでは0を正常終了、0以外は異常終了を意味する。
          * エラーの原因ごとに異なる終了コードを返すようにすると、バッチ処理などで異常終了時に原因究明に役立てることができる。
* 外部プログラムの実行
     * ProcessBuilder クラスを使って、外部プロセスを操作できる。
          * コンストラクタの引数は可変長引数で、起動するプログラム、コマンド名、起動オプション、入力ファイルなどを指定できる。
          * ProcessBuilder#start 処理が実行され、その戻り値の Process インスタンスから、起動終了を待ったり、終了コードを得ることができる。
* システムプロパティの利用
     * JVM に関する情報が入っている文字列のMap（内容は一部）
          * java.version -> 実行中のJREバージョン
          * java.home -> 実行中のJavaのインストール先ディレクトリ
          * os.name -> 動作中のOSの名前
          * line.separator -> 動作中のOSの改行コード
          * user.name -> 実行したユーザーの名前
     * System.getProperty(key) で取得できる。
     * 改行コード
          * OSによってCR+LFだったりCRだったりLFだったりするのを、統一的に扱うためにline.separatorを使う。
     * システムプロパティの設定
          * System.setProperty() を使う。キー文字列は自由。
     * Javaプログラム起動時のシステムプロパティの追加
          * -Dオプションを使って指定する。
* OSの環境変数の取得
     * System#getenv() メソッドを使って取得する。

第５章　非標準ライブラリの活用
============================

* commons-lang の利用
     * EqualsBuilder
          * 全てのフィールドが等価ならインスタンスも等価とみなす機能。
          * EqualsBuilder#reflectionEquals()
     * HashCodeBuilder
          * ハッシュ値の生成を肩代わりしてくれる
          * EqualsBuilder#reflectionHashCode()
* ロガー
     * Log4J, java.util.logging, commons-logging, SLF4J
* オープンソースとライセンス
     * Public Domain, BSD License, GPL License
     * デュアルライセンス
          * GPL と商用ライセンスの組み合わせ
               * GPL なら、無償、ソースコード公開必須
               * 商用ライセンスなら、コード公開はしなくても良いが有償。
     * Creative Commons
          * BY（原作者の表示）, SA（二次創作時にライセンスを継承する）, ND（改変禁止）, NC（商用禁止）を組み合わせたもの。
          * BY, BY-SA, BY-ND, BY-NC, BY-NC-SA, BY-NC-ND の６つの組み合わせ。

第６章　外部資源へのアクセス
==========================

* 文字ストリーム
     * InputStreamReader, FileReader
     * OutputStreamReader, FileWriter
* バイトストリーム
     * FileInputStream
     * FileOutputStream
* ランダムアクセス
     * RandomAccessFile
* フィルタ
     * FilterReader, FilterWriter, FilterInputStream, FilterOutputStream のいずれかのクラスを継承
     * 単独で生成できず、すでに存在するストリームを接続先としてコンストラクタで指定して生成する。
     * フィルタを複数連結することもできる。
     * フィルタ側のclose処理をすれば、接続されている元のストリームもcloseされる。flushも同様。
     * 代表的なフィルタにBufferedReader, BufferedWriter, BufferedInputStream, BufferedOutputStream
          * 処理性能の向上
          * まとまった単位でデータを読める（readLine）
* クラスパスからファイルを読む
     * Class#getResourceAsStream("クラスパスからの相対ファイルパス");
     * 上記でクラスパス基点からのファイル読み込みが行える。
* ファイナライザで後始末しない
     * ファイナライザでcloseしても、いつ呼ばれるかわからないためファイルを壊す可能性があるから。

第７章　さまざまなファイル形式
============================

ネットワークやファイルを介して他のプログラムとやり取りするために、ファイルにはデータフォーマットを規定しておくことで、スムーズにやり取りできる。独自のデータフォーマットを定義しても構わないが、他のシステムとの連携の際にその仕様を考慮する必要が出てくる。

* CSV
     * 簡易なデータ形式で、操作も簡単。
     * String#split() や、StringTokenizer などを使って操作する。
     * データの見出しを含めることもできるが、データの位置が意味を持つため、人間には読みづらい。
     * データをネストできない。
     * 内部では1文字ずつデリミタかどうかを判断しながら読み取りするのと、改行の取り扱いなども注意を払う必要がある。
     * opencsv, OrangeSignal CSV などの汎用CSVライブラリを使用することを推奨する。
* プロパティファイル
     * JavaのAPIとしてサポートしているファイル形式。key と value で１データを表現する。
     * 人間が読みやすい。
     * データのネストもできないことはないが、key名をドットでつなぐような形になるので、深くなると見づらくなる。
     * Properties クラスで操作する。load(InputStream)で読み込み、store(OutputStream, Properties)で書き込み。
     * getProperty(key), setProperty(key, value) を使ってデータを操作する。
* XML
     * 汎用性が高くで表現力が高いデータフォーマット。
     * 多くの言語でサポートされているが、Javaでは標準で操作するAPIが提供されている。
     * データのネストも可能。
     * いくつかAPIがあり、APIごとに操作方法が多少違う。（JAXPなど）
          * ここは調べる必要がある。
     * データの操作手順（JAXP）
          * XML文書全体を取得（Document）
          * Document#getDocumentElement() でルート要素を取得
          * ルート要素の中から、タグ名を指定して要素を走査する。（getChildNodes() -> NodeList.item().getTagName() と 指定のタグ名が等しいかをループで検索する）
          * 要素が見つかったら、その値を取得（Element#getTextContent()）
* JSON
     * 最近はWeb上で主流のデータフォーマット
     * XMLと同じく表現力が豊かで、ネストもサポート。
     * XMLよりもデータ容量が少なく、ネットワーク負荷も低い。
     * Java8から標準のAPIが提供される予定だが、現状サードパーティのライブラリしかない。
* シリアライズ
     * データの形式を問わず、オブジェクトのインスタンスの状態をそのままバイト列にして保存する。
     * Serialzable インタフェースを実装する必要がある。
     * static フィールドは直列化されない。
     * transient 修飾子をつけたフィールドは直列化されない。
     * フィールドの情報全てを直列化する。
     * Stream#writeObject(obj), Stream#readObject で読み書きできる。
     * インスタンスのクラスが改変され、バージョンが変わった時にデシリアライズすると矛盾が生じる。それを防ぐためにシリアルバージョンUIDを定義しておく。

第８章　ネットワーク通信
=======================

* java.net.URLクラスを使った高レイヤーでの通信
    * SSL通信(https）は、java.net.URLConnection クラスで実現可能。
* java.net.Socketクラスを使った低レイヤーでの通信
* ServerSocket を使ったサーバプログラム
    * HTTP1.0のGETリクエストを送ってHTMLを取得する例

第９章　データベースアクセス
==========================

* JDBC API の説明。
* 主なAPI
    * DriverManager（接続準備）
    * Connection（接続や切断を行う）
    * PreparedStatement（SQL文を送信する）
    * ResultSet（結果表を扱う）
* JDBC API が JDBC Driver を通して、各社固有の実装を隠蔽してくれる。

第１０章　基本的な開発ツール
==========================

JavaDoc
-------

### よく使うオプション

* private
    * プライベートを含むすべてのメンバを出力
* d
    * ソースコードのディレクトリ指定
* locale
    * 言語の指定。ja など
* docencoding
    * 出力するHTMLの文字コード。utf-8 など
* charset
    * HTMLでの明示的な文字コード宣言。docencodingで指定したものと同じにする
* encoding
    * ソースコードの文字コードの指定。

### JavaDocタグ

* @author
* @version
* @param
* @return
* @exception
* @see
* @since
* @deprecated

javac コマンド
-------------

### 厳密なチェック

* -Xlint オプション

native2ascii
------------

     native2ascii [ -encoding 入力の文字コード ] input output

jar
---

* アーカイブの作成
    * -cvf
* アーカイブの展開
    * -xvf
* アーカイブの閲覧
    * -tvf

### マニフェストファイル

* MANIFEST.MF

./META-INF/MANIFEST.MF に作成する。

     jar -cvfm JARファイル名 マニフェストファイル名 ファイル群

で作成できる。マニフェストファイルの中身は、

     Manifest-Version:1.0
     Main-Class:

メインクラスを指定することにより、

* java コマンドでの起動時にメインクラスの指定が不要になる
     * java -jar hoge.jar で起動できる
* JARファイルをダブルクリックするだけでプログラムを起動できる
     * Windows など一部のOS

java コマンド
------------

### よく使われるオプション

* -cp, -classpath
* -jar
* -verbose:gc
     * ガベージコレクションが発生する度に情報が表示される
* -Xms 最小割り当てメモリ
     * JVM に割り当てるヒープの最小量を指定する
* -Xmx 最大割り当てメモリ
     * JVM に割り当てるヒープの最大量を指定する

### ヒープとスタック

* OutOfMemoryError が起こる場合は、-Xmx で多めのメモリを割り当てるようにする

### ガベージコレクション

* ヒープ領域の中から使わないインスタンスを消去する
* この処理自体が非常に重いので、全体の処理が一時中断することがある
* ヒープ領域を大きく取り過ぎると、GCの処理に時間がかかるようになり、処理効率が低下する
* GCがいつ動作を開始するかは制御できない
* GCが動作している間、他の計算処理は完全に停止する
     * Stop the world
* メモリリークを防ぐ
     * 外部リソースの閉じ漏れ
     * スコープが外れるようにインスタンスの生存時間が短くなるようにコーディングする
          * 生成したコレクションにインスタンスを格納したままにしない、など


第１１章　単体テストとアサーション
================================

テストの捉え方は、「工程」で分ける捉え方と「役割」で分ける捉え方がある。

工程で分ける捉え方
-----------------

* 単体テスト
* 結合テスト
* システムテスト

役割で分ける捉え方
-----------------

* 顧客要件テスト（Custumer Testing）
* 品質保証テスト（QA Testing）
* 開発者テスト（Developer Testing）

単体テストのやり方
-----------------

テストしたい対象のクラスを動かすためだけに作るクラスをテストクラスやテストドライバと呼ぶ。

JUnit での単体テスト
-------------------

コードを参照。

アサーション
------------

     assert <条件式>;

### AssertionError が例外ではなくエラーである理由

そのまま処理を継続すると、データを破壊したり致命的な障害につながるため。

### アサーションを有効にする

アサーションはデフォルトでは無効となっている。有効にするには、java コマンドに -ea オプションを与えることで有効になる。

### アサーションの注意点

* アサーションの OFF 時に問題となるコードを書かない
     * アサーションの条件式に変数をインクリメントするような式を記述するなど
     * アサーションのON/OFFで状態が変わるのでNG
* 単体テストに変わるものではない
     * 絶対に起こるはずがない、起こってはならない異常事態に備えるための保険

契約による設計
-------------

バートランド・メイヤーの契約による設計。

あるメソッドについて、

* 事前条件
     * 呼び出し時に、渡されてきた引数が正しいか。
* 事後条件
     * 終了時に、返そうとしている戻り値が正しいか
* クラス不変条件
     * 呼び出し時及び終了時に、インスタンスの内部状態に矛盾がないか

バートランド自身が開発した Eiffel というプログラミング言語では、メソッド、クラスを宣言する際にこれら３つの条件を宣言の一部として記述することで、メソッドを呼ぶ側と呼ばれる側で順守すべき責任を明確にできる。
Java で、アサーションをこういうような形で使うことで、契約による設計をしていくこともできる。


第１２章　メトリクスとリファクタリング
====================================

品質の見える化
-------------

品質向上に取り組む場合、その品質を見える化することが重要。その方法としてメトリクスを取ることを考える。プログラムやクラスの使われ方に全部でどれだけのパターンがあって、テストで試したのはその内の何パターン化を明らかにするのが、メトリクス。例えば、テストカバレッジ。

### コードのカバレッジ

* 命令網羅率
     * テストで通過した命令数 / コード全体の命令数
* 分岐網羅率
     * テストで通過した分岐経路数 / コード全体の分岐経路数

カバレッジを取るツールとして、Cobertura がある。

Cobertura では、ant や Maven を使って、テストクラスに通過用のログを仕込み、仕込んだ後のクラスに対してテストを実行し、その結果を .ser ファイルに記録する。.ser ファイルをパースして html, xml などに変換することで、メトリクスの収集が可能。

Jenkins などからフックすることも出来る。

メトリクスは、あくまでも現在のコードベースに対してのテスト指標であり、そもそものコードの漏れなどに対しては無力である。カバレッジを100%にすることが目的ではないはずなので、数字に束縛されて本質を見失わないようにすることが肝要。

### その他のメトリクス

* LOC
* CC
* WMC

#### LOC

Line of Code の略で、コードの行数のこと。規模を大まかに把握することが出来る。ただし、オブジェクト指向のプログラムにおいては、単純な行数がプログラムの規模を正しく表すとは限らないので、あくまでも大まかな指標としてみるのが良い。

#### CC

Cyclomatic Complexity の略で、トーマス・マッケーブという人が開発したメトリクス。マッケーブの循環的複雑度とも言う。ある特定のメソッドの中身がどれだけ複雑化を示す1以上の数値で、大きいほど複雑であることを示している。

具体的には、 if による分岐や for によるループが多く使われるほど高い数値を示す。

| CC    | 複雑さの程度         | 修正時バグ混入率 |
| ----- | ------------------ | ------------- |
| 〜10  | 良い構造             | 約5%          |
|10〜30 | 普通                | 約20%          |
|30〜50 | 構造的問題がある恐れ   | 約30%         |
|50〜   | テスト、修正ができない | 約40%          |

#### WMC

Weighted Method Per Class の略で、あるクラスに含まれる全メソッドの CC を合計した値で、クラスの複雑度を示す。WMC が以上に突出しているクラスは、複数に分割することを検討するべきかもしれない。
なお、クラスに含まれる全メソッドの CC を足さずに平均してクラスの複雑度を算出することもある。Cobertura の HTML レポートでは、この方法でクラスやパッケージの複雑度を Complexity 欄に表示している。

リファクタリング
--------------

プログラム開発を続けていくと、技術的負債が積み上がっていく。これを解消するのがリファクタリング。

* 重複部分を1つにまとめる
* メソッドやクラスに分割する
* 外部ライブラリや新しいバージョンの文法を利用する
* コメントなどを適正につける

リファクタリングによるデグレを起こさないために、自動化されたユニットテストが必要になる。テストがあれば、リファクタリングを積極的に行うことが出来る。

* 納期直前はリファクタリングのメリットが得られにくい
* むやみにやらない、やりすぎない

コードの静的解析
--------------

むやみやたらにリファクタリングを行うのではなく、どこが悪いのか、何が悪いのかを的を絞って行う方が効率的。そのための手段として、静的解析がある。

トム・デマルコの言葉に、「計測できないものは制御（改善）できない」というものがある。何かを改善したい場合は、現実的に手間を掛けられる範囲で効果的に計測を行う。

### FindbBugs

FindBugs は、クラスファイルを解析し、コード内に含まれる「バグの原因になりそうな危険な記述」を探しだしてくれるツール。

* 文字列を「==」で比較している
* データベース接続が閉じられていない恐れがあるコード

* Ant, Maven, Gradle
* Eclipse, NetBeans, Intellij IDEA

などに対応している。

### Checkstyle

Checkstyle は、「Javadocコメントが付いていない」「インデントがおかしい」「クラス名が大文字で始まっていない」といったコーディングスタイルに関するルール違反を検出するためのツール。

* Ant, Maven, Gradle
* Eclipse, NetBeans, Intellij IDEA

などに対応している。

第13章　コードとタスクの共有
========================

* 分業は最も基本的かつ有力な効率改善の方法。
* ただし、連携がうまくいかないとかえって非効率になる。

コードの共有
----------

* SCM を使ってソースコードをチームで共有し、変更履歴の共有や並行作業を可能にする。
    * CVS, Subversion, Git…

タスクの共有
----------

* BTS を使って、作業履歴を共有できるようにすること。
* できるだけ最新の正確な状況を反映するようにチケットを更新すること。

第14章　アジャイルな開発
====================

UML
----

* 開発者同士のコミュニケーションの曖昧さを排除するために、図を使う。
* そのための手段としてのUML。

開発プロセス
----------

* ウォーターフォールで最近の早い動きに対応できない
* 俊敏に対応するために、アジャイル。決して早く作るためではない。

アジャイル開発手法
----------------

### コードと人と変化を重視する

* アジャイルソフトウェア開発宣言
* 2〜4週間程度の極めて短期間で工程を反復するスパイラル型開発プロセスを実践する。
* 1回の工程反復期間のことをイテレーションやスプリントという。
* 様々なプラクティスがある。

### アジャイルプラクティス

* デイリースタンドアップ（朝会）
* レトロスペクティブス（ふりかえり）
* ペアプログラミング
* テスト駆動開発
* 継続的インテグレーション（CI）

第15章　設計の原則とデザインパターン
===============================

6つの設計原則
------------

* DRY
* PIE
* SRP
* OCP
* SDP
* ADP

### DRY(Don't Repeat Yourself)

* 同じ事を何度もするな
* コピー・ペーストしようと思った時は、手を止める
* 重複を排除する

### PIE(Program Intently and Expressively)

* 明確かつ表現豊かに記述せよ
* 適切な名前を選ぶ
* マジックナンバーに名前をつける
* 複雑な処理に名前をつける
* 名前が適切だと、コードを読む際に予測が効く

### SRP(Single Responsibility Principle)

* 単一責任原則
* 1つのクラスに1つの責務だけを持たせる

### OCP(Open-Closed Principle)

* 開放閉鎖原則
* 拡張に対して開いていて、変更に対して閉じている
* 将来自由に拡張できて、既存部分の変更は不要である設計

### SDP(Stable Dependencies Principle)

* 安定依存の原則
* なるべく安定的なクラスに依存するようにする

### ADP(Acyclic Dependencies Principle)

* 非循環依存の原則
* 循環または相互に依存しないようにする

SOLID 原則
----------

ロバート・C・マーチンがまとめた5原則。

* SRP
* OCP
* LSP（リスコフの置換原則）
* ISP（インタフェース分離の原則）
* DIP（依存性逆転の原則）

デザインパターン
--------------

* GoF パターン

第16章　スレッド
==============

* run()を実行し終えると自動消滅する
* stop()の利用はご法度
    * suspend()やdestroy()も同様
* 全スレッドの終了を持ってJVM自体は終了する
* join()メソッドで別スレッドの終了を待つことができる
* OS によって動作に違いが生じる可能性がある
* 例外は main スレッドに伝播しない
* 同時に1つの変数を利用するとデータが壊れる
    * スレッドセーフな設計が必要

Concurrency Utilities の利用
---------------------------

java.util.concurrent パッケージに、スレッド処理を簡単かつ安全に行うための高水準 API が提供されている。

* Executor による高水準スレッドジョブ実行
    * Thread クラスを意識せずに並列プログラミングが可能
    * スレッドプールなどの仕組みも持つ
* スレッドセーフなコレクションの実装
    * ConcurrentHashMap など
* シンクロナイザやロックに関する実装
    * CountDownLatch, CyclicBarrier, Exchanger, Semaphore
    * java.util.cuncurrent.locks
