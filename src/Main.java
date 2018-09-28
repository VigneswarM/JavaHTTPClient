package assignment1;

import java.util.*;
import java.util.Scanner;

public class Main {

	static String GET_URL = "http://httpbin.org/get?author=Computer+Networks&course=GET+Request+Using+Sockets+in+Java";
	// static String GET_URL = "https://httpbin.org/";
	static String POST_URL = "http://httpbin.org/post";
	private static Scanner scan;

	public static void main(String[] args) {

		System.out.println("***************************************");
		scan = new Scanner(System.in);
		System.out.println(" Enter Your Httpc Command line Request ");
		System.out.println("***************************************");

		String str = scan.nextLine();
		while (!str.equalsIgnoreCase("exit")) {
			String output = "";
			String[] inputArr = str.split(" ");
			if (inputArr != null) {
				if (inputArr[0].equals("httpc")) {
					try {
						if (inputArr[1].equals("get"))
							Httpprocess_get(inputArr, "GET");
						else if (inputArr[1].equals("post"))
							Httpprocess_post(inputArr, "POST");
						else if (inputArr[1].equals("help"))
							HttpClient.Http_Help(inputArr);
						else
							output = "Invalid Command, type httpc help";

					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				} else {
					output = "Invalid Command, type httpc help";
				}
			}
			System.out.println(output);
			System.out.println("Renter Request or Press Exit to terminate");
			str = scan.nextLine();
		}
		System.out.println("***************************************");
		System.out.println("**** Terminating the command line *****");
		System.out.println("***************************************");

	}

	public static void Httpprocess_get(String[] inputArr, String request) {

		Boolean flag_V = false;
		Boolean flag_H = false;
		Boolean flag_O = false;
		Boolean flag_Invalid = false;

		Map<String, String> responseMap;
		int length = inputArr.length;
		int val = length - 1;
		String filename = null;
		String GET_URL = null;
		ArrayList<String> headerlist = new ArrayList<String>();

		try {

			// checking headers
			if (length > 3) {
				String header = "";
				int i = 2;

				while (i != val) {
					header = inputArr[i];

					switch (header) {

					case "-v":
						flag_V = true;
						break;

					case "-h":
						// Adding headers to a list
						flag_H = true;
						i = i + 1;
						headerlist.add(inputArr[i]);
						break;

					case "-d":
						flag_Invalid = true;
						break;

					case "-f":
						flag_Invalid = true;
						break;

					case "-o":
						flag_O = true;
						filename = inputArr[i + 1];
						break;

					default:
						break;

					}
					i++;

				}
			}

			if (flag_Invalid)
				System.out.println("Invalid Command, type httpc help");

			else {
				// Getting the URL within double quotes
				if (flag_O)
					GET_URL = inputArr[val - 2].substring(1, inputArr[val - 2].length() - 1);
				else
					GET_URL = inputArr[val].substring(1, inputArr[val].length() - 1);

				// fetching response
				responseMap = HttpClient.sendGetRequest(GET_URL, headerlist);
				// System.out.println(responseMap);

				// Printing and writing to a file
				if (length == 3)
					System.out.println("Content: \n" + responseMap.get("content"));

				else if (flag_V) {
					System.out.println("Content: \n" + responseMap.get("header"));
					System.out.println("Content: \n" + responseMap.get("content"));

					if (flag_O) {
						HttpClient.File_backup(filename, responseMap.get("header"));
						HttpClient.File_backup(filename, responseMap.get("content"));
					}
				} else {
					System.out.println("Content: \n" + responseMap.get("content"));

					if (flag_O)
						HttpClient.File_backup(filename, responseMap.get("content"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void Httpprocess_post(String[] inputArr, String request) {

		int length = inputArr.length;
		Map<String, String> responseMap;
		int val = length - 1;
		String filename = null;
		String POST_URL = null;
		String data = null;
		ArrayList<String> headerlist = new ArrayList<String>();

		Boolean flag_V = false;
		Boolean flag_H = false;
		Boolean flag_O = false;
		Boolean flag_D = false;
		Boolean flag_F = false;
		Boolean flag_Invalid = false;

		// checking headers
		String header = "";
		int i = 2;

		try {
			while (i != val) {
				header = inputArr[i];

				switch (header) {

				case "-v":
					flag_V = true;
					break;

				case "-h":
					// Adding headers to a list
					flag_H = true;
					i = i + 1;
					headerlist.add(inputArr[i]);
					break;

				case "-d":
					flag_D = true;
					i = i + 1;
					data = inputArr[i];
					break;

				case "-f":
					flag_F = true;
					break;

				case "-o":
					flag_O = true;
					filename = inputArr[i + 1];
					break;

				default:
					break;

				}
				i++;

			}

			if ((flag_D == true) && (flag_F == true))
				flag_Invalid = true;

			if (flag_Invalid)
				System.out.println("Invalid Command, type httpc help");

			else {
				// Getting the URL within double quotes
				if (flag_O)
					POST_URL = inputArr[val - 2].substring(1, inputArr[val - 2].length() - 1);
				else
					POST_URL = inputArr[val].substring(1, inputArr[val].length() - 1);

				// fetching response
				responseMap = HttpClient.sendPostRequest(POST_URL, data, headerlist);

				// System.out.println(responseMap);
				
				/**
				 * 1. Header not apprearing for http post
				 * 2. json not present in data
				 * 3. URL redirection
				 */
				
				if (flag_V) {
					System.out.println("Content: \n" + responseMap.get("header"));
					System.out.println("Content: \n" + responseMap.get("content"));

					if (flag_O) {
						HttpClient.File_backup(filename, responseMap.get("header"));
						HttpClient.File_backup(filename, responseMap.get("content"));
					}
				} else {
					System.out.println("Content: \n" + responseMap.get("content"));
					if (flag_O)
						HttpClient.File_backup(filename, responseMap.get("content"));

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
