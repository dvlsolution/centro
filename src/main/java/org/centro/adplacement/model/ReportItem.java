package org.centro.adplacement.model;

import lombok.Data;

public @Data class ReportItem extends Report
{
    private String name;
    private long csVal;
    private CostStructure cs;

    public double getCost()
    {
        double result = 0;
        if (cs.equals(CostStructure.CPC)) {
            result = getClicks() * csVal;
        } else if (cs.equals(CostStructure.CPM)) {
            result = ((double) getImpressions() / 1000) * csVal;
        }

        return result;
    }
}
