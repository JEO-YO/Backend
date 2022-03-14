package com.unittest.recentsearch;

import android.view.View;

import java.util.ArrayList;

public interface ClickListener {

    void onDeleteClicked(View v, int position, ArrayList<String> histories);

//    boolean onQuoteDeleted(String deleteString);

}
