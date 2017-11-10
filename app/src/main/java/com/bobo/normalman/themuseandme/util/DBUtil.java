package com.bobo.normalman.themuseandme.util;

import com.bobo.normalman.themuseandme.auth.MyAuth;
import com.bobo.normalman.themuseandme.model.Application;
import com.bobo.normalman.themuseandme.model.Coach;
import com.bobo.normalman.themuseandme.model.Company;
import com.bobo.normalman.themuseandme.model.Job;
import com.bobo.normalman.themuseandme.model.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by xiaobozhang on 11/6/17.
 */

public class DBUtil {
    public static final String COMPANY = "COMPANY";
    public static final String JOB = "JOB";
    public static final String COACH = "COACH";
    public static final String POST = "POST";
    public static final String APPLICATION = "APPLICATION";

    public static void saveCompany(Company company) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(COMPANY);
        ref.child(String.valueOf(company.id)).setValue(company);
    }

    public static void removeCompany(Company company) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(COMPANY);
        ref.child(String.valueOf(company.id)).removeValue();
    }

    public static void saveJob(Job job) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(JOB);
        ref.child(String.valueOf(job.id)).setValue(job);
    }

    public static void removeJob(Job job) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(JOB);
        ref.child(String.valueOf(job.id)).removeValue();
    }

    public static void savePost(Post post) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(POST);
        ref.child(String.valueOf(post.id)).setValue(post);
    }

    public static void removePost(Post post) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(POST);
        ref.child(String.valueOf(post.id)).removeValue();
    }


    public static void saveCoach(Coach coach) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(COACH);
        ref.child(String.valueOf(coach.id)).setValue(coach);
    }

    public static void removeCoach(Coach coach) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(COACH);
        ref.child(String.valueOf(coach.id)).removeValue();
    }

    public static void saveApplication(Application application) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(APPLICATION);
        removeApplication(application);
        ref.child(application.jobId).setValue(application);
    }

    public static void removeApplication(Application application) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(MyAuth.getFireBaseUser().getUid())
                .child(APPLICATION);
        ref.child(application.jobId).removeValue();
    }
}
