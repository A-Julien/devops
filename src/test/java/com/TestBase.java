package com;

import static junit.framework.Assert.*;

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
    }

    @Test
    public void testOpeningDataFrame() {
        Dframe dframe = new Dframe(this.data);
        System.out.println(Arrays.toString(dframe.getColumns()));
    }

    @Test
    public void testHead(){
        Dframe dframe = new Dframe(this.data);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < 3; ++i) {
            assertEquals(i + 1, dframe.head(i).split("\n").length);
            //dframe.head(i).
        }
       // System.out.println(this.data[0][1]);
    }

    @Test
    public void testTail(){
        Dframe dframe = new Dframe(this.data);
        for (int i = 1; i < 3; ++i) assertEquals(i + 1, dframe.tail(i).split("\n").length);
    }

    @Test
    public void testIloc(){
        Dframe dframe = new Dframe(this.data);
        Dframe dframe1 = dframe.iloc(10);
        assertNull(dframe1);
    }

    @Test
    public void testIloc2(){
        Dframe dframe = new Dframe(this.data);
        Dframe dframe1 = dframe.iloc(1);
        System.out.println(dframe1.toString());
    }

    @Test
    public void testLoc(){
        Dframe dframe = new Dframe(this.data);
        Dframe dframe1 = dframe.loc("Age");
        assertEquals(Integer.parseInt((String) dframe1.data[0][0]), 24);
        assertEquals(Integer.parseInt((String) dframe1.data[1][0]), 23);
        assertEquals(Integer.parseInt((String) dframe1.data[2][0]), 23);
    }
}