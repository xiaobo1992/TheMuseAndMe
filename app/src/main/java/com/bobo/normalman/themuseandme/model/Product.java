package com.bobo.normalman.themuseandme.model;

import java.util.HashMap;

/**
 * Created by xiaobozhang on 11/2/17.
 */

public class Product {
    String id;
    int purchases_remaining;
    HashMap<String, String> product;
    HashMap<String, String> offering;
    public String KEY_MENU = "menu";
    public String KEY_PRICE = "price";
    public String KEY_DESCRIPTION = "description";
    public String KEY_NAME = "name";
    public String N_A = "N/A";

    public String getProductMenu() {
        if (!product.containsKey(KEY_MENU)) {
            return N_A;
        }
        return product.get(KEY_MENU);
    }

    public String getProductPrice() {
        if (!product.containsKey(KEY_PRICE)) {
            return N_A;
        }
        return product.get(KEY_PRICE);
    }

    public String getOfferDescription() {
        if (!offering.containsKey(KEY_DESCRIPTION)) {
            return N_A;
        }
        return offering.get(KEY_DESCRIPTION);
    }

    public String getOfferName() {
        if (!offering.containsKey(KEY_NAME)) {
            return N_A;
        }
        return offering.get(KEY_NAME);
    }
}
