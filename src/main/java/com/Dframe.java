package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Exception.CsvException;
import Exception.FrameOutOfBound;
import Exception.IndexException;

public class Dframe {

    private String[] index;
    private String[] columns;
    private Object[][] data;

    Dframe(int nb_index, int nb_columns){
        this.index = new String[nb_index];
        this.columns = new String[nb_columns];
        this.data = new Object[nb_index][nb_columns];
    }

    /**
     * Construct data frame
     *
     * @param data
     */
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

    /**
     * Construct data frame from csv file
     * @param csvFile the csv file
     * @throws IOException if file not found
     */
    Dframe(String csvFile) throws CsvException {

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
            throw new CsvException("can not open csv file");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new CsvException("can not open csv file");
                }
            }
        }
    }

    /**
     * Return a specifique columns of the data frame
     *
     * @return
     */
    @Generated
    public String[] getIndex(){
        return this.index;
    }

    /**
     * Return a specifique columns of the data frame
     *
     * @return column
     */
    public String[] getColumns(){
        return this.columns;
    }

    /**
     * This function returns the first n rows for the object based on position.
     * It is useful for quickly testing if your object has the right type of data in it.
     *
     * @param n Number of rows to select.
     * @return The first n rows of the caller object.
     */
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

    /**
     * This function returns last n rows from the object based on position.
     * It is useful for quickly verifying data, for example, after sorting or appending rows.
     *
     * @param n Number of rows to select.
     * @return The last n rows of the caller object.
     */
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

    /**
     * return a string with formatted data frame
     * @return dataframe to string
     */
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

    /**
     * Print the dataframe
     */
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

    /**
     * Purely integer-location based indexing for selection by position.
     *
     * .iloc[] is primarily integer position based (from 0 to length-1 of the axis),
     * but may also be used with a boolean array.
     *
     * @param line
     * @return
     */
    public Dframe iloc(int line) throws FrameOutOfBound {
        if(line > this.index.length) throw new FrameOutOfBound("frame out frame");
        Dframe dframe = new Dframe(1, this.columns.length);

        dframe.index[0] = this.index[line];
        for(int j = 0 ; j < this.columns.length ; j++) {
            dframe.columns[j] = this.columns[j];
            dframe.data[0][j] = this.data[line][j];
        }
        return dframe;
    }

    /**
     * Check if parameter label belongs to the dataframe
     * @param label
     * @return index of the label if it does, -1 otherwise
     */
    private int find(String label){
        int b = -1;
        for(int j = 0 ; j < this.columns.length ; j++) {
            if (columns[j].equals(label))
                b = j;
        }
        return b;
    }

    /**
     * Access a group of rows and columns by label(s) or a boolean array.
     *
     * .loc[] is primarily label based, but may also be used with a boolean array.
     *
     * @param label
     * @return
     */
    public Dframe loc(String label) throws IndexException {
        int k = find(label);
        if(k == -1) throw new IndexException("can not find :" + label);
        Dframe dframe = new Dframe(this.index.length, 1);
        dframe.columns[0] = label;
        for(int i = 0 ; i < this.index.length ; i++) {
            dframe.index[i] = this.index[i];
            dframe.data[i][0] = this.data[i][k];
        }
        return dframe;
    }

    /**
     * Compute the mean of values of column label. Values must be numbers.
     * @param label
     * @return mean of values, -1 if label not found
     */
    public float mean(String label){
        int k = find(label);
        if(k  == -1)
            return -1;
        float sum = 0;
        for(int i = 0 ; i < this.index.length ; i++){
            sum = sum + Float.parseFloat((String) this.data[i][k]);
        }
        return sum/this.index.length;
    }

    /**
     * Compute the max of values of column label. Values must be numbers.
     * @param label
     * @return max of values, -1 if label not found
     */
    public float min(String label){
        int k = find(label);
        if(k == -1)
            return -1;
        float max = Float.parseFloat((String)this.data[0][k]);
        for(int i = 1 ; i < this.index.length ; i++){
            if(max > Float.parseFloat((String)this.data[i][k]))
                max = Float.parseFloat((String)this.data[i][k]);
        }
        return max;
    }

    /**
     * Compute the min of values of column label. Values must be numbers.
     * @param label
     * @return min of values, -1 if label not found
     */
    public float max(String label){
        int k = find(label);
        if(k == -1)
            return -1;
        float min = Float.parseFloat((String)this.data[0][k]);
        for(int i = 1 ; i < this.index.length ; i++){
            if(min < Float.parseFloat((String)this.data[i][k]))
                min = Float.parseFloat((String)this.data[i][k]);
        }
        return min;
    }

    @Generated
    public Object[][] getData() {
        return data;
    }
}