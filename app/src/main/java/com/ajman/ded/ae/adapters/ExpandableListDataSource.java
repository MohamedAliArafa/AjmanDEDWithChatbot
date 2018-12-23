package com.ajman.ded.ae.adapters;

import android.content.Context;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 10/3/17.
 */

public class ExpandableListDataSource {

    /**
     * Returns fake data of films
     *
     * @param context
     * @return
     */
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new LinkedHashMap<>();

        List<String> filmGenres = Arrays.asList(context.getResources().getStringArray(R.array.film_genre));
        List<String> application = Arrays.asList(context.getResources().getStringArray(R.array.e_services));
        List<String> license = Arrays.asList(context.getResources().getStringArray(R.array.inspection));
        List<String> permit = Arrays.asList(context.getResources().getStringArray(R.array.transactions));
        List<String> other = Arrays.asList(context.getResources().getStringArray(R.array.reports));
        List<String> reports = Arrays.asList(context.getResources().getStringArray(R.array.inquiries));
        List<String> Administrative_Control = Arrays.asList(context.getResources().getStringArray(R.array.Administrative_Control));

        if (UserData.getUserObject(context) != null) {
            expandableListData.put(filmGenres.get(0), application);
            expandableListData.put(filmGenres.get(1), license);
            expandableListData.put(filmGenres.get(2), permit);
            expandableListData.put(filmGenres.get(3), other);
            expandableListData.put(filmGenres.get(4), reports);
            expandableListData.put(filmGenres.get(5), Administrative_Control);
        } else {
            expandableListData.put(filmGenres.get(4), reports);
            expandableListData.put(filmGenres.get(5), Administrative_Control);

        }

        return expandableListData;
    }
}
