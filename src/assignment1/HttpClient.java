package assignment1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class HttpClient {

    final static Integer PORT = 80;
    public static Map<String, String> sendGetRequest(String url) throws Exception {
        Map<String, String> responseMap = new HashMap<>();
        URL url_Object = new URL(url);
        System.out.println("Establishing Connection");
        Socket socket_ = new Socket(InetAddress.getByName(url_Object.getHost()), PORT);
        System.out.println("Connection Established");
        PrintWriter printWriter = new PrintWriter(socket_.getOutputStream());
        printWriter.println("GET /" + url_Object.getFile() + " HTTP/1.0");
        printWriter.println("Host: " + url_Object.getHost());
        printWriter.println("");
        printWriter.flush();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        boolean headerDone = false;
        System.out.println("Fetching response. Please wait...");
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line + "\n");
            if (line.isEmpty() && !headerDone) {
                responseMap.put("header", response.toString());
                headerDone = true;
                response = new StringBuilder();
            }
        }

        bufferedReader.close();
        printWriter.close();
        socket_.close();
        System.out.println("Done!\n");

        responseMap.put("content", response.toString());
        return responseMap;
    }


    public static Map<String, String> sendPostRequest(String url, String data) throws Exception {
        Map<String, String> responseMap = new HashMap<>();
        URL url_Object = new URL(url);
        System.out.println("Creating Connection");
        Socket socket_ = new Socket(InetAddress.getByName(url_Object.getHost()), PORT);
        System.out.println("Connection Established");
        PrintWriter printWriter = new PrintWriter(socket_.getOutputStream());
        printWriter.println("POST /" + url_Object.getFile() + " HTTP/1.0");
        printWriter.println("Host: " + url_Object.getHost());
        printWriter.println("Content-Length: " + data.length());
        printWriter.println();   //Writing an empty line just to notify the server the header ends here
                                // and next thing written will the data/content
        printWriter.println(data);
        printWriter.println();
        printWriter.flush();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        boolean headerDone = false;
        System.out.println("Fetching response. Please wait...");
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line + "\n");
            if (line.isEmpty() && !headerDone) {
                headerDone = true;
                response = new StringBuilder();
            }
        }
        bufferedReader.close();
        printWriter.close();
        socket_.close();
        System.out.println("Done!\n");

        responseMap.put("content", response.toString());
        return responseMap;
    }
}
