package com;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestBase {
    Object[][] data = null;

    @Before
    public void init(){
        this.data = new Object[][]{
                {"Julien", "Hugo", "Laura"},
                {"Age", "24", "23", "23"},
                {"Domicile", "Coin Paumé", "Grenoble", "Grenoble"},
                {"Formation", "M1 INFO", "M1 INFO", "M1 INFO"}
        };
    }

    @Test
    public void testOpeningFile() {
        Dframe dframe = new Dframe("src/main/resources/data.csv");
        Assert.assertEquals(data, dframe.data);
    }

    @Test
    public void testOpeningDataFrame() {
        Dframe dframe = new Dframe(this.data);
        System.out.println(Arrays.toString(dframe.getColumns()));
    }
}
