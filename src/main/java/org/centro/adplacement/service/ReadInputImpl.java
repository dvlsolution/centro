package org.centro.adplacement.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.centro.adplacement.exeption.BrokenInputFileException;
import org.centro.adplacement.model.Delivery;
import org.centro.adplacement.model.Placement;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadInputImpl implements ReadInput
{
    private static final String BROKEN_INPUT_FILE = "Broken input file %s";
    private static final String BROKEN_LINE_IN_FILE = "Wrong line format";

    private static final Character DEFAULT_DELIMITER = ',';
    private static final String DEFAULT_RECORDS_SEPARATOR = "\r\n";

    private static final SimpleDateFormat DELIVERY_DATE_DF = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat PLACEMENTS_DATE_DF = new SimpleDateFormat("MM/dd/yy");

    private final String fileName;
    private final Character delimiter;
    private final String recordSeparator;

    public ReadInputImpl(String fileName)
    {
        this(fileName, DEFAULT_DELIMITER, DEFAULT_RECORDS_SEPARATOR);
    }

    public ReadInputImpl(String fileName, Character delimiter, String recordSeparator)
    {
        this.fileName = fileName;
        this.delimiter = delimiter;
        this.recordSeparator = recordSeparator;
    }

    private CSVFormat initializeFormat() {
        final CSVFormat format = CSVFormat.DEFAULT;
        format.withDelimiter(delimiter);
        format.withRecordSeparator(recordSeparator);
        format.withIgnoreEmptyLines();
        format.withIgnoreSurroundingSpaces();
        format.withFirstRecordAsHeader();
        format.withIgnoreHeaderCase();
        format.withAllowDuplicateHeaderNames();
        format.withTrim();

        return format;
    }

    private int parseString(String str) {
        if (str == null || str.trim().length() == 0) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    public Map<Long, Placement> readPlacementsInput() throws BrokenInputFileException {
        final Map<Long, Placement> placements = new HashMap();

        final CSVFormat format = initializeFormat();
        try (Reader reader = Files.newBufferedReader(Paths.get(fileName));
             CSVParser csvParser = new CSVParser(reader, format))
        {
            // skip the first line, it's a columns definition
            Iterator<CSVRecord> iterator = csvParser.iterator();
            iterator.next();

            // iterate the rest
            while (iterator.hasNext()) {
                // lets parse the entire file even if one row is broken
                try {
                    // csv format: id,name,start,end,cpm
                    CSVRecord csvRecord = iterator.next();

                    long placementId = Long.parseLong(csvRecord.get(0));

                    Placement placement = new Placement();
                    placement.setId(placementId);
                    placement.setName(csvRecord.get(1));
                    placement.setStart(PLACEMENTS_DATE_DF.parse(csvRecord.get(2)));
                    placement.setEnd(PLACEMENTS_DATE_DF.parse(csvRecord.get(3)));
                    placement.setCpm(csvRecord.get(4));
                    placement.setCpc(csvRecord.get(5));

                    placements.put(placementId, placement);
                }
                catch (ParseException | NumberFormatException e) {
                    System.out.println(BROKEN_LINE_IN_FILE + e.getLocalizedMessage());
                }
            }
        }
        catch (IOException e) {
            throw new BrokenInputFileException(String.format(BROKEN_INPUT_FILE, fileName), e);
        }

        return placements;
    }

    public List<Delivery> readDeliveryInput(Map<Long, Placement> placements) throws BrokenInputFileException {
        final List<Delivery> deliveries = new ArrayList<>();

        final CSVFormat format = initializeFormat();
        try (Reader reader = Files.newBufferedReader(Paths.get(fileName));
             CSVParser csvParser = new CSVParser(reader, format))
        {
            // skip the first line, it's a columns definition
            Iterator<CSVRecord> iterator = csvParser.iterator();
            iterator.next();

            // iterate the rest
            while (iterator.hasNext()) {
                // lets parse the entire file even if one row is broken
                try {
                    // csv format: placement_id,date,impressions
                    CSVRecord csvRecord = iterator.next();

                    long placementId = Long.parseLong(csvRecord.get(0));
                    Placement placement = placements.get(placementId);

                    Delivery delivery = new Delivery();
                    delivery.setPlacement(placement);
                    delivery.setDate(DELIVERY_DATE_DF.parse(csvRecord.get(1)));
                    delivery.setImpressions(parseString(csvRecord.get(2)));
                    delivery.setClicks(parseString(csvRecord.get(3)));

                    deliveries.add(delivery);
                }
                catch (ParseException | NumberFormatException e) {
                    System.out.println(BROKEN_LINE_IN_FILE + e.getLocalizedMessage());
                }
            }
        }
        catch (IOException e) {
            throw new BrokenInputFileException(String.format(BROKEN_INPUT_FILE, fileName), e);
        }

        return deliveries;
    }
}
