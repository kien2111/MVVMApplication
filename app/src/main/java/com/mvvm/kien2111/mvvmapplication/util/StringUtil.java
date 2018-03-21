package com.mvvm.kien2111.mvvmapplication.util;

import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by WhoAmI on 21/03/2018.
 */

public class StringUtil {
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
}
