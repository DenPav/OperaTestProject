package com.example.hp.operatestproject.Parsers;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by hp on 28.07.15.
 */
public class CustomGridIdXMLParser {

    public ArrayList<Integer> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList<Integer> readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Integer> ids = new ArrayList();
        parser.require(XmlPullParser.START_TAG, null, "page");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("view") && parser.getAttributeValue(null, "type").equals("card3")) {
                ids = readListID(parser);
            } else {
                skip(parser);
            }
        }
        return ids;
    }

    private ArrayList<Integer> readListID(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "view");
        Integer id = null;
        ArrayList<Integer> list = new ArrayList();

        while (parser.next() != XmlPullParser.END_TAG ) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item") ) {
                id = readItem(parser);
                list.add(id);
            } else{
                skip(parser);
            }
        }
        return list;
    }

    private Integer readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "item");
        Integer title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "item");
        return title;
    }

    private Integer readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        Integer result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = Integer.parseInt(parser.getText());
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
