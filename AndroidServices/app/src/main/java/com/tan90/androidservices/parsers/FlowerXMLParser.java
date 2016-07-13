package com.tan90.androidservices.parsers;

import com.tan90.androidservices.model.Flower;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class FlowerXMLParser {

    public static List<Flower> parseFeed(String content) {
        List<Flower> flowers = new ArrayList<Flower>();
        try {
            boolean inDataItemTag = false;
            String currentTagName = "";
            Flower flower = null;

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(new StringReader(content));

            int eventtype = pullParser.getEventType();
            while (eventtype != XmlPullParser.END_DOCUMENT) {
                switch (eventtype) {
                    case XmlPullParser.START_TAG:
                        currentTagName = pullParser.getName();
                        if (currentTagName.equals("product")) {
                            inDataItemTag = true;
                            flower = new Flower();
                            flowers.add(flower);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (pullParser.getName().equals("product")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;
                    case XmlPullParser.TEXT:
                        if (inDataItemTag && flower != null) {
                            switch (currentTagName) {
                                case "productId":
                                    flower.setProductId(Integer.parseInt(pullParser.getText()));
                                    break;
                                case "name":
                                    flower.setName(pullParser.getText());
                                    break;
                                case "category":
                                    flower.setCategory(pullParser.getText());
                                    break;
                                case "instructions":
                                    flower.setInstructtions(pullParser.getText());
                                    break;
                                case "photo":
                                    flower.setPhoto(pullParser.getText());
                                    break;
                                case "price":
                                    flower.setPrice(Double.parseDouble(pullParser.getText()));
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                }
                eventtype = pullParser.next();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return flowers;
    }
}
