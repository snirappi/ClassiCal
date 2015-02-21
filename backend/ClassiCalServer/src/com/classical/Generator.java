package com.classical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Generator {

	private static final char[] REPLACE_CHARS = {'<', '>', '\'', '\"', '&'};

	public static final String[] COLORS = getFileContent("data/colors.txt").split("\n");
	public static final String[] BIRDS = getFileContent("data/birds.txt").split("\n");
	public static final String[] NOCTURNAL = getFileContent("data/nocturnal.txt").split("\n");
	public static final String[] QUESTIONS = getFileContent("data/questions.txt").split("\n");
	private static final Random RANDOM = new Random("ideastock".hashCode());
	
	public static String generateName(boolean isPublic) {
		String name = getName(isPublic);
		while (doesExist(name)) {
			name = getName(isPublic);
		}
		return name;
	}
	
	public static String getName(boolean isPublic) {
		String name = buildName(isPublic);
		while (doesExist(name)) {
			name = buildName(isPublic);
		}
		return name;
	}
	
	private static String buildName(boolean isPublic) {
		return COLORS[RANDOM.nextInt(COLORS.length)] + COLORS[RANDOM.nextInt(COLORS.length)] +
			((isPublic) ? (BIRDS[RANDOM.nextInt(BIRDS.length)]) : (NOCTURNAL[RANDOM.nextInt(NOCTURNAL.length)])) + "!";
	}
	
	public static boolean doesExist(String name) {
		return new File("generated/" + name.toLowerCase() + ".txt").exists();
	}
	
	/**
	 * Cleans input/output to prevent malicious script injection
	 * @param s String to clean
	 * @return Cleaned string
	 */
	public static String clean(String s) {
		StringBuilder builder = new StringBuilder();
		int length = s.length();
		char c;
		boolean flag = false;
		for (int i = 0; i < length; i++) {
			c = s.charAt(i);
			flag = false;
			for (int j = 0; j < REPLACE_CHARS.length; j++) {
				if (c == REPLACE_CHARS[j]) {
					builder.append("&#" + (int)c + ";");
					flag = true;
					break;
				}
			}
			if (!flag) {
				builder.append(c);
			}
		}
		return builder.toString();
//		return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&#34").replaceAll("'", "&#39").replaceAll("&", "&amp");
	}

	/**
	 * Generates the string of a whole html page 
	 * @param title Title of the page
	 * @param css CSS files to reference
	 * @param js JS files to reference
	 * @param classes List of HTML classes to label divs
	 * @param contents Contents of each class
	 * @return The HTML page as a string
	 */
	public static String generatePage(String title, String[] css, String[] js, String[] classes, String[][] contents) {
		List<List<String>> newContents = new LinkedList<List<String>>();
		for (String[] s : contents) {
			newContents.add(Arrays.asList(s));
		}
		return generatePage(title, Arrays.asList(css), Arrays.asList(js), Arrays.asList(classes), newContents);
	}
	
	/**
	 * Generates the string of a whole html page 
	 * @param title Title of the page
	 * @param css CSS files to reference
	 * @param js JS files to reference
	 * @param classes List of HTML classes to label divs
	 * @param contents Contents of each class
	 * @return The HTML page as a string
	 */
	public static String generatePage(String title, List<String> css, List<String> js, List<String> classes, List<List<String>> contents) {
		if (classes.size() != contents.size()) {
			System.out.println("classes.size() [" + classes.size() + "] != contents.size() [" + contents.size() + "]");
			return "Page was not generated properly";
		}
		StringBuilder builder = new StringBuilder();
		builder.append("<html>\n\t<head>\n\t\t<title>" + title + "</title>\n");
		for (String s : css) {
			builder.append("\t\t<link rel='stylesheet' type='text/css' href='/" + s + "'>\n");
		}
		for (String s : js) {
			builder.append("\t\t<script src='/" + s + "'></script>\n");
		}
		builder.append("\t</head>\n\t<body>\n\t\t<div class='container'>\n");
		int max = -1;
		for (List<?> l : contents) {
			int size = l.size();
			max = (size > max) ? size : max;
		}
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < classes.size(); j++) {
				if (i < contents.get(j).size()) {
					builder.append("\t\t\t<div class='" + classes.get(j) + "'>" + contents.get(j).get(i) + "</div>\n");
				}
			}
		}
		builder.append("\t\t</div>\n\t</body>\n</html>");
		return builder.toString();
	}
	
	/**
	 * Parses files and puts it into a string
	 * @param path File path of file we want
	 * @return Content of the file
	 */
	public static String getFileContent(String path) {
		File file = new File(path);
		StringBuilder builder = new StringBuilder();
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
		while (reader.hasNextLine()) {
			builder.append(reader.nextLine() + "\n");
		}
		reader.close();
		return builder.toString();
	}
	
}