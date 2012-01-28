package org.yukung.perfect_java.chap5;

/*
 * mainメソッドを実行すると、Myオブジェクトを無限に生成するコード。（致命的なバグ）
 */
public class My {
	
	public static void main(String[] args) throws Exception {
		My my1 = new My();
		System.out.println(my1.createdAt);
		Thread.sleep(1000);
		My my2 = new My();
		System.out.println(my2.createdAt);
	}
	
	
	long createdAt = System.currentTimeMillis(); // オブジェクト生成時に初期化される
}
