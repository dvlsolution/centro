package org.centro.adplacement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class AdPlacementMainTest
{
    private AdPlacementMain app;
    private int errorCode;
    private PrintStream console;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Before
    public void setup()
    {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.console = System.out;
        System.setOut(new PrintStream(this.byteArrayOutputStream));

        app = new AdPlacementMain()
        {
            @Override
            public void exit(int errorCode)
            {
                AdPlacementMainTest.this.errorCode = errorCode;
            }
        };
    }

    @After
    public void tearDown()
    {
        System.setOut(console);
    }

    private String getAbsoluteFilePath(String fileName) {
        return new File(fileName).getAbsolutePath();
    }

    @Test
    public void shouldPrintHelp() {
        app.execute();
        assertEquals(AdPlacementMain.ERROR_CODE_BAD_ARGUMENTS, errorCode);
    }

    @Test
    public void shouldReturnFullReport() {
        final String expectedResult = "Sports (11/1/2020-11/30/2020): 1,083,576 impressions @ $5 CPM = $5,418\n" +
                "Business (12/1/2020-12/31/2020): 1,607,958 impressions @ $8 CPM = $12,864\n" +
                "Travel (11/1/2020-11/30/2020): 1,035,966 impressions @ $3 CPM = $3,108\n" +
                "Politics (12/1/2020-12/31/2020): 1,529,821 impressions @ $6 CPM = $9,179\n";

        app.execute("-d", getAbsoluteFilePath("delivery.csv"), "-p", getAbsoluteFilePath("placements.csv"));
        assertThat(byteArrayOutputStream.toString(), is(expectedResult));
    }

    @Test
    public void shouldReturnSearchTotal() {
        final String expectedResult = "Total (11/22/2020-12/5/2020): 1,126,785 impressions, $6,061\n";

        app.execute("-d", getAbsoluteFilePath("delivery.csv"), "-p", getAbsoluteFilePath("placements.csv"), "-s", "11/22/2020", "-e", "12/5/2020");
        assertThat(byteArrayOutputStream.toString(), is(expectedResult));
    }
}
