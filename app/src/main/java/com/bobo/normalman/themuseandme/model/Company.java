package com.bobo.normalman.themuseandme.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class Company {
    public static final String KEY_LOGO_IMAGE = "logo_image";
    public static final String KEY_F1_IMAGE = "f1_image";
    public static final String KEY_F2_IMAGE = "f2_image";
    public static final String KEY_F3_IMAGE = "f3_image";
    public static final String KEY_NAME = "name";
    public static final String N_A = "N/A";

    public int id;
    public String name;
    public String description;
    public HashMap<String, String> refs;
    public List<HashMap<String, String>> locations;
    public List<HashMap<String, String>> industries;
    public boolean liked;
    public String getLogoImageUrl() {
        if (refs.containsKey(KEY_LOGO_IMAGE)) {
            return refs.get(KEY_LOGO_IMAGE);
        } else {
            return "";
        }
    }

    public String getF1ImageUrl() {
        if (refs.containsKey(KEY_F1_IMAGE)) {
            return refs.get(KEY_F1_IMAGE);
        } else {
            return "";
        }
    }

    public String getF2ImageUrl() {
        if (refs.containsKey(KEY_F2_IMAGE)) {
            return refs.get(KEY_F2_IMAGE);
        } else {
            return "";
        }
    }

    public String getF3ImageUrl() {
        if (refs.containsKey(KEY_F3_IMAGE)) {
            return refs.get(KEY_F3_IMAGE);
        } else {
            return "";
        }
    }

    public String getLocation() {
        StringBuffer buffer = new StringBuffer();
        if (locations.size() == 0) {
            return N_A;
        }
        for (HashMap<String, String> location : locations) {
            buffer.append(location.get(KEY_NAME)).append(",").append(" ");
        }
        String res = buffer.toString().trim();
        return res.substring(0, res.length() - 1);
    }

    public String getIndustriesContent() {
        StringBuffer buffer = new StringBuffer();
        for (HashMap<String, String> industry : industries) {
            buffer.append(industry.get(KEY_NAME)).append(",").append(" ");
        }
        String res = buffer.toString().trim();
        if (res.length() == 0) {
            return res;
        } else {
            return res.substring(0, res.length() - 1);
        }
    }
}
