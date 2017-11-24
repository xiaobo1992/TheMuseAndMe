package com.bobo.normalman.themuseandme.model;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class Application {
    public enum Status {
        Wait("Wait"),
        Applied("Applied"),
        Review("Reviewed"),
        Interview("In Interview"),
        Accept("Accept"),
        Reject("Reject");
        String name;
        Status(String name) {
            this.name = name;
        }
    }
    public String jobId;
    public long jobApplicationDate;
    public String jobStatus;
    public String comment;
    public Job job;

    public void setJobStatus(Status status) {
        jobStatus = status.name;
    }
}
