package org.centro.adplacement.model;

import lombok.Data;

import java.util.Date;

public @Data class Placement
{
    private long id;
    private String name;
    private Date start;
    private Date end;
    private long cp;
    private CostStructure cs;

    public void setCpc(String cpcStr)
    {
        if (cpcStr != null && cpcStr.trim().length() > 0) {
            this.cs = CostStructure.CPC;
            this.cp = Long.parseLong(cpcStr);
        }
    }

    public void setCpm(String cpmStr)
    {
        if (cpmStr != null && cpmStr.trim().length() > 0) {
            this.cs = CostStructure.CPM;
            this.cp = Long.parseLong(cpmStr);
        }
    }
}