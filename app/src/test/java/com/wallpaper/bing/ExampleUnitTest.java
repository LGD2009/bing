package com.wallpaper.bing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

    }


    @Test
    public void grab() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.sowang.com/bbs/forum.php?mod=forumdisplay&fid=67").get();
            System.out.println(doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.getElementById("threadlisttableid").getElementsByClass("s xst");
        for (Element e : elements) {
            String text = e.childNode(0).toString();
            if (text.contains(" ")) {
                String date = text.substring(text.lastIndexOf(" ") + 1, text.length());
                String title = text.substring(text.indexOf("：") + 1, text.lastIndexOf(" ") + 1);
                System.out.println(date + "---" + title);
            }
        }

        /*OkHttpClient client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                //.url("http://www.sowang.com/bbs/forum.php?mod=forumdisplay&fid=67")
                .url("http://cn.bing.com/")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            Document doc = Jsoup.parse(s);

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Test
    public void jsoup() {
        Document doc;
        try {
            doc = Jsoup.connect("http://cn.bing.com/cnhp/life?currentDate=20171225").get();
            System.out.println(doc.title());
            String id = "20171225";
            String title = doc.getElementsByClass("hplaTtl").text();
            String attribute = doc.getElementsByClass("hplaAttr").text();
            String para1 = doc.getElementsByClass("hplaCata").get(0).childNode(2).childNode(0).toString();
            String thumbnail = doc.getElementsByClass("rms_img").get(1).attributes().get("src");

            System.out.println(thumbnail);
            Elements elementh = doc.getElementsByClass("hplac hplacl");
            for (Element e : elementh) {
                String t = e.getElementsByClass("hplactt").text();
                String c = e.getElementsByClass("hplactc").text();
            }


            Elements elements = doc.getElementsByClass("hplaCard");
            for (Element element : elements) {
                String knowledgeLocation1 = element.getElementsByClass("hplatt").text();
                String knowledgeTitle1 = element.getElementsByClass("hplats").text();
                String knowledgeSrc1 = element.getElementsByClass("rms_img").get(1).attributes().get("src");
                String knowledgeContent1 = element.getElementsByClass("hplatxt").text();
                List<Node> nodes = element.childNodes();
                element.getElementsByClass("hplatt");
                String knowledgeLocation = nodes.get(0).childNode(0).childNode(0).childNode(0).toString();
                String knowledgeTitle = nodes.get(1).childNode(0).toString();
                String knowledgeSrc = nodes.get(2).childNode(0).attributes().get("src");
                String knowledgeContent = nodes.get(3).childNode(0).toString();
                System.out.println(knowledgeLocation + "---" + knowledgeTitle + "----" + knowledgeSrc + "---" + knowledgeContent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test() {
        Document doc;
        try {
            doc = Jsoup.connect("https://bing.ioliu.cn/?p=1").get();
            System.out.println(doc.title());

            Elements elements1 = doc.getElementsByClass("item");
            for (Element element : elements1) {
                String href = element.getElementsByClass("ctrl share").get(0).attributes().get("href");
                String date = element.getElementsByClass("t").get(0).text();
                System.out.println(date);
                String pic = href.substring(href.indexOf("pic=") + 4, href.indexOf(".jpg") + 4).replace("800", "1920").replace("480", "1080");
                System.out.println(pic);
                String name = pic.substring(pic.lastIndexOf("/") + 1, pic.length());
                System.out.println(name);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConcatWithObserver() throws InterruptedException {
        Observable<List<Integer>> just1 = Observable.error(new Throwable("aaa"));
        Observable<List<Integer>> just2 = Observable.just(Arrays.asList(1, 2, 3));

        Observable.concatDelayError(Arrays.asList(just1,just2))
                .firstOrError().subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> list) throws Exception {
                System.out.println(list.get(0));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
            }
        });



    }


}