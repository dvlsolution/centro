package org.centro.adplacement.model;

enum CostStructure
{
    CPM("CPM"),
    CPC("CPC");

    private String costType;

    CostStructure(String costType)
    {
        this.costType = costType;
    }

    public String toString() {
        return costType;
    }
}
