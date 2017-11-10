package com.bobo.normalman.themuseandme.model;

import java.util.List;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class Result<T> {
    private int page;
    private int pageCount;
    private int total;
    public List<T> results;
}
