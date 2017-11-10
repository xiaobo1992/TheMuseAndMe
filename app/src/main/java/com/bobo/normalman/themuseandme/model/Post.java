package com.bobo.normalman.themuseandme.model;

import java.util.HashMap;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class Post {
    public String KEY_IMAGE = "image";
    public String KEY_LANDING_PAGE = "landing_page";
    public String N_A = "N/A";

    public int id;
    public HashMap<String, String> refs;
    public Author author;
    public boolean liked;
    public String name;


    public String getName() {
        return name;
    }

    public HashMap<String, String> getRefs() {
        return refs;
    }

}
