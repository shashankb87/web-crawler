package com.bss.webcrawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        String root = "https://www.google.com/";
        crawler.crawl(root, 100);
    }
private Queue<String> wQueue;
    private List<String> visitedList;

    public WebCrawler() {
        wQueue = new LinkedList<>();
        visitedList = new ArrayList<>();
    }

    public void crawl(String root, int maxDepth){
        wQueue.add(root);
        visitedList.add(root);

        while(!wQueue.isEmpty()){
            String s = wQueue.remove();
            StringBuilder build = new StringBuilder();
            try{
                URL url = new URL(s);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine = in.readLine();

                while(inputLine  != null){
                    build.append(inputLine);

                    inputLine = in.readLine();
                }
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            String urlPattern = "(www|http:|https:)+[^\s]+[\\w]";
            Pattern pattern = Pattern.compile(urlPattern);
            Matcher matcher = pattern.matcher(build.toString());

            maxDepth = getMaxDepth(maxDepth, matcher);

            if(maxDepth == 0){
                break;
            }
        }
    }

    private int getMaxDepth(int maxDepth, Matcher matcher) {
        while(matcher.find()){
            String url = matcher.group();

            if(!visitedList.contains(url)){
                visitedList.add(url);
                System.out.println("URL " + url);
                wQueue.add(url);
            }

            // exit the loop if it reaches the breakpoint.
            if(maxDepth == 0){
                break;
            }
            maxDepth--;
        }
        return maxDepth;
    }
}
