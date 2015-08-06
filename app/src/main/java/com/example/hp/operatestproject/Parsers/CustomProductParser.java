package com.example.hp.operatestproject.Parsers;

import android.util.Xml;

import com.example.hp.operatestproject.Product;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by hp on 28.07.15.
 */
public class CustomProductParser {

    public ArrayList<Product> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readProductList(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList<Product> readProductList(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Product> products = new ArrayList();
        parser.require(XmlPullParser.START_TAG, null, "product_list");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("product")) {

                products.add(readProduct(parser));
            } else {
                skip(parser);
            }
        }
        return products;
    }

    private Product readProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "product");



        int id = Integer.parseInt(parser.getAttributeValue(0));
        String title = null;
        String icon  = null;
        ArrayList<String> screenshots  = null;
        String short_descr = null;
        String description = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("icon")) {
                icon = readIcon(parser);
            } else if (name.equals("screenshots")) {
                screenshots = readScreenshots(parser);
            } else if (name.equals("short_description")){
                short_descr = readShortDescr(parser);
            } else if (name.equals("description")){
                description = readDescription(parser);
            }else {
                skip(parser);
            }
        }
        return new Product(id, title, icon, screenshots, short_descr, description);
    }

    private ArrayList<String> readScreenshots(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<String> screechots = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, null, "screenshots");
        String screen = null;

        while (parser.next() != XmlPullParser.END_TAG ) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("image") ) {
                screen = readImage(parser);
                screechots.add(screen);
            } else{
                skip(parser);
            }
        }


        return screechots;
    }

    private String readImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "image");
        String image = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "image");
        return image;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "description");
        return description;
    }

    private String readShortDescr(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "short_description");
        String short_description = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "short_description");
        return short_description;
    }

    private String readIcon(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "icon");
        String icon = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "icon");
        return icon;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "title");
        return title;
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }



    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
