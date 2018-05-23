package com.mvvm.kien2111.mvvmapplication.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.Toast;

import com.mvvm.kien2111.mvvmapplication.model.Profile;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by WhoAmI on 21/03/2018.
 */

public final class StringUtil {
    @Nullable
    public static List<Integer> splitToIntList(@Nullable String input) {
        if (input == null) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(input, ",");
        while (tokenizer.hasMoreElements()) {
            final String item = tokenizer.nextToken();
            try {
                result.add(Integer.parseInt(item));
            } catch (NumberFormatException ex) {
                Log.e("ROOM", "Malformed integer list", ex);
            }
        }
        return result;
    }

    /**
     * Joins the given list of integers into a comma separated list.
     *
     * @param input The list of integers.
     * @return Comma separated string composed of integers in the list. If the list is null, return
     * value is null.
     */
    @Nullable
    public static String joinIntoString(@Nullable List<Integer> input) {
        if (input == null) {
            return null;
        }

        final int size = input.size();
        if (size == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(Integer.toString(input.get(i)));
            if (i < size - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static List<String> splitToStringList(String data) {
        return Arrays.asList(StringUtils.split(data,","));
    }

    public static String joinStringoString(List<String> stringlist) {
        return StringUtils.join(stringlist.toArray(),",");
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String getFileNameFromPath(String filePath){
        return filePath.substring(filePath.lastIndexOf("/")+1,filePath.length());
    }

    public long getMediaItemIdFromProvider(Uri providerUri, Context appContext, String fileName) {
        //find id of the media provider item based on filename
        String[] projection = { MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DATA };
        Cursor cursor = appContext.getContentResolver().query(
                providerUri, projection,
                MediaStore.MediaColumns.DATA + "=?", new String[] { fileName },
                null);
        if (null == cursor) {
            return -1;
        }
        long id = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
        }
        cursor.close();
        return id;
    }

    public static String formatDecimal(double param){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0", decimalFormatSymbols);
        return decimalFormat.format(param);
    }

    public static void showToastShort(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
    public static void showToastLong(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

    public static String formatPlace(Profile profile){
        if(profile==null)return "User not supply profile";
        StringBuilder builder = new StringBuilder();
        if(profile.getDistrict()!=null && profile.getDistrict().getNamedist()!=null){
            builder.append(profile.getDistrict().getNamedist());
            builder.append(" ");//add space
        }
        if(profile.getCity()!=null && profile.getCity().getNamecity()!=null){
            builder.append(profile.getCity().getNamecity());
        }
        return builder.toString();
    }
}
