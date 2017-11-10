package com.bobo.normalman.themuseandme.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.themuse.TheMuse;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;

/**
 * Created by xiaobozhang on 10/25/17.
 */

public class LoadCompanyImageTask extends AsyncTask<Void, Void, Company> {
    SimpleDraweeView imageView;
    String companyId;
    public LoadCompanyImageTask(SimpleDraweeView imageView, String companyId) {
        this.companyId = companyId;
        this.imageView = imageView;
    }

    @Override
    protected Company doInBackground(Void... voids) {
        try {
            return TheMuse.getCompany(companyId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Company company) {
        if (!TextUtils.isEmpty(company.getLogoImageUrl())) {
            imageView.setImageURI(company.getLogoImageUrl());
        }
    }
}
