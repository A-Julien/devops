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

    Dframe(int nb_index, int nb_columns){
        this.index = new String[nb_index];
        this.columns = new String[nb_columns];
        this.data = new Object[nb_index][nb_columns];
    }

    Dframe(Object[][] data){
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
                this.data[i][j] = data[j+1][i+1];
            }
        }
    }

    Dframe(String csvFile){

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
                    this.data[i][j] = tmp_list.get(j+1).get(i+1);
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

    public String head(int n){
        StringBuilder stringBuilder = new StringBuilder();
        for(String col : this.columns) stringBuilder.append("       ").append(col);

        stringBuilder.append("\n");
        for(int i = 0 ; i < n ;  i++){
            stringBuilder.append(index[i]).append("     ");
            for(int j = 0 ; j < this.columns.length ;  j++)
                stringBuilder.append(data[i][j]).append("     ");
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String tail(int n){
        StringBuilder stringBuilder = new StringBuilder();
        for(String col : this.columns) stringBuilder.append("       ").append(col);

        stringBuilder.append("\n");
        for(int i = this.index.length - n ; i < this.index.length ;  i++){
            stringBuilder.append(index[i]).append("     ");
            for(int j = 0 ; j < this.columns.length ;  j++)
                stringBuilder.append(data[i][j]).append("     ");
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String col : this.columns)
            stringBuilder.append("       ").append(col);
        stringBuilder.append("\n");
        for(int i = 0 ; i < this.index.length ;  i++){
            stringBuilder.append(index[i]).append("     ");
            for(int j = 0 ; j < this.columns.length ;  j++)
                stringBuilder.append(data[i][j]).append("     ");
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public void to_string(){
        for(String col : this.columns)
            System.out.print("       " + col);
        System.out.println();
        for(int i = 0 ; i < this.index.length ;  i++){
            System.out.print(index[i] + "     ");
            for(int j = 0 ; j < this.columns.length ;  j++)
                System.out.print(data[i][j] + "     ");
            System.out.println();
        }
        System.out.println();
    }

    public Dframe iloc(int line) {
        if(line > this.index.length) return null;
        Dframe dframe = new Dframe(1, this.columns.length);

        dframe.index[0] = this.index[line];
        for(int j = 0 ; j < this.columns.length ; j++) {
            dframe.columns[j] = this.columns[j];
            dframe.data[0][j] = this.data[line][j];
        }
        return dframe;
    }

    public Dframe loc(String label){
        int find = -1;
        for(int j = 0 ; j < this.columns.length ; j++) {
            if (columns[j].equals(label))
                find = j;
        }
        if(find == -1)
            return null;
        Dframe dframe = new Dframe(this.index.length, 1);
        dframe.columns[0] = label;
        for(int i = 0 ; i < this.index.length ; i++) {
            dframe.index[i] = this.index[i];
            dframe.data[i][0] = this.data[i][find];
        }
        return dframe;
    }
}

