# サーブレット

## サーブレットとは

サーブレットコンテナの上で動くプログラムをサーブレットアプリと呼ぶ。

サーブレットプログラミングとはサーブレットアプリを開発すること。サーブレットアプリは極論すると、HTTPリクエストを入力としてHTTPレスポンスを出力とするプログラム。リクエストURLごとに様々な処理を実行する。サーブレットアプリを１つのクラスのように見立てると、リクエストURLはメソッドに相当し、レスポンスが返り値に相当する。

### ビルドとデプロイ

サーブレット開発では、以下の２つの工程が必要になる。

* ビルド
    * ソースファイルを javac でコンパイルして、クラスファイルを生成する工程
* デプロイ
    * 生成したクラスファイルとその他実行に必要なファイルをサーブレットコンテナがロードできるディレクトリにコピーする工程

この作業を自動化するためのツールとして、ant がある。開発では ant を使いながらビルドとデプロイを繰り返しながら進めていく。

ant の処理を定義するのが build.xml というビルドファイル。このファイルにターゲットとタスクという定義を記述することで、ビルドとデプロイを自動的に行うようにすることができる。

### マッピング

リクエストURLから実行するサーブレットクラスを選択することをマッピングと呼ぶ。マッピングの規則は、web.xml というファイルに定義していく。

Tomcat がホストする各サーブレットアプリを**コンテキスト**と呼び区別する。リクエストURLはまず`コンテキストパス/マッピングパス`に分解される。

    http://localhost:8080/myservlet/MyServlet

のURLで myservlet の部分がコンテキストパス。直感的にはコンテキストパスはアプリ名。web.xml のマッピングで、コンテキストパスより後ろのURLをサーブレットクラスに対応づける。

#### マッピングのURLパターン

`<url-pattern>`に記述できるURLのパターンとマッチの優先順を示す。

1. 完全一致
2. 拡張子ルール一致
3. 最長の前方一致
4. デフォルト

