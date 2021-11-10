package org.centro.adplacement;

import org.apache.commons.cli.*;
import org.centro.adplacement.exeption.BrokenInputFileException;
import org.centro.adplacement.model.Delivery;
import org.centro.adplacement.model.Placement;
import org.centro.adplacement.service.AdPlacementImpl;
import org.centro.adplacement.service.ReadInputImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AdPlacementMain
{
    static final int ERROR_CODE_BAD_ARGUMENTS = 1;

    private static final String PLACEMENT = "placement";
    private static final String DELIVERY = "delivery";
    private static final String START_SEARCH_DATE = "start-date";
    private static final String END_SEARCH_DATE = "end-date";

    private static final SimpleDateFormat SEARCH_DATE_DF = new SimpleDateFormat("MM/dd/yyyy");

    private Date parseSearchOption(CommandLine cmd, String searchOptionName) {
        Date endSearch = null;
        String endSearchDate = cmd.getOptionValue(searchOptionName);
        if (endSearchDate != null && endSearchDate.length() > 0) {
            try {
                endSearch = SEARCH_DATE_DF.parse(endSearchDate);
            }
            catch (java.text.ParseException e) {
                System.out.println(String.format("Wrong date format option %s, expected MM/dd/yyyy", searchOptionName));
            }
        }

        return endSearch;
    }

    private CommandLine validate(String[] args) {
        Options options = new Options();

        Option placement = new Option("p", PLACEMENT, true, "placement input file path (required)");
        placement.setRequired(true);
        options.addOption(placement);

        Option delivery = new Option("d", DELIVERY, true, "delivery input file path (required)");
        delivery.setRequired(true);
        options.addOption(delivery);

        Option startDate = new Option("s", START_SEARCH_DATE, true, "start search date MM/dd/yyyy (optional)");
        startDate.setRequired(false);
        options.addOption(startDate);

        Option endDate = new Option("e", END_SEARCH_DATE, true, "end search date MM//dd/yyyy (optional)");
        endDate.setRequired(false);
        options.addOption(endDate);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("AdPlacement", options);

            exit(ERROR_CODE_BAD_ARGUMENTS);
        }

        return cmd;
    }

    public void execute(String... args) {
        CommandLine cmd = validate(args);

        // small guard to exit, used in tests
        if (cmd == null) {
            return;
        }

        String placementFilePath = cmd.getOptionValue(PLACEMENT);
        String deliveryFilePath = cmd.getOptionValue(DELIVERY);

        Date startSearch = parseSearchOption(cmd, START_SEARCH_DATE);
        Date endSearch = parseSearchOption(cmd, END_SEARCH_DATE);

        ReadInputImpl placementInput = new ReadInputImpl(placementFilePath);
        ReadInputImpl deliveryInput = new ReadInputImpl(deliveryFilePath);

        AdPlacementImpl output = new AdPlacementImpl();
        try {
            Map<Long, Placement> placements = placementInput.readPlacementsInput();
            List<Delivery> deliveries = deliveryInput.readDeliveryInput(placements);

            if (startSearch != null && endSearch != null) {
                output.calcNumberOfImpressions(deliveries, startSearch, endSearch);
            }
            else {
                output.generateReport(deliveries);
            }
        }
        catch (BrokenInputFileException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void exit(int errorCode) {
        System.exit(errorCode);
    }

    public static void main(String[] args) {
        new AdPlacementMain().execute(args);
    }
}
