package org.centro.adplacement.service;

import org.centro.adplacement.model.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdPlacementImpl implements AdPlacement
{
    private static final SimpleDateFormat DELIVERY_DATE_DF = new SimpleDateFormat("MM/d/yyyy");

    // prints report output in the following format:
    // Sports (11/1/2020-11/30/2020): 1,083,576 impressions @ $5 CPM = $5,418
    private void printReportItem(ReportItem item) {
        System.out.println(
                String.format(
                        "%s (%s-%s): %,d impressions @ $%d %s = $%,.0f",
                        item.getName(), DELIVERY_DATE_DF.format(item.getStartDate()),
                        DELIVERY_DATE_DF.format(item.getEndDate()), item.getImpressions(),
                        item.getCsVal(), item.getCs(), item.getCost()
                ));
    }

    // prints search result in the following format:
    // Total (11/22/2020-12/5/2020): 1,126,785 impressions, $6,061
    private void printSearchItem(SearchItem item) {
        System.out.println(
                String.format(
                        "Total (%s-%s): %,d impressions, $%,.0f",
                        DELIVERY_DATE_DF.format(item.getStartDate()),
                        DELIVERY_DATE_DF.format(item.getEndDate()),
                        item.getImpressions(), item.getCost()
                ));
    }

    @Override
    public void generateReport(final List<Delivery> deliveries)
    {
        Map<Long, ReportItem> reportItemMap = new HashMap<>();
        for (Delivery delivery : deliveries) {
            Placement placement = delivery.getPlacement();
            if (reportItemMap.containsKey(placement.getId())) {
                ReportItem item = reportItemMap.get(placement.getId());
                item.setClicks(item.getClicks() + delivery.getClicks());
                item.setImpressions(item.getImpressions() + delivery.getImpressions());
            } else {
                ReportItem item = new ReportItem();
                item.setName(placement.getName());
                item.setCs(placement.getCs());
                item.setCsVal(placement.getCp());
                item.setStartDate(placement.getStart());
                item.setEndDate(placement.getEnd());
                item.setClicks(delivery.getClicks());
                item.setImpressions(delivery.getImpressions());

                reportItemMap.put(placement.getId(), item);
            }
        }

        for (Long key : reportItemMap.keySet()) {
            printReportItem(reportItemMap.get(key));
        }
    }

    @Override
    public void calcNumberOfImpressions(final List<Delivery> deliveries, final Date startDate, final Date endDate)
    {
        final SearchItem searchItem = new SearchItem();
        searchItem.setStartDate(startDate);
        searchItem.setEndDate(endDate);

        for (Delivery delivery : deliveries) {
            Date date = delivery.getDate();
            // we are including start and end dates such as start <= date <= end
            if (!(date.before(startDate) || date.after(endDate))) {
                long impressions = delivery.getImpressions();
                long cpm = delivery.getPlacement().getCp();

                searchItem.setCost(searchItem.getCost() + ((double) impressions / 1000) * cpm);
                searchItem.setImpressions(searchItem.getImpressions() + impressions);
            }
        }

        printSearchItem(searchItem);
    }
}
