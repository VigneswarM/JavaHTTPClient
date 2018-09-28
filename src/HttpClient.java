package assignment1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

	final static Integer PORT = 80;

	public static Map<String, String> sendGetRequest(String url, ArrayList<String> headerlist) throws Exception {
		Map<String, String> responseMap = new HashMap<>();
		URL url_Object = new URL(url);
		Socket socket_ = new Socket(InetAddress.getByName(url_Object.getHost()), PORT);
		System.out.println("Connection Established\n");
		PrintWriter Writer = new PrintWriter(socket_.getOutputStream());
		Writer.println("GET /" + url_Object.getFile() + " HTTP/1.0");
		Writer.println("Host: " + url_Object.getHost());

		if (!headerlist.isEmpty())
			headerlist.stream().forEach(x -> Writer.println(x));

		Writer.println("");
		Writer.flush();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		boolean headerDone = false;
		while ((line = bufferedReader.readLine()) != null) {
			response.append(line + "\n");
			if (line.isEmpty() && !headerDone) {
				responseMap.put("header", response.toString());
				headerDone = true;
				response = new StringBuilder();
			}
		}

		bufferedReader.close();
		Writer.close();
		socket_.close();

		responseMap.put("content", response.toString());
		return responseMap;
	}

	public static Map<String, String> sendPostRequest(String url, String data, ArrayList<String> headerlist)
			throws Exception {
		Map<String, String> responseMap = new HashMap<>();
		URL url_Object = new URL(url);
		Socket socket_ = new Socket(InetAddress.getByName(url_Object.getHost()), PORT);
		System.out.println("Connection Established\n");
		PrintWriter Writer = new PrintWriter(socket_.getOutputStream());
		Writer.println("POST /" + url_Object.getFile() + " HTTP/1.0");
		Writer.println("Host: " + url_Object.getHost());
		Writer.println("Content-Length: " + data.length());
		if (!headerlist.isEmpty())
			headerlist.stream().forEach(x -> Writer.println(x));

		Writer.println(); // Writing an empty line just to notify the server the header ends here
							// and next thing written will the data/content
		Writer.println(data);
		Writer.println();
		Writer.flush();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		boolean headerDone = false;
		while ((line = bufferedReader.readLine()) != null) {
			response.append(line + "\n");
			if (line.isEmpty() && !headerDone) {
				headerDone = true;
				response = new StringBuilder();
			}
		}
		bufferedReader.close();
		Writer.close();
		socket_.close();

		responseMap.put("content", response.toString());
		return responseMap;
	}

	static void Http_Help(String inputArr[]) {

		int length = inputArr.length;

		if (length == 3 && inputArr[2].equals("get")) {
			System.out.println("\nGet Usage " + "\n\n httpc help get " + "\n usage: httpc get [-v] [-h key:value] URL "
					+ "\n Get executes a HTTP GET request for a given URL."
					+ "\n -v Prints the detail of the response such as protocol, status and headers."
					+ "\n -h key:value Associates headers to HTTP Request with the format'key:value'.");

		}

		else if (length == 3 && inputArr[2].equals("post")) {

			System.out.println("\nPost Usage " + "\n\n httpc help post "
					+ "\n usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL "
					+ "\n Post executes a HTTP POST request for a given URL with inline data or from file."
					+ "\n  -v Prints the detail of the response such as protocol, status,	and headers."
					+ "\n -h key:value Associates headers to HTTP Request with the format 'key:value'."
					+ "\n -d string Associates an inline data to the body HTTP POST request."
					+ "\n -f file Associates the content of a file to the body HTTP POST request."
					+ "\n Either [-d] or [-f] can be used but not both.");

		}

		else
			System.out.println("\nhttpc help" + "\n httpc is a curl-like application but supports HTTP protocol only."
					+ "\n Usage: httpc command [arguments]" + "\nThe commands are:"
					+ "\n get executes a HTTP GET request and prints the response."
					+ "\n post executes a HTTP POST request and prints the response." + " help prints this screen."
					+ "\n Use 'httpc help [command]' for more information about a command.\n");

	}

	public static void File_backup(String filename, String content) {
		try {
			File file = new File(filename);

			// if file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.newLine();
				bw.close();
			} else {
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.newLine();
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
