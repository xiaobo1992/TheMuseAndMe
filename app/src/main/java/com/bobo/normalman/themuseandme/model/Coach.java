package com.bobo.normalman.themuseandme.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class Coach {
    public static final String N_A = "N/A";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LINK = "link";

    public HashMap<String, String> refs;
    public String name;
    public String bio;
    public int id;
    public int reviews;
    public double rating;
    public List<HashMap<String, String>> specializations;
    public List<Product> products;
    public boolean liked;

    public String getSpecializationsContent() {

        if (specializations == null) {
            return N_A;
        }
        StringBuffer buffer = new StringBuffer();

        for (HashMap<String, String> specialization : specializations) {
            buffer.append(specialization.get(KEY_NAME)).append("/");
        }
        return buffer.toString();
    }

    public String getProfileImage() {
        if (!refs.containsKey(KEY_IMAGE)) {
            return N_A;

        }
        return refs.get(KEY_IMAGE);
    }

    public String getLink() {
        if (refs.containsKey(KEY_LINK)) {
            return refs.get(KEY_LINK);
        } else {
            return "";
        }
    }

    public String getRatingString() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(rating);
    }

}
