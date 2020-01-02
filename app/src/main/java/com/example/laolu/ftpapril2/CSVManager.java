package com.example.laolu.ftpapril2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laolu on 24/04/2015.
 */
public class CSVManager {
    public  ArrayList<String> GetArrayListFromCSV(String csvdata){
        ArrayList<String> result_arr= new ArrayList<String>();
        String csvSplitter = ",";//sets ',' as the csv splitter
        String[] lines = csvdata.split("\\r?\\n");//split by r= return n=new line

        List<DataFile> dataList= new ArrayList<DataFile>();
        for (int i=1;i<14;i++){
            String[] cellcontent = lines[i].split(csvSplitter);
            if(cellcontent!=null){
                DataFile dFile = new DataFile();
                dFile.dataValue = cellcontent[4]!=null? cellcontent[4]:"No Content";
                dFile.dataUnits = cellcontent[5]!=null? cellcontent[5]:"No Content";
                dFile.dataDescriptor = cellcontent[6]!=null? cellcontent[6]:"No Content";

                dataList.add(dFile);
            }
        }

        result_arr.add("Parameter");
        result_arr.add("Value");
        result_arr.add("Unit");

        
        for(final DataFile cell: dataList){

            result_arr.add(cell.dataDescriptor);
            result_arr.add(cell.dataValue);
            result_arr.add(cell.dataUnits);
        }

        return result_arr;

    }

}
