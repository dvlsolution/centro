package org.centro.adplacement.model;

import lombok.Data;

import java.util.Date;

public @Data class Delivery
{
    private Placement placement;
    private Date date;
    private long impressions;
    private int clicks;
}