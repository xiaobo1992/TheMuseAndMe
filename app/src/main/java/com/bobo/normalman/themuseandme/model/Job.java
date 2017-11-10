package com.bobo.normalman.themuseandme.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class Job {
    public String KEY_NAME = "name";
    public String KEY_ID = "id";
    public String KEY_LANDING_PAGE = "landing_page";
    public String N_A = "N/A";

    public int id;
    public String name;
    public String contents;
    public List<HashMap<String, String>> locations;
    public List<HashMap<String, String>> levels;
    public HashMap<String, String> refs;
    public HashMap<String, String> company;
    public boolean liked;
    public String getCompanyName() {
        if (company.containsKey(KEY_NAME)) {
            return company.get(KEY_NAME);
        } else {
            return N_A;
        }
    }

    public String getCompanyID() {
        if (company.containsKey(KEY_ID)) {
            return company.get(KEY_ID);
        } else {
            return N_A;
        }
    }

    public String getRefLandingPage() {
        if (refs.containsKey(KEY_LANDING_PAGE)) {
            return refs.get(KEY_LANDING_PAGE);
        } else {
            return N_A;
        }
    }

    public String getLevelName() {
        if (levels.size() == 0) {
            return N_A;
        }

        String levelName = "";
        for (HashMap<String, String> level : levels) {
            if (level.containsKey(KEY_NAME)) {
                levelName = level.get(KEY_NAME);
            }
        }
        return levelName;
    }

    public String getLocation() {
        StringBuffer buffer = new StringBuffer();
        if (locations == null || locations.size() == 0) {
            return N_A;
        }
        for (HashMap<String, String> location : locations) {
            buffer.append(location.get(KEY_NAME)).append(",").append(" ");
        }
        String res = buffer.toString().trim();
        return res.substring(0, res.length() - 1);
    }
}
