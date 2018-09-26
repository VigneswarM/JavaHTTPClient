package assignment1;

import java.util.Map;

public class Main {

    static String GET_URL = "http://httpbin.org/get?author=Computer+Networks&course=GET+Request+Using+Sockets+in+Java";
   // static String GET_URL = "https://httpbin.org/";
    
    static String POST_URL = "http://httpbin.org/post";

    public static void main(String[] args) {
        try {
            Map<String, String> responseMap;

            responseMap = HttpClient.sendGetRequest(GET_URL);
            System.out.println("Header: \n" + responseMap.get("header"));
            System.out.println("Content: \n" + responseMap.get("content"));

            String data = "{" +
                    "\"author\" : \"Computer Networks Assignment 1\"," +
                    "\"course\" : \"POST Request Using Sockets in Java\"" +
                    "}";
            responseMap = HttpClient.sendPostRequest(POST_URL, data);
            System.out.println("Content: \n" + responseMap.get("content"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
