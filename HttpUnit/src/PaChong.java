

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.collections.map.StaticBucketMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PaChong {
	static String url_str = "http://www.dailystrength.org/group/depression?page=";
	//static String url_str = "http://www.dailystrength.org/group/depression?page=1#discussion-3578394";
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        
        String charset = "utf-8";
        String filepath = "/home/tianhao/study/研究/url.txt";
        for(int i=0;i<4;i++){ 
        HttpClient hc = new DefaultHttpClient();
        HttpGet hg = new HttpGet(url_str+i+"#discussion-3578394");
        HttpResponse response = hc.execute(hg);
        HttpEntity entity = response.getEntity();
        InputStream htm_in = null;  
        if(entity != null){
        	htm_in = entity.getContent();
        	String htm_str = InputStream(htm_in,charset);
            Document  doc =  Jsoup.parse(htm_str);
			Elements links= doc.select("div[class=newsfeed]").select("ul[class=discussion-list newsfeed__feed]").select("li[class=newsfeed__item]");
			for (Element link : links) {
				Elements lin = link.select("div[class=newsfeed__item-header]").select("div[class=newsfeed__title-block]").select("h3").select("a");  
				String re_url = lin.attr("href");
			    System.out.println(re_url);
			    saveHtml(filepath, re_url);
			}
        }
        }
        }
    
    
    
    public static void saveHtml(String filepath, String str){
        
        try {
            OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true), "utf-8");
            outs.write(str);
            outs.close();
        } catch (IOException e) {
            System.out.println("Error at save html...");
            e.printStackTrace();
        }
    }

    public static String InputStream(InputStream in_st,String charset) throws IOException{
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while((line = buff.readLine()) != null){
        	res.append(line);
        }
        return res.toString();
    }
}