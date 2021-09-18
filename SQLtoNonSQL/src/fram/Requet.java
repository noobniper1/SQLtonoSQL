/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fram;

import java.util.ArrayList;

/**
 *
 * @author Info Pratique
 */
public class Requet {
    public ArrayList<Create> createTable;
    public ArrayList<Create> principleTable;
    
    public Requet(){
        createTable = new ArrayList<Create>();
        principleTable = new ArrayList<Create>();
    }
    
    public void setPrincipleTable(){
        for (int i = 0;i<createTable.size();i++){
            if(createTable.get(i).getPrimaryKey()==null){
                for(int j=0;j<createTable.size();j++){
                    for(int k =0;k<createTable.get(i).getForeingKey().size();k++){
                        String[] key = createTable.get(i).getForeingKey().get(k).split("\\ ");
                        if(createTable.get(j).getTableName().equals(key[1])){
                            createTable.get(j).setPrinciple(true);
                        }
                    }
                }
            }
        }
    }
    public void mostUsedTable(String tablename){
        Create table;
        for (int k=0;k<createTable.size();k++){
            if (createTable.get(k).getTableName().equals(tablename)){
                System.out.println("hello");
                table=createTable.get(k);
                table.setPrinciple(true);
                System.out.println(createTable.get(k).getPrincipal());
                for(int i=0;i<createTable.size();i++){
                    if(createTable.get(i).getForeingKey()!=null){
                        for(int j=0;j<createTable.get(i).getForeingKey().size();j++){
                            if(createTable.get(i).getForeingKey().get(j)!=null){
                                String na=createTable.get(i).getForeingKey().get(j).split("\\ ")[1];
                                System.out.println(na);
                                if(na.equals(tablename)){
                                    System.out.println("huy");
                                    table.setExternalKey(createTable.get(i).getTableName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void setExternalKeys(){
        String name;
        String key[];
        for (int i =0;i<createTable.size();i++){
            name = createTable.get(i).getTableName();
            for(int j=0;j<createTable.size();j++){
                for(int k=0;k<createTable.get(j).getForeingKey().size();k++){
                    if(createTable.get(j).getForeingKey().get(k)!=null){
                        key=createTable.get(j).getForeingKey().get(k).split("\\ ");
                        if(key[1].equals(name)){
                            createTable.get(i).setExternalKey(createTable.get(j).getTableName());
                        }
                    }
                }
            }
        }
    }
    
    public static String requetType(String req){
        String[] reqt = req.split(" ");
        switch(reqt[0]){
            case "CREATE":{
                return "C";
            }
            case "SELECT":{
                return "S";
            }
            default:{
                return null;
            }
        }
    }
}
