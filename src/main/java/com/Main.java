package com;

public class Main {
    public static void main(String[] args) {
        Object[][] data = { {"Julien", "Hugo", "Laura"},
                            {"Age", "24", "23", "23"},
                            {"Domicile", "Coin Paumé", "Grenoble", "Grenoble"},
                            {"Formation", "M1 INFO", "M1 INFO", "M1 INFO"}
                          };

        Dframe dataframe = new Dframe(data);
        dataframe.print();

        Dframe dataframe2 = new Dframe("/home/hugo/Documents/IMAG/M1/DevOps/project/src/main/java/data.csv");
        dataframe2.print();
    }
}
