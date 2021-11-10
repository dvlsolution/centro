package org.centro.adplacement.model;

import lombok.Data;

import java.util.Date;

public @Data class Report
{
    private Date startDate;
    private Date endDate;
    private long clicks;
    private long impressions;
}
