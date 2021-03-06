package org.yukung.perfect_java.chap2;

import com.google.common.base.Stopwatch;

import org.apache.commons.lang3.time.StopWatch;

public class StringBuilderTest {
	
	private static final int LOOP_COUNT = 10000;
	
	
	/*
	 * 無駄が多い文字列結合。
	 *
	 * += の結合時にStrigBuilderが都度生成され、さらにappend()が2度呼ばれる。
	 * sのStringオブジェクトをappend,resultのStringオブジェクトをappend。
	 *
	 * 実測結果 time: 64093ms
	 */
	public static String concat(String[] array) {
		String result = "";
		for (String s : array) {
			result += s;
		}
		return result;
	}
	
	/*
	 * StringBuilder#append()を使った例。
	 * StringBuilderが一度だけ生成され、append()も一度しか呼ばれない。
	 *
	 * 実測結果 time: 9ms
	 */
	public static String effectiveConcat(String[] array) {
		StringBuilder result = new StringBuilder();
		for (String s : array) {
			result.append(s);
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		lookAtBehavior();
		
		measureConcatenate();
		
		sameAndEqual();
		
		contentsEquals();
		
	}
	
	/*
	 * StringBuilderの同値比較
	 */
	private static void contentsEquals() {
		StringBuilder sb1 = new StringBuilder("012");
		StringBuilder sb2 = new StringBuilder("012");
		System.out.println(sb1 == sb2); // false
		System.out.println(sb1.equals(sb2)); // false
		System.out.println(sb1.toString().contentEquals(sb2)); // true
	}
	
	private static void lookAtBehavior() {
		StringBuilder sb = new StringBuilder("0123456789");
		char charAt = sb.charAt(3);
		System.out.println(charAt);
		int length = sb.length();
		System.out.println(length);
		sb.append(8);
		System.out.println(sb.toString());
		sb.delete(1, 2);
		System.out.println(sb.toString());
		int indexOf = sb.indexOf("6");
		System.out.println(indexOf);
		sb.insert(8, 'f');
		System.out.println(sb.toString());
		sb.replace(0, 5, "543210");
		System.out.println(sb.toString());
		sb.setCharAt(0, '7');
		System.out.println(sb.toString());
		String substring = sb.substring(5);
		System.out.println(substring);
		sb.reverse();
		System.out.println(sb.toString());
		
	}
	
	private static void measureConcatenate() {
		String[] data = new String[LOOP_COUNT];
		for (int i = 0; i < LOOP_COUNT; i++) {
			data[i] = Integer.toString(i);
		}
		
		Stopwatch stopwatch = new Stopwatch().start();
		
		concat(data);
		
		stopwatch.stop();
		System.out.printf("time: %sms%n", stopwatch.elapsedMillis());
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		effectiveConcat(data);
		
		stopWatch.stop();
		
		System.out.printf("time: %sms%n", stopWatch.getTime());
	}
	
	/*
	 * 文字列リテラル同士の結合は同一のStringオブジェクトを参照する。
	 */
	private static void sameAndEqual() {
		String s1 = "012";
		String s2 = "012";
		String s3 = "0" + "1" + "2";
		String s4 = "0" + "1" + 2;
		
		System.out.println(s1 == s2); // true
		System.out.println(s2 == s3); // true
		System.out.println(s3 == s4); // true
		System.out.println(s4 == s1); // true
		System.out.println(s1 == "012"); // true
	}
}
