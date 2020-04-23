package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dframe {

    String[] index;
    String[] columns;
    public Object[][] data;

    public Dframe(Object[][] data){
        int m = data.length-1;
        int n = data[0].length;
        this.index = new String[n];
        this.columns = new String[m];
        this.data = new Object[n][m];

        for(int j = 0 ; j < m ; j++) {
            columns[j] = (String) data[j+1][0];
        }

        for(int i = 0 ; i < n ; i++){
            index[i] = (String) data[0][i];
        }

        for(int i = 0 ; i < n; i++) {
            for(int j = 0 ; j < m ; j++){
                this.data[i][j] = data[i+1][j+1];
            }
        }
    }

    public Dframe(String csvFile){

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<List<String>> tmp_list = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] tmp = line.split(cvsSplitBy); // use comma as separator
                tmp_list.add(Arrays.asList(tmp));
            }

            this.index = new String[tmp_list.get(0).size()];
            for(int i = 0 ; i < tmp_list.get(0).size() ; i++)
                this.index[i] = tmp_list.get(0).get(i);

            this.columns = new String[tmp_list.size()-1];
            for(int j = 0 ; j < tmp_list.size()-1 ; j++)
                this.columns[j] = tmp_list.get(j+1).get(0);

            this.data = new Object[index.length][columns.length];
            for(int i = 0 ; i < index.length ; i++){
                for(int j = 0 ; j < columns.length ; j++){
                    this.data[i][j] = tmp_list.get(i+1).get(j+1);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String[] getIndex(){
        return this.index;
    }

    public String[] getColumns(){
        return this.columns;
    }

    public void print(){
        for(String col : this.columns)
            System.out.print("       " + col);
        System.out.println();
        for(int i = 0 ; i < this.index.length ;  i++){
            System.out.print(index[i] + "     ");
            for(int j = 0 ; j < this.columns.length ;  j++)
                System.out.print(data[j][i] + "     ");
            System.out.println();
        }
    }

}

