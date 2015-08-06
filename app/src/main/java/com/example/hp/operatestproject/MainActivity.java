package com.example.hp.operatestproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hp.operatestproject.Parsers.CustomGridIdXMLParser;
import com.example.hp.operatestproject.Parsers.CustomListIdXMLparser;
import com.example.hp.operatestproject.Parsers.CustomProductParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Denis Pavlovsky on 25.07.15.
 */
public class MainActivity extends Activity {

    private ArrayList<Integer> listID = new ArrayList<>();
    private ArrayList<Integer> cardID = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Product> chosenProductsList = new ArrayList<>();
    private ArrayList<Product> chosenProductsGrid = new ArrayList<>();

    private RecyclerView listRecyclerView;
    private LinearLayoutManager listLayoutManager;

    private String TAG = MainActivity.class.getSimpleName();

    private RecyclerListAdapter adapter;
    private RecyclerGridAdapter gridAdapter;
    private RecyclerView gridRecyclerView;
    private final int COLUMN_COUNT = 3;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "XMLTask start");

        new XMLTask().execute();
    }

    private void loadListXMLids () throws IOException {
        Log.e(TAG, "loadListXMLids START");
        InputStream stream = getResources().openRawResource(R.raw.products_ids);
        CustomListIdXMLparser parser = new CustomListIdXMLparser ();
        try {
            listID = parser.parse(stream);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
    }

    private void loadGridXMLids () throws IOException {
        Log.e(TAG, "loadGridXMLids START");
        InputStream stream = getResources().openRawResource(R.raw.products_ids);
        CustomGridIdXMLParser parser = new CustomGridIdXMLParser ();
        try {
            cardID = parser.parse(stream);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
    }

    private void loadProductsXML () throws IOException {
        Log.e(TAG, "loadProductsXML START");
        InputStream stream = getResources().openRawResource(R.raw.products);
        CustomProductParser parser = new CustomProductParser ();
        try {
            products = parser.parse(stream);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
    }

    private void chooseProductsList (ArrayList<Product> list, ArrayList<Integer> ids) {
        Log.d(TAG, "chooseProductsList start");
        for (int i = 0; i < ids.size(); i++) {
            for (int j = 0; j < list.size(); j++){
                if(ids.get(i) == list.get(j).getId()) {
                    chosenProductsList.add(list.get(j));
                }
            }
        }
        Log.d(TAG, "chooseProductsList finish");
    }

    private void chooseProductsGrid (ArrayList<Product> list, ArrayList<Integer> ids) {
        Log.d(TAG, "chooseProductsGrid start");
        for (int i = 0; i < ids.size(); i++) {
            for (int j = 0; j < list.size(); j++){
                if(ids.get(i) == list.get(j).getId()) {
                    chosenProductsGrid.add(list.get(j));
                }
            }
        }
        Log.d(TAG, "chooseProductsGrid finish");
    }

    private  void contentSet () {
        setContentView(R.layout.activity_main2);

        Log.e(TAG, "Layout CHANGE");
        adapter = new RecyclerListAdapter(chosenProductsList);
        listRecyclerView = (RecyclerView) findViewById(R.id.recycler_list_view);
        listRecyclerView.setAdapter(adapter);
        listLayoutManager = new LinearLayoutManager(getApplicationContext());
        listLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listRecyclerView.setLayoutManager(listLayoutManager);
        listRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(view.getContext(), ProductActivity.class);

                        intent.putExtra(Constants.ID, chosenProductsList.get(position).getId());
                        intent.putExtra(Constants.TITLE, chosenProductsList.get(position).getTitle());
                        intent.putExtra(Constants.ICON, chosenProductsList.get(position).getIcon());
                        intent.putExtra(Constants.SCREENSHOTS, chosenProductsList.get(position).getScreenshots());
                        intent.putExtra(Constants.SHORT_DESCRIPTION, chosenProductsList.get(position).getShort_descr());
                        intent.putExtra(Constants.FULL_DESCRIPTION, chosenProductsList.get(position).getDescription());

                        startActivity(intent);
                    }
                })
        );


        gridAdapter = new RecyclerGridAdapter(chosenProductsGrid, this);
        gridRecyclerView = (RecyclerView) findViewById(R.id.recycler_grid_view);
        gridRecyclerView.setAdapter(gridAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), COLUMN_COUNT);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(view.getContext(), ProductActivity.class);

                        intent.putExtra(Constants.ID, chosenProductsList.get(position).getId());
                        intent.putExtra(Constants.TITLE, chosenProductsList.get(position).getTitle());
                        intent.putExtra(Constants.ICON, chosenProductsList.get(position).getIcon());
                        intent.putExtra(Constants.SCREENSHOTS, chosenProductsList.get(position).getScreenshots());
                        intent.putExtra(Constants.SHORT_DESCRIPTION, chosenProductsList.get(position).getShort_descr());
                        intent.putExtra(Constants.FULL_DESCRIPTION, chosenProductsList.get(position).getDescription());

                        startActivity(intent);
                    }
                })
        );
    }


    private class XMLTask extends AsyncTask<Void, Void, String>  {

        @Override
        protected String doInBackground(Void... params) {
            try {
                loadListXMLids();
                loadGridXMLids();
                loadProductsXML();
                chooseProductsGrid(products, cardID);
                chooseProductsList(products, listID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute");

            contentSet();
        }
    }
}
