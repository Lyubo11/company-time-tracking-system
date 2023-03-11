package com.company.timecompany.constants;

public enum ProjectStatus {
    ACTIVE ("Active"),
    COMPLETED ("Completed"),
    CANCELLED ("Cancelled");
    private String stat;

    ProjectStatus(String stat) {
        this.stat = stat;
    }

    public String getStat() {
        return stat;
    }
}
