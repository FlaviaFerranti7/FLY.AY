package com.flam.flyay.util;

import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.flam.flyay.model.Event;
import com.flam.flyay.model.StatusResponse;
import com.flam.flyay.services.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Utils {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getTimeToString(Event e) {

        Map<String, Object> valueEvent = e.getValueEvent();

        if(valueEvent.get("startingTime") != null && valueEvent.get("endTime") != null) {
            return getTimeToString((Double) valueEvent.get("startingTime"), (Double) valueEvent.get("endTime"));
        }

        return "all day";
    }

    public static String getTimeToString(double startingTimeInput, double endTimeInput) {

        String startingTime = convertionFromDoubleToTime(startingTimeInput, ':');
        String endTime = convertionFromDoubleToTime(endTimeInput, ':');

        return startingTime + " - " + endTime;
    }

    public static String convertionFromDoubleToTime(double input, char separator) {
        String doubleString = Double.toString(input);
        String hours = doubleString.substring(0,2);
        String minutes = doubleString.substring(3);

        if(minutes.length() == 1)
            minutes += '0';

        return hours + separator + minutes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static void merge(Event[] events, String property, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        Event[] L = new Event[n1];
        Event[] R = new Event[n2];

        for( int i = 0; i < n1; i ++)
            L[i] = events[l + i];
        for (int j = 0; j < n2; j ++)
            R[j] = events[m + 1 + j];


        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            Double firstProperty = (Double) L[i].getValueEvent().get(property);
            Double secondProperty = (Double) R[j].getValueEvent().get(property);

            if (firstProperty != null && (secondProperty == null || firstProperty <= secondProperty)) {
                events[k] = L[i];
                i++;
            }
            else {
                events[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            events[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            events[k] = R[j];
            j++;
            k++;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static void sort(Event[] events, String property, int l, int r)
    {
        if (l < r) {
            int m = (l + r) / 2;
            System.out.println(m);
            sort(events, property, l, m);
            sort(events, property,m + 1, r);

            merge(events, property, l, m, r);
        }
    }

    private static void clearList(List<?> list) {
        int size = list.size();
        if (size > 0) {
            list.subList(0, size).clear();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void orderEventListBy(List<Event> events, String property) {
        Log.d(".Utils.orderEventListBy", "start ordering");
        System.out.println(events);
        Event[] eventsArray = new Event[events.size()];
        for(int i = 0; i < events.size(); i ++)
            eventsArray[i] = events.get(i);
        sort(eventsArray, property, 0, events.size() - 1);
        clearList(events);
        events.addAll(Arrays.asList(eventsArray));
    }

    public static void getStatusResponse(JSONObject requestResult, final ServerCallback callback) {
        JSONObject containerResponse;
        String status = "";

        StatusResponse response = new StatusResponse();

        try {
            containerResponse = requestResult.getJSONObject("return");
            status = containerResponse.getString("status");

            response.setStatus(status);
            if(status.equalsIgnoreCase("KO")) {
                response.setMessage(containerResponse.getString("message"));
            }

            Log.d("response: ", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callback.onSuccess(response);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isEmptyOrBlank(String s){
        return Objects.isNull(s) || s.length() == 0;
    }

    public static int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String capitalize(String str) {
        if(Objects.isNull(str) || str.length() == 0)
            return "";
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String buildPropertyName(String nameProperty, char finalCharacter) {
        List<String> namePropertyList = new ArrayList<>();
        StringBuilder ret = new StringBuilder();
        int lastPointUsed = 0;

        for(int i = 1; i < nameProperty.length(); i ++) {
            if(nameProperty.charAt(i) >= 'A' && nameProperty.charAt(i) <= 'Z') {
                namePropertyList.add(nameProperty.substring(lastPointUsed, i));
                lastPointUsed = i;
            }
        }

        namePropertyList.add(nameProperty.substring(lastPointUsed));

        for(String word: namePropertyList) {
            ret.append(capitalize(word)).append(" ");
        }
        return ret.toString() + finalCharacter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String convertionFromDateToString(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date);
    }

}
