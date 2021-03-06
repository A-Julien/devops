package com;


import java.io.IOException;
import Exception.*;

@Generated
public class Main {
    public static void main(String[] args) throws IOException, CsvException, FrameOutOfBound, IndexException {
        Object[][] data = { {"Julien", "Hugo", "Laura"},
                            {"Age", "24", "23", "23"},
                            {"Domicile", "Coin Paumé", "Grenoble", "Grenoble"},
                            {"Formation", "M1 INFO", "M1 INFO", "M1 INFO"}
                          };
        Dframe dataframe = new Dframe(data);
        System.out.println("On affiche le dataframe obtenu via un tableau : ");
        dataframe.to_string();

        Dframe dataframe2 = new Dframe("/app/data.csv");
        System.out.println("On affiche le dataframe obtenu via un fichier csv : ");
        dataframe2.to_string();

        System.out.println("On affiche les 2 premiers index du dataframe : ");
        System.out.println(dataframe.head(2));

        System.out.println("On affiche les 2 derniers index du dataframe : ");
        System.out.println(dataframe.tail(2));


        Dframe dataframe3 = dataframe.iloc(0);
        System.out.println("On affiche le dataframe obtenu via sélection de l'index 0 : ");
        dataframe3.to_string();

        Dframe dataframe4 = dataframe.loc("Domicile");
        System.out.println("On affiche le dataframe obtenu via sélection du label \"Domicile\" : ");
        dataframe4.to_string();

        System.out.println("Calcul :");
        System.out.println("Moyenne d'age : " + dataframe.mean("Age"));
        System.out.println("Age maximum : " + dataframe.max("Age"));
        System.out.println("Age minimum : " + dataframe.min("Age"));
    }
}
