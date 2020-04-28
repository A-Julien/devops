package com;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import  Exception.CsvException;
import  Exception.FrameOutOfBound;
import  Exception.IndexException;

public class TestBase {
    private Object[][] data = null;

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
    public void testOpeningFile() throws CsvException {
        new Dframe("src/main/resources/data.csv").to_string();
    }

    @Test(expected = CsvException.class)
    public void testDframeExcéption() throws CsvException {
        new Dframe("bad/path/data.csv");
    }

    @Test
    public void testOpeningDataFrame() {
        Dframe dframe = new Dframe(this.data);
        System.out.println(Arrays.toString(dframe.getColumns()));
    }

    @Test
    public void testHead(){
        Dframe dframe = new Dframe(this.data);
        for (int i = 1; i < 3; ++i) assertEquals(i + 1, dframe.head(i).split("\n").length);
        assertTrue(dframe.head(1).contains("Julien"));
        assertFalse(dframe.head(1).contains("Hugo"));
        assertFalse(dframe.head(1).contains("Laura"));
    }

    @Test
    public void testTail(){
        Dframe dframe = new Dframe(this.data);
        for (int i = 1; i < 3; ++i) assertEquals(i + 1, dframe.tail(i).split("\n").length);

        assertFalse(dframe.tail(1).contains("Julien"));
        assertFalse(dframe.tail(1).contains("Hugo"));
        assertTrue(dframe.tail(1).contains("Laura"));
    }

    @Test(expected = FrameOutOfBound.class)
    public void testIloc() throws FrameOutOfBound {
        Dframe dframe = new Dframe(this.data);
        dframe.iloc(10);
    }

    @Test
    public void testIloc2() throws FrameOutOfBound {
        Dframe dframe = new Dframe(this.data);
        Dframe dframe1 = dframe.iloc(1);
        System.out.println(dframe1.toString());
    }

    @Test
    public void testLoc() throws IndexException {
        Dframe dframe = new Dframe(this.data);
        Dframe dframe1 = dframe.loc("Age");
        assertEquals(Integer.parseInt((String) dframe1.getData()[0][0]), 24);
        assertEquals(Integer.parseInt((String) dframe1.getData()[1][0]), 23);
        assertEquals(Integer.parseInt((String) dframe1.getData()[2][0]), 23);
    }

    @Test(expected = IndexException.class)
    public void testLoc2() throws IndexException {
        Dframe dframe = new Dframe(this.data);
        dframe.loc("Doc");
    }

    @Ignore
    @Test
    public void testToString(){
        Dframe dframe = new Dframe(this.data);
        dframe.to_string();
    }
}