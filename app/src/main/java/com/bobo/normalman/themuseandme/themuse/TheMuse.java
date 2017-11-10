package com.bobo.normalman.themuseandme.themuse;

import android.content.res.Resources;

import com.bobo.normalman.themuseandme.BuildConfig;
import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.model.Job;
import com.bobo.normalman.themuseandme.model.Post;
import com.bobo.normalman.themuseandme.model.Result;
import com.bobo.normalman.themuseandme.util.ModelUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class TheMuse {
    public static final String KEY_API = "API";
    public static final String KEY_PAGE = "page";
    public static final String KEY_COMPANY = "company";

    public static final String API_KEY = BuildConfig.THE_MUSE_API_KEY;
    public static final String SCHEMA = "https";

    public static final String BASE_URL = "api-v2.themuse.com";
    public static final String JOBS_SEGMENT = "jobs";
    public static final String COMPANIES_SEGMENT = "companies";
    public static final String POSTS_SEGMENT = "posts";
    public static final String COACHES_SEGMENT = "coaches";


    public static Request buildGetRequest(HttpUrl httpUrl) {
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        return request;
    }

    public static Response makeRequest(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }

    public static <T> T parseResponse(Response response, TypeToken<T> token) {
        try {
            String res = response.body().string();
            //Log.d("res", res);
            return ModelUtil.toObject(res, token);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Job> getJobs(int page) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(BASE_URL)
                .addPathSegment(JOBS_SEGMENT)
                .addQueryParameter(KEY_API, API_KEY)
                .addQueryParameter(KEY_PAGE, String.valueOf(page))
                .build();
        Result<Job> jobResult = parseResponse(makeRequest(buildGetRequest(httpUrl)),
                new TypeToken<Result<Job>>() {
                });
        return jobResult.results;
    }

    public static List<Company> getCompanies(int page) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(BASE_URL)
                .addPathSegment(COMPANIES_SEGMENT)
                .addQueryParameter(KEY_API, API_KEY)
                .addQueryParameter(KEY_PAGE, String.valueOf(page))
                .build();
        Result<Company> jobResult = parseResponse(makeRequest(buildGetRequest(httpUrl)),
                new TypeToken<Result<Company>>() {
                });
        return jobResult.results;
    }


    public static List<Post> getPosts(int page) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(BASE_URL)
                .addPathSegment(POSTS_SEGMENT)
                .addQueryParameter(KEY_API, API_KEY)
                .addQueryParameter(KEY_PAGE, String.valueOf(page))
                .build();
        Result<Post> jobResult = parseResponse(makeRequest(buildGetRequest(httpUrl)),
                new TypeToken<Result<Post>>() {
                });
        return jobResult.results;
    }


    public static List<Coach> getCoaches(int page) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(BASE_URL)
                .addPathSegment(COACHES_SEGMENT)
                .addQueryParameter(KEY_API, API_KEY)
                .addQueryParameter(KEY_PAGE, String.valueOf(page))
                .build();
        Result<Coach> jobResult = parseResponse(makeRequest(buildGetRequest(httpUrl)),
                new TypeToken<Result<Coach>>() {
                });
        return jobResult.results;
    }

    public static Company getCompany(String companyId) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(BASE_URL)
                .addPathSegment(COMPANIES_SEGMENT)
                .addPathSegment(companyId)
                .addQueryParameter(KEY_API, API_KEY)
                .build();
        Company company = parseResponse(makeRequest(buildGetRequest(httpUrl)),
                new TypeToken<Company>() {
                });
        return company;
    }

    public static List<Job> getJobsByCompany(String company, int page) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(BASE_URL)
                .addPathSegment(JOBS_SEGMENT)
                .addQueryParameter(KEY_COMPANY, company)
                .addQueryParameter(KEY_API, API_KEY)
                .addQueryParameter(KEY_PAGE, String.valueOf(page))
                .build();
        Result<Job> jobResult = parseResponse(makeRequest(buildGetRequest(httpUrl)),
                new TypeToken<Result<Job>>() {
                });
        return jobResult.results;
    }
}
