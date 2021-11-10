package org.centro.adplacement.service;

import org.centro.adplacement.model.Delivery;

import java.util.Date;
import java.util.List;

public interface AdPlacement
{
    void generateReport(final List<Delivery> deliveries);
    void calcNumberOfImpressions(final List<Delivery> deliveries, final Date start, final Date end);
}