|記述|説明|例|
|----|----|--|
|パス|完全一致パスのルール|/my|
|/* で終端するパス|前方一致パスのルール|/my/*|
|\*.拡張子|拡張子によるマッピングルール|\*.jsp|
|/の1文字|デフォルトルール|/|

#### Tomcat のデフォルト web.xml

各サーブレットアプリごとの web.xml を先に読み、URLパターンが一致しない場合は Tomcat 自体のデフォルト web.xml を参照する。

#### WEB-INF と META-INF

WEB-INFとMETA-INFの2つのディレクトリの下のファイルは、URLのパターンがマッチしてもファイルの中身を返さない決まりになっている。（サーブレットの規格）つまり、WEB-INFディレクトリの下に配置した web.xml の中身がWebブラウザに見えることはない。クラスファイルの中身もそのまま外部からダウンロードできないようになっている。

## サーブレットAPI

サーブレットAPIのパッケージは次の2つ。

* javax.servlet
* javax.servlet.http

`Javax.servlet` パッケージにはHTTPに依存しないクラスやインタフェースがある。クラスのいくつかは抽象基底クラス。

`javax.servlet.http` パッケージにはHTTPに依存したクラスやインタフェースがある。多くのクラスやインタフェースが `javax.servlet` パッケージのクラスやインタフェースを継承している。

### サーブレットクラス

サーブレットクラスは `javax.servlet.http.HttpServlet` を拡張継承した具象クラス。サーブレットアプリの開発者は自作のサーブレットクラスを作成する。フレームワークが抽象基底クラスを用意し、フレームワークを利用する開発者が拡張継承した具象クラスを作成する技法はフレームワークで一般的。サーブレットの世界ではサーブレットコンテナがフレームワーク。

Spring Framework などはサーブレットコンテナの上に乗るフレームワーク。上位のフレームワークをその役割を強調してMVCフレームワークと呼ぶこともある。

サーブレットオブジェクトの生成はサーブレットコンテナ（フレームワーク）の役割。サーブレットコンテナはサーブレットオブジェクトに対して抽象基底クラスのメソッドを呼ぶ。結果として開発者が具象クラスで抽象基底クラスのメソッドをオーバーライドすると、サーブレットコンテナがそのメソッドをコールバックする関係になる。

`HttpServlet` クラスは、全てのメソッドにデフォルト動作の実装を持つ。具象クラスはデフォルト動作を変更したいメソッドだけをオーバーライドする。

開発者は、自作のサーブレットクラスでdoGetやdoPostのように接頭辞doのついた名前のメソッドをオーバーライドする。doメソッドはHTTPのメソッドに対応しており、リクエストがGETメソッドであれば、doGetメソッドが呼ばれる。doメソッドをオーバーライドしない場合、doメソッドが呼ばれると該当HTTPメソッドをサポートしていない旨のエラーをWebブラウザに返します。つまりHTTPエラーを返す実装がデフォルト実装。

doメソッドのパラメータ引数は`HttpServletRequest`オブジェクトと`HttpServletResponse`オブジェクト。doメソッドの基本的な動作は、`HttpServletRequest`オブジェクトからリクエスト情報を読み取り、`HttpServletResponse`オブジェクトにレスポンス情報を書きだす。概念的には`HttpServletRequest`オブジェクトが入力元で`HttpServletResponse`オブジェクトが出力先。

MVCアーキテクチャの下ではレスポンス処理をJSPに任せるのが普通。この場合のdoメソッドの基本構造は`HttpServletRequest`オブジェクトからリクエスト情報を読み取り、JSPに`HttpServletResponse`オブジェクトを渡してレスポンス処理をまるなげする。サーブレットの世界では処理の丸投げを**フォワード**と呼ぶ。

#### initメソッドのオーバーライド

doメソッド以外にオーバーライドすることのあるメソッドはinitメソッド。サーブレットコンテナはサーブレットオブジェクト生成後にinitメソッドをただ1度だけ呼ぶ。各サーブレットオブジェクトはただ一つしか生成されないことに注意する。開発者はinitメソッドをオーバーライドすることで最初に1度だけ行いたい処理を書くことができる。

#### サーブレットクラスの排他制御

サーブレットコンテナは、サーブレットオブジェクトをクラスごとにただ1つだけ生成する。サーブレットは同時に複数のクライアントから接続を受ける。一般に同時に行うリクエスト処理に別々のスレッドを割り当てる。つまり、複数のWebブラウザが同じURLに同時にアクセスすると複数のスレッドが同時に同じサーブレットオブジェクトのdoメソッドを呼び出す。

サーブレットクラスがインスタンスフィールドを持つ場合、排他制御が必要。しかし、一般論としてサーブレットクラスはインスタンスフィールドを持つべきではない。リクエストごとに持つべき状態は`HttpServletRequest`オブジェクトに持たせる。セッションごと（直感的にはログインユーザごと）に持つべき状態は`HttpSession`に持たせる。

上記の指針を守ると、サーブレットクラスに排他制御は不要になる。

doメソッドの引数に渡る`HttpServletRequest`オブジェクトと`HttpServletResponse`オブジェクトはリクエストごとに独立して生成される。これらを他のスレッドと共有することはない。このため、これらには排他制御は不要。

### リクエスト処理

HTTPリクエストから得られる主な情報と取得メソッドを以下に示す。

|リクエストの構成要素|対応メソッド|
|--------------------|------------|
|リクエストURL|`getRequestURL`など|
|クエリパラメータ|`getParameter`や`getParameterValues`など|
|リクエストボディ（=ポストデータ）|`getInputStream`や`getReader`など|
|リクエストヘッダ|`getHeader`や`getHeaders`など|

#### リクエストURL

リクエストURLの情報を取得するメソッドは複数存在する。

|メソッド名|説明|返り値の具体例|
|----------|----|--------------|
|`getContextPath`|コンテキストパス|/appname|
|`getServletPath`|サーブレットクラスのパス|/doJob|
|`getPathInfo`|拡張パス|/extra|
|`getQueryString`|クエリパラメータ|id=foobar&x=y|
|`getRequestURI`|URLのパス部分|/appname/doJob/extra|
|`getRequestURL`|クレリパラメータを除くURL全体|http://localhost:8080/appname/doJob/extra|

リクエストURLのパス部分は次のように分解される。

    http://localhost:8080/コンテキストパス/サーブレットクラスのパス/拡張パス…?クエリパラメータ

#### クエリパラメータ

クエリパラメータはURLの?文字移行に現れる文字列。リンク先URLで指定したり、あるいはフォーム送信で生成することも出来る。

Webブラウザで利用者からの入力を受け付けるには、一般にフォームと呼ばれるHTML要素を使い、フォームの入力項目をGETメソッドまたはPOSTメソッドで送信される。

フォームの入力項目をGETメソッドで送信すると、入力項目はリクエストURLのクエリパラメータになる。

#### GETとPOSTメソッドの使い分けの指針

* GETメソッドはWebサーバから情報を得る場合に使う
    * パラメータが検索条件となったURLとなり、リンクとして利用することができる
    * ただし、ログイン処理にGETメソッドは使わない（パスワードが丸見えになってしまうため）
* POSTメソッドはWebサーバ上の状態を変更するためにWebサーバにデータを送信するために使う

#### クエリパラメータ用メソッド

クエリパラメータの全体は`getQueryString`メソッドで取得できる。通常、これで得られる文字列を自分で解析する必要はない。`HttpServletRequest`オブジェクトが内部で解析して、クエリ名とクエリ値のペアを管理しているため。

クエリパラメータはURL固有のエンコード処理が施されているが、内部解析がデコード処理を隠蔽する。クエリパラメータを取得するメソッドを次にまとめる。

|メソッド定義|説明|
|------------|----|
|`String getParameter(String name)`|クエリ名からクエリ値を取得。存在しない場合、null|
|`Stirng[] getParameterValues(String name)`|クエリ名から複数のクエリ値を取得。存在しない場合、null|
|`Enumeration getParameterNames()`|クエリ名の一覧を取得。クエリが1つも存在しない場合、空のEnumeration。コレクション型を使うと返り値の型は`Set<String>`相当|
|`Map getParameterMap()`|キーがクエリ名、値がクエリ値の集合のマップを取得。コレクション型を使うと返り値の型は`Map<String, String[]>`相当。|

`getParameterValues`メソッドから想像できるように、同じクエリ名に対して複数のクエリ値が存在しえる。

http://localhost:8080/appname?foo=bar1&foo=bar2 のような場合。

クエリ値は常に文字列で得られる。意味的に数値を送信する場合も、ネットワーク上は文字列になるのでメソッドの返り値の型は`String`。必要に応じて数値に変換するのは開発者の責任。

#### フォームのPOSTデータ

フォームの入力項目をPOSTメソッドで送信した場合、通称、POSTデータと呼ぶ。HTTPの観点で見るとPOSTデータはヘッダ部とボディ部で送信される。

HTTPボディ部がフォームのPOSTデータの場合、上記のクエリパラメータを取得するメソッドを使える。`HttpServletRequest`が内部でHTTPボディ部を解析して、クエリ名とクエリ値のペアにするため。

つまり、フォーム送信に関しては、`HttpServletRequest`のクエリパラメータ処理がGETメソッドとPOSTメソッドの違いを隠蔽する。

#### HTTPのボディ部

HTTPのボディ部（POSTデータ）は、フォーム送信に限定するものではない。HTTPボディ部の現実的な形式は次の3パターン。

* HTMLフォームから送るPOSTデータ（フォームデータ）
* ファイルアップロードによるPOSTデータ（ファイルアップロードデータ）
* その他（バイト列データ）

一般的なWebブラウザが送信できるHTTPボディの形式はフォームデータとファイルアップロードデータの2種。それ以外の形式はWebサービスなど他の用途に使うことがある。その他の形式は開発者が自分でHTTPボディ部を解釈する必要がある。

#### HTTPのボディ部のストリーム処理

HTTPボディ部のデータ取得には`HttpServletRequest`の`getInputStream`メソッドもしくは`getReader`メソッドを使う。バイトストリームとして扱う場合は`getInputStream`メソッド、文字ストリームとして扱う場合は`getReader`メソッドを使う。

`ServletInputStream`クラスは`InputStream`クラスを拡張継承したクラス。

HTTPボディ部のデータ取得にはデータ長を取得する`getContentLength`メソッドがあるが、HTTPの構造上、データ長が不明な場合がある。（Content-Lengthヘッダは必須ではないので）データ長が不明な場合、`getContentLength`メソッドは-1を返す。どんな場合も確実にHTTPボディ部を読み取るには、ストリームからデータを全て読み取る処理が必要。

理屈上はHTTPボディ部のデータをストリームとして読み取り、自前で解釈可能。現実的にはライブラリに処理を任せることを勧める。ファイルアップロードデータに関しては次の節に説明を譲り、その他のPOSTデータのフォーマットは用途にいよって様々。WebサービスではXMLデータをHTTPボディ部で送信することが多々ある。この場合も自分でストリーム処理せず、適切なライブラリに処理を任せることを推奨する。

#### リクエストヘッダ

リクエストヘッダ情報の読み取りメソッドは以下。

|メソッド定義|説明|
|------------|----|
|`String getHeader(String name)`|ヘッダ名からヘッダ値を取得。ヘッダが存在しない場合、null|
|`int getIntHeader(String name)`|ヘッダ名からヘッダ値をintで取得。ヘッダが存在しない場合、-1。intに変換できない場合、NumberFormatException例外が発生。|
|`long getDateHeader(String name)`|ヘッダ名からヘッダ値を時刻のエポック値で取得。ヘッダが存在しない場合、-1。エポック値に変換できない場合、IllegalArgumentException例外が発生。|
|`Enumeration getHeaderNames()`|ヘッダ名の一覧を取得。ヘッダが1つも存在しない場合、空のEnumeration。コレクション型を使うと返り値の型は`Set<String>`相当|
|`Enumeration getHeaders(String name)`|ヘッダ名から複数のヘッダ値を取得。ヘッダが存在しない場合、空のEnumeration。コレクション型を使うと返り値の型は`List<String>`相当|

ヘッダ名には大文字小文字の区別がない。（クエリ名は大文字小文字の区別がある）ヘッダ値を文字列以外で取得するメソッドがある。HTTP上はヘッダ値はすべて文字列で送信しているので、これらのメソッドは内部で文字列から数値や日付に変換している。変換処理を自前で書くよりは適切なメソッドで変換処理を隠蔽する。

リクエストヘッダの読み取りは、サーブレットコンテナやフレームワークに隠蔽することが多く、実際に使う機会はそれほど多くない。リクエストヘッダの有無はWebブラウザに依存する部分が多いため、リクエストヘッダに依存するサーブレットアプリは現実的ではないから。

### ファイルアップロード

ファイルアップロード時の送信データは内部動作が異なるため、パラメータ取得のメソッドではデータを取得できない。ファイルアップロードのデータを取得するにはライブラリを使うことを推奨する。

代表的なライブラリは Apache Commons の FileUpload ライブラリ。

### レスポンス処理

レスポンス処理とは `HttpServletResponse` オブジェクトに対してレスポンス出力を行うこと。サーブレットクラスの仕事を端的に言えばリクエスト処理を入力としてレスポンス処理を出力すること。

HTTPレスポンスは、「**レスポンスステータス**」「**レスポンスヘッダ**」「**レスポンスボディ**」の3つの構成要素から成る。`HttpServletResponse`はそれぞれに対応するメソッドを持つ。

|レスポンスの構成要素|対応メソッド|
|--------------------|------------|
|レスポンスステータス|`setStatus`など|
|レスポンスヘッダ|`setHeader`や`addHeader`など|
|レスポンスボディ|`getOutputStream`や`getWriter`など|

#### レスポンスステータス

レスポンスステータスは`200 OK`や`404 Not Found`などがよく知られている。200や404の数字の部分がステータスコードで、HTTPの規格で数値と意味が決まっている。`setStatus`メソッドは引数でint型のステータスコードを与える。ステータスコードは`HttpServletResponse`のクラスフィールドで定数定義されている。

```
#!Java
// ステータスコードの定数定義（一部抜粋）
public static final int SC_OK = 200;
public static final int SC_MOVED_TEMPORARILY = 302;
public static final int SC_UNAUTHORIZED = 401;
public static final int SC_FORBIDDEN = 403;
public static final int SC_NOT_FOUND = 404;
```

現実的にはサーブレットアプリで`setStatus`メソッドを使う場面はあまりない。なぜなら`setStatus`メソッドを呼ばない場合、自動的に200の成功ステータスコードになるため。

200以外のステータスコードを返すための特別に用意されたメソッドがある。

* `sendRedirect`
    * リダイレクト処理を行う
* `sendError`
    * 引数指定したステータスコードでエラーページを返す

`sendError`メソッドで明示的にエラーページを返すことができる。`sendError`メソッドの濫用は避けるべき。なぜならエラーページを返しても利用者に利することは殆ど無いから。例えばフォーム入力でパラメータが足りない場合、エラーページを返すのではなく、利用者に適切なフィードバック（不正な入力項目の合ったフィールドを明示するなど）のある画面を返すほうが望ましい。

#### レスポンスヘッダ

レスポンスヘッダはヘッダ名とヘッダ値のペアで指定する。リクエストヘッダ同様、レスポンスヘッダの処理もサーブレットコンテナやフレームワークで暗黙に処理することがほとんど。Webブラウザがレスポンスヘッダをどう解釈するかWebブラウザ依存が大きく、レスポンスヘッダの利用は汎用性が低いため。

`setContentType`メソッドはレスポンスボディのフォーマットを指示するレスポンスヘッダを返す。`Content-Type`ヘッダの値を指定する。HTML以外のレスポンスを返す場合、指定するほうが利用者の利便性が上がる。

#### レスポンスボディ

レスポンスボディの送信はストリームに対する出力で行う。バイトストリームと文字ストリームそれぞれの取得メソッドがある。

出力ストリームへの書き込みはそのままレスポンスボディとして送信される。直感的には、ストリームにHTMLを書き込むとWebブラウザの画面に表示される。

後述するように適切なMVCアーキテクチャに従うサーブレットアプリでは、レスポンス処理、特にレスポンスボディ部の出力処理をサーブレットクラスが行うべきではない。JSPに任せるのが筋。この場合、出力ストリームの取得メソッドを明示的に呼ぶ出すことはほとんどない。

### フォワード処理

フォワード処理とは別のサーブレットクラスやJSPに処理を丸投げすること。

ただし、サーブレットオブジェクトから他のサーブレットオブジェクトのdoメソッドを呼ぶのは禁止。たとえできたとしても、サーブレットクラスのオブジェクト生成を明示的に行うのは禁止、サーブレットオブジェクトの生成はサーブレットコンテナの仕事。

```
#!java

// 決して行なっては行けないフォワード処理
public class MyServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        AnotherServlet another = new AnotherServlet();    // 生成してはいけない
        AnotherServlet another = (AnotherServlet)getServletContext().getAttribute("another");    // アプリケーションコンテキストからもダメ
        another.doGet(req, res);    // 他のサーブレットのdoメソッドを呼ぶのは禁止
    }
}
```

原則として、サーブレットオブジェクトから別のサーブレットオブジェクトを参照することは禁止と考える。その代わり、次のフォワード処理をする。

#### フォワード処理の方法

処理をフォワードするには`RequestDispatcher`オブジェクトを使う。`RequestDispatcher`はフォワード先サーブレットオブジェクトのdoメソッドの呼び出しを隠蔽する。

`RequestDispatcher`オブジェクトは次の3つの手段で取得できる。

|取得メソッド|説明|
|------------|----|
|`ServletContext`オブジェクトの`getRequestDispatcher`メソッド|パスから`RequestDispatcher`オブジェクトを取得。コンテキストルートからの絶対パス指定。もっとも一般的な取得手段。|
|`ServletContext`オブジェクトの`getNamedDispatcher`メソッド|名前から`RequestDispatcher`オブジェクトを取得。|
|`HttpServletRequest`オブジェクトの`getRequestDispatcher`メソッド|パスから`RequestDispatcher`オブジェクトを取得。相対パス指定もできる。|

`HttpServletRequest`オブジェクトの`getRequestDispatcher`メソッドは`ServletContext`オブジェクトの同名のメソッドのラッパー。このため、`ServletContext`オブジェクトの`getRequestDispatcher`メソッドの利用を推奨する。doメソッド内で`ServletContext`オブジェクトは`getServletContext`メソッドで取得できる。

`RequestDispatcher`オブジェクトに対して`forward`メソッドを呼ぶとフォワード処理できる。

結果的にフォワード処理は次のように書ける。

```
#!java

// getRequestDispatcher を使うフォワード処理（ただしフォワード先はJSPであるべき）
public class MyServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // リクエスト処理など
        getServletContext().getRequestDispatcher("/Another").forward(req, res);    // レスポンス処理を移譲
    }
}

// getNamedDispatcher を使うフォワード処理（ただしフォワード先はJSPであるべき）
public class MyServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // リクエスト処理など
        getServletContext().getNamedDispatcher("my").forward(req, res);    // レスポンス処理を移譲
    }
}
```

動作上、サーブレットオブジェクトから別のサーブレットオブジェクトへフォワード処理も可能。しかし、これは悪い習慣。サーブレットクラス間に依存関係を持たせることは避けるべき。フォワード処理はサーブレットクラスからJSPのみに限定することを勧める。

### インクルード処理

フォワード処理が処理の全てのを完全に丸投げするのに対し、インクルード処理は処理の一部を任せる機能。

HTML内のヘッダやフッタのような共通部分をインクルード処理に任せることが一般的。サーブレットクラスの中からインクルード処理を行うことは悪い習慣なので気をつけること。

JSP内では普通に行う。

### リダイレクト処理

リダイレクト処理とは、特別なステータスコードを指定するレスポンス。リダイレクトのステータスコードを受けたWebブラウザは、指定URLにもう1度リクエストし直す。

doメソッドからリダイレクト処理をするには`HttpServletResponse`オブジェクトの`sendRedirect`メソッドを呼ぶ。

#### リダイレクト処理とフォワード処理

リダイレクト処理はフォワード処理と混同されることがあるが、全く別の仕組み。リダイレクト処理はHTTPの通信が余計にある。サーバ側の負荷も少しかかり、利用者の体感する速度も若干低下する。この速度低下を嫌い、フォワード処理でリダイレクト処理を代用することを考える人がいるが、この2つはそもそも目的が異なるので、リダイレクトすべき時にフォワード処理で代用することは間違い。

#### リダイレクトの指針

ブログや掲示板のようなWebアプリを考える。

保存処理の後、利用者に文書一覧画面を見せる時、フォワード処理で画面遷移することは間違い。保存処理は一般にPOSTメソッドであり、リクエストURLは保存処理に対応するURL。フォワード処理にしてしまうと、利用者のWebブラウザ上では保存処理のURLに対して文書一覧画面が表示されることになってしまい、ブックマークやメールでこのURLを送られてしまうと、保存用URLに直接アクセスされてしまうことになってしまう。

この問題を避けるために、リダイレクトを使う。

保存処理の後、文書一覧画面を表示するためのリクエストURLにリダイレクトする。Webブラウザはリダイレクトにより文書一覧画面用のURLにアクセスして文書一覧を表示する。この時のWebブラウザは適切なもの。ブックマークにしてもリンクにしても問題ない。

### 状態管理

MVCアーキテクチャに従うと、サーブレットクラスからJSPへ処理をフォワードすることが一般的になるが、その際のサーブレットクラスの処理結果をJSPに渡す方法としては、メソッドのパラメータ引数は`HttpServletRequest`と`HttpServletResponse`で既に決まってしまっているため、パラメータではなく**サーブレットクラスとJSPの間で共有可能なオブジェクトに属性を持たせる**という方法を用意している。

属性は概念的にはマップのような機能。文字列をキーとして、任意のオブジェクトを値として持てる。コレクション型を使って説明すると、属性は`Map<String, Object>`に相当する。`HttpServletRequest`クラスに属性の設定・取得メソッドがあり、これはサーブレットクラスからでもJSPからでも利用できる。

サーブレットクラスでは`HttpServletRequest`クラスの`setAttribute`メソッドを使い任意のオブジェクトを属性としてセットしておき、JSPの方で同じ属性名を使い`getAttribute`メソッドを呼び出すと、属性値としてオブジェクトを取得することができる。

属性値の型は`Object`なので任意のオブジェクトを指定できる。属性値には Java Beans オブジェクトもしくは文字列をキーとしたマップオブジェクトが便利。なぜなら、JSPのELでのアクセスが簡易だから。

#### 属性を持つオブジェクト

`HttpServletRequest`のように属性を持つことのできるオブジェクトが他に3種類ある。

|コンテナ型|doメソッドでの取得方法|JSPのスコープ|
|----------|----------------------|-------------|
|`HttpServletRequest`|引数で渡ってくる|リクエスト|
|`HttpSession`|`HttpServletRequest`の`getSession`メソッドで取得|セッション|
|`ServletContext`|`getServletContext`メソッドで取得|アプリケーション|
|`PageContext`|−|ページ|

属性用コンテナの違いはJSPの世界ではスコープの違いとして認識される。スコープは可視範囲と生存期間に関係する。スコープの広い順に

1. アプリケーション
2. セッション
3. リクエスト
4. ページ

となる。スコープが広いほど、可視範囲が大きく生存期間が長くなる。スコープが広いほどグローバル変数に近くなるので、必要な範囲で最小のスコープの属性コンテナを使うべき。ただし、ページスコープだけはJSPの中で有効な属性用コンテナで、サーブレットクラスとJSPの間の状態共有には使わない。

#### 属性とスコープ

* フォワードを行うサーブレットクラスとJSPの間だけで共有する状態は、`HttpServletRequest`の属性で引き渡す
* セッション中のユーザに紐づく状態管理は`HttpSession`の属性で管理する
* `ServletContext`を使うアプリケーションスコープは、事実上サーブレットアプリの中のグローバル変数。グローバル変数と同じ理由で利用は非推奨。

## MVCアーキテクチャ

MVCアーキテクチャとは、モデル、ビュー、コントローラの3つの機能の分割を意識した技法のこと。Webアプリケーションの世界へ適用され主流のアーキテクチャになっている。

プログラミングの大原則の1つに分割統治があるが、MVCアーキテクチャは大きな視点での役割分割。

### MVCの最初の一歩

最初のポイントは、ビューの分離。ビューとはソフトウェアの機能の中で見た目に関わる部分。いわゆるユーザーインタフェース。サーブレットアプリでは出力HTMLを生成する部分と、利用者からの入力を処理する部分。

ビュー（見た目）は、最も変わりやすい部分。プログラミングの原則の1つが、変わりやすい部分と変わりにくい部分を分離することと、変わりやすい部分への依存を減らすこと。MVCアーキテクチャはビューを分離するだけではなく、他の部分がビューに依存することをなくすことも含んでいる。

サーブレットアプリは、利用者の操作に応じて、必要な内部処理を呼び出し、内部処理の結果を出力するためにビューを呼び出す役割をコントローラとして分離し、データベース処理などの内部処理をモデルとして分離する。

### ビューを分離したCRUDの実例

* サーブレットクラスのみでビューも実装すると
    * HTMLの断片が文字列の形で散在する
    * 見た目は最も変わりやすい部分で、HTMLの変更のたびにJavaのコードに手を入れることは煩雑
    * 開発者とWebデザイナが分業する大規模開発では、WebデザイナながHTMLの変更ををJavaのPrint文に落としこむのは非現実的
* JSP/JSTLのみでモデル・コントローラを実装すると
    * 逆にJava開発者の負担が増える
    * 複雑なロジックをJSPファイル内に無理矢理埋め込むことになる

サーブレット開発では、ビューをJSPに分離することがベストプラクティスとして知られるようになった。JSPファイルはHTML生成の表示だけに役割を特化する。それ以外の処理はサーブレットクラスで行い、現代ではJSPファイルの中にJavaのロジックを書くことは忌避される。

#### プログラムの構造

HTML生成処理（ビュー）をJSP側に追い出す技法の大まかな流れは次のようになる。

1. サーブレットクラス（コントローラ）でリクエストを受ける
2. 内部処理を呼び出す。内部処理にリクエスト情報を入力として渡す
3. 内部処理から出力を受け取る
4. サーブレットクラス（コントローラ）からJSPファイルにフォワードする。この時、内部処理からの出力をJSPに入力として渡す
5. JSPファイルはサーブレットクラス（コントローラ）から受け取った入力を元にHTMLを生成する

#### 日本語

サーブレットAPIで日本語のクエリパラメータを適切に処理するには、次のように`getParameter`メソッドの呼び出し前に`setCharacterEncoding`メソッドで文字コードを指定する必要がある。

```
#!java

// 日本語入力を受けつける
req.setCharacterEncoding("UTF-8");
String title = req.getParameter("title");
String body = req.getParameter("body");

```

現実的にはフィルタで一括設定を推奨する。

GETメソッドでHTMLフォーム入力を受け取る場合、Tomcat固有の注意がある。server.xml に次のように`useBodyEncodingForURI`属性の設定が必要。

```
#!xml

<Connector port="8080" protocol="HTTP/1.1"
    省略
    useBodyEncodingForURI="true" />    <!-- 追加 -->
```

#### ファイルアップロード

WebブラウザのHTMLフォームで`<input type="file">`の要素を指定すると利用者がファイルをサーバにアップロードできる。内部的には、ファイルのアップロード時は通常のHTMLフォームの入力値と異なる形式でネットワーク上を流れる。（multipart/form-data）

ファイルのアップロードデータを取得する直接的なメソッドはサーブレットAPIには存在しない。`getInputStream`や`getReader`メソッドでリクエストボディを読むストリームオブジェクトを取得して解析する必要がある。よく用いられる手法として、ライブラリを使う。

#### ファイルアップロード処理ライブラリ

ファイルのアップロードデータを取得するためのライブラリに Apache Commons の FileUpload ライブラリがある。このライブラリは Commons IO ライブラリに依存するので、それも必要。

#### FileUpload ライブラリの利用

```
#!java

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

// doPost 処理内
if (ServletFileUpload.isMultipartContent(request)) {
    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List<ListItem> items = upload.parseRequest(request);
    
    for (FileItem item : items) {
        if (!item.isFormField()) {
            String fieldName = item.getFieldName();
            String fileName = item.getName();
            String contentType = item.getContentType();
            byte[] data = item.get();    // アップロードされたファイル
        }
    }
}
```

### フレームワークへの道

サーブレットアプリのコードの重複が目につく。コードの重複は保守性を落としバグの元。重複しがちなコードとして、

* web.xml 内の似たマッピングの構造
* 各サーブレットクラスに同じデータソース定義
* 各サーブレットクラスに似たSQL
* 各サーブレットクラスに似たDTO生成コード
* 各サーブレットクラスにリクエスト値の検証コード
* 各サーブレットクラスにエスケープ処理

コードの重複の原因の大半は、サーブレットクラスが多くの仕事をしすぎていること。改善のためにコントローラとモデルを分離する。サーブレットクラスはコントローラの役割に徹すべき。

直感的に言えば、上記に上げた重複処理を内部処理（モデル）としてまとめ、コントローラはモデルに処理を移譲する。コントローラはモデルから結果を受け取り（ビューが参照しやすい形式に変換して）ビューに渡す。ビューはコントローラから受け取ったデータ（DTO）を表示する。ビューはDTO以外の状態に依存しないようにする。

* コントローラとモデルを分離
* サーブレットクラスは処理のディスパッチに専念したコントローラにする
* ページコントローラパターンからフロントコントローラパターンにする（全体を受けるコントローラクラスを用意し、コントローラに共通の前処理を実施後、下位コントローラに処理を移譲する）
* 内部処理クラスを最低次の3層に分離する
    * サーブレットAPI（`HttpServletRequest`や`HttpServletResponse`）を利用する層（アプリケーション層やサービス層と呼ぶ）
    * データベースを利用する層（データソース層やデータアクセス層と呼ぶ）
    * 上記2つから独立した中間層（問題領域そのものを扱うことからドメイン層やビジネス層と呼ぶ）

このような改善手法を提供するフレームワークが既に数多く存在する。

## セッション管理

サーブレットアプリにおける「**セッション管理**」とは、端的に説明すると、HTTPリクエストがどの利用者からのリクエストかを判別するための仕組み。

### セッション管理が必要な理由

HTTPという通信プロトコルは、1つのリクエストに対し1つのレスポンスが返ることが1つの単位。同じ両者がWebブラウザから同じサーバにリクエストを投げても、次のリクエストと前のリクエストを結びつける情報は原則的にない。

TCP/IPのレベルで見ると、同じPCからリクエストを投げると同じIPアドレスからのリクエストになるが、同じIPアドレス空のリクエストを同じ利用者からのリクエストとみなすには、2つの理由で無理がある。

* NATやプロクシサーバの存在
    * サーバ側から見ると、異なる利用者のPCからのリクエストが同じIPアドレスからのリクエストに見える
* 利用者がノートPCや携帯端末を使う場合
    * 同じ利用者が同じWebブラウザを使い続けていても、サーバ側には異なるIPアドレスに見えることがある
    * DHCPなどの動的なIPアドレスの割当で利用者のPCが変わることもある。
    * TCP/IPのレベルで接続を維持し続ける keep-alived を利用したとしても、これはHTTPの必須機能ではなく、またWebブラウザは複数のHTTPリクエストを同時に投げることもあるので、同一利用者を判別するために依存することはできない

### セッション管理の仕組み

それぞれが独立したHTTPリクエストに対し、同じ利用者からのリクエストを区別するマークを付けて、そのマークを元にサーバ側でリクエストから利用者を判別するための仕組みがセッション管理。

リクエストを区別するためのマークには、クッキーもしくは特別なクエリパラメータ（一般に`jsessionid`と呼ぶ）を使う。サーブレットアプリ側で利用者毎に保持する状態をセッションと呼ぶ。HTTPリクエストのクッキーもしくは`jsessionid`を参照することで、そのリクエストがどのセッションに属するかを判別可能で、セッションに利用者の情報を格納することで、リクエストごとに利用者に応じたレスポンスを返すことが出来る。

#### クッキーによるセッション管理

HTTPリクエスト上のクッキーの実体はリクエストヘッダの１つ。クッキーヘッダが他のリクエストヘッダと異なるのは、Webブラウザがクッキーヘッダの値を記憶する点。Webブラウザは相手サーバごとにクッキー値を記憶して、同じ相手サーバへのリクエストには記憶しているクッキー値をクッキーヘッダに載せて送る。サーブレットアプリは`Cookie`ヘッダの値を参照することで同じWebブラウザからのリクエストの判別が可能。

クッキーで区別できるのは同じWebブラウザなので、別の利用者が同じWebブラウザを使うと利用者の区別が付かない。企業や学校のPCでも十分に危険であるし、ネットカフェ等不特定多数の利用者が同一のPCを使う環境では致命的なセキュリティリスクになる。また、クッキーヘッダの値を1度でも盗まれると完全に利用者のなりすましができる。しかしWebブラウザが記憶するクッキーの値に厳重な防御機能はない。

このリスクを防ぐために、クッキー値にはサーブレットアプリが発行するセッションIDを使い、利用者がログアウトをした時あるいは利用者から一定時間リクエストがないときにサーバ側でセッションIDをクリアする。サーバ側のセッションIDの有効期間限定することで、クッキー値つまりセッションIDを盗まれた時のリスクを減らす。これをセッションタイムアウトという。

クッキー値をセットするには、2つの方法がある。

* サーバからのレスポンスの`Set-Cookie`ヘッダを使う
* JavaScript を使う

サーブレットアプリのセッション管理では一般にレスポンスヘッダの`Set-Cookie`ヘッダを使う。

#### セッションオブジェクト

サーブレットアプリでは Cookie ヘッダや Set-Cookie ヘッダを直接意識する必要はない。またセッションIDの値も直接意識することはない。

プログラムの中でセッションオブジェクト作成のAPIを呼ぶと自動的にセッションIDを生成し、Set-Cookie レスポンスヘッダにセッションIDが載る。セッションIDの値はユニークであればいいので、通常、ランダムに生成する。

サーブレットコンテナは明示的にセッションオブエジェクトを破棄するか、もしくはセッションタイムアウト値に達しない限り、生成したセッションオブエジェクトを維持する。

セッションオブジェクト取得のAPIを呼ぶと、Cookie ヘッダの値のセッションIDから対応するセッションオブジェクトを取得する。こうして同じWebブラウザからのリクエストはクッキーヘッダに載ったセッションIDにより、サーブレットアプリ側で同じセッションオブジェクトにたどり着くことが出来る。

セッションオブジェクトの型は`HttpSession`。

### jsessionid クエリパラメータによるセッション管理

クッキーが使えないWebブラウザがある。セッション管理のためにサーブレットアプリはクエリパラメータを利用できる。利用者からのHTTPリクエストURLに必ずセッションIDのクエリパラメータが載るようにする。デフォルトではクエリパラメータ名が`jsessionid`。

通常このクエリパラメータ名を変更することはないため、一般に`jsessionid`パラメータと呼ぶ。クッキーを使うセッション管理で、開発者がクッキーの具体的な値を意識する必要がないように、クエリパラメータを使う場合も開発者は`jsessionid`パラメータ値を意識する必要はない。リクエストURLに`jsessionid`パラメータがあれば、サーブレットコンテナは自動的にセッションIDのとして認識する。セッション管理に関して、クッキーとクエリパラメータの利用は透過に扱える。

クエリパラメータを使うセッション管理にはクッキーを使う場合と1つ違う点がある。サーブレットアプリが実行中に生成するリクエストURLに`jsessionid`パラメータを明示的に付与しなければいけない点。クッキーの場合、セッションオブジェクトを生成するだけで暗黙にクッキーヘッダの読書をしてくれるが、クエリパラメータの場合は自前でURLを変更する必要がある。

URLに`jsessionid`を明示的に付与するAPIがある。文字列結合でリクエストURLを組み立てないようにする。`jsessionid`パラメータを付与してURLを書き換えるAPIは`encodeURL`。

`jsessionid`パラメータのないURL文字列を引数に渡すと、`jsessionid`パラメータを付与したURL文字列を返す。

JSTLでは、次の`c:url`タグを使う。

```
#!java

<c:url value="url"/>
```

### セッションタイムアウト

サーバ側でセッションIDを無効にするまでの時間を「**セッションタイムアウト値**」と呼ぶ。セッションタイムアウト値が長いと、長時間同じセッションIDをクッキー値として使い続けるので、セッションIDを盗まれる確率が高まる。また盗まれたセッションIDを悪用される確率も上がる。

セッションタイムアウト値が短いと安全になるが、Webアプリの利用者はすぐにセッションタイムアウトの画面を見て、再ログインを促されることになり利便性を落とす。利便性とセキュリティリスクの兼ね合いでセッションタイムアウト値を決める必要がある。

セッションタイムアウト値は Tomcat の場合、`web.xml`に記述をする。

### セッション管理での注意

* セッションIDの値を直接読み書きすることは避ける。具体的なセッションID値に依存すべきではない。
    * セッションID以外の値をクッキーに載せない。クッキーの値は秘密情報にはなり得ない。
* セッションオブジェクトに大きなオブジェクトを格納しない
    * 利用者に結びつくオブジェクトをマップで管理して、マップのキーをセッションオブジェクトで管理する技法などを使う
    * JSPからセッションスコープのオブジェクトを参照することは推奨しない
* JSPはフォワード元のサーブレットオブジェクトから渡された状態（リクエストスコープのオブジェクト）だけに依存すべき。
    * `<%@ page session="false" %>`を設定する。

### ユーザ認証

サーブレットアプリのユーザー認証の方式としてはフォーム認証が一般的。

* フォーム認証のリクエストは、必ずPOSTメソッドを使う
    * GETメソッドを使うとリクエストURLのクエリパラメータにパスワードが載る
    * Webブラウザの履歴やサーバのログに生のパスワードがそのまま残る危険がある。
* HTTPS（SSL）で通信経路を暗号化するのが普通
    * 通常のHTTPでは、POSTメソッドでも利用者が入力したパスワードが生のままネットワークを流れる。
    * 処理性能の問題でHTTPSに出来ない場合でも、ログイン画面のフォーム認証のポスト処理はHTTPS通信にする。
* データベースに保存するパスワードは、データベースが安全に守られていない場合、ハッシュ化したパスワードを保存する。認証時は入力されたパスワードをハッシュ化した値と一致すれば本人とみなす。
    * ハッシュ化と暗号化は違うことに注意する
    * 暗号化は復号化できる。ハッシュ化は複合できない。

