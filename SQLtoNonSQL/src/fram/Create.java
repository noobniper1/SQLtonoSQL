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
public class Create extends Requet{
    private String TableName;
    private String PrimaryKey;
    private ArrayList<String> Attribut = new ArrayList<String>();
    private boolean Principal;
    private ArrayList<String> ForeingKey = new ArrayList<String>();
    private ArrayList<String> ExternalKey = new ArrayList<String>();
    private int preority;
    private String jsone;
    
    
    public String getTableName(){
        return TableName;
    }
    public String getPrimaryKey(){
        return PrimaryKey;
    }
    public ArrayList<String> getAttribut(){
        return Attribut;
    }
    public boolean getPrincipal(){
        return Principal;
    }
    public ArrayList<String> getForeingKey(){
        return ForeingKey;
    }
    public ArrayList<String> getExternalKey(){
        return ExternalKey;
    }
    public int getPreority(){
        return preority;
    }
    public String getJsone(){
        return jsone;
    }
    
    public void setTableName(String name){
        TableName = name;
    }
    public void setPrimaryKey(String key){
        PrimaryKey=key;
    }
    public void setAttribut(String attr){
        Attribut.add(attr);
    }
    public void setPrinciple(boolean prin){
        Principal=prin;
    }
    public void setForeingKey(String forei){
        if(forei!=null){
            String[] reqt = forei.split("\\ ");
            StringBuffer sb = new StringBuffer(reqt[0]);
            sb=sb.deleteCharAt(0);
            sb=sb.deleteCharAt(sb.length()-1);
            String gg = reqt[1];
            
            reqt=reqt[1].split("\\(");
            StringBuffer bs = new StringBuffer(reqt[1]);        
            bs=bs.deleteCharAt(bs.length()-1);
            forei = sb.toString()+" "+reqt[0]+" "+bs.toString();
           
        }
        ForeingKey.add(forei);
    }
    public void setExternalKey(String exter){
        ExternalKey.add(exter);
    }
    public void setPreority(int preo){
        preority=preo;
    }
    public void setJsone(String jsn){
        jsone=jsn;
    }
    
    public void deleteforing(int k){
        ForeingKey.remove(k);
    }
    
    public Create(String requet,int preo){
        this.preority = preo;
        requet = removetabs(requet);
        setTableName(tableNameExtractor(requet));
        setPrimaryKey(PrimaryKeyextractor(requet));
        ForeingKeyextractor(requet);
        attributExtractor(requet);
        setPreority(2);
    }
    
    
    public static String tableNameExtractor(String req){
        String[] reqt = req.split("\\r?\\n");
        reqt = reqt[0].split("\\(");
        reqt = reqt[0].split("\\ ");
        return reqt[2];
    } 
    public void printor(){
        System.out.println("table name est "+getTableName());
        System.out.println(getPrimaryKey());
        System.out.println(getAttribut());
        System.out.println(getForeingKey());
        System.out.println(getPrincipal());
        System.out.println(getExternalKey());
        System.out.println("------------------------------");
    }
    public String removetabs(String req){
        StringBuffer sb = new StringBuffer(req);
        while(sb.charAt(0)==' '){
            sb.deleteCharAt(0);
        }
        while((sb.charAt(sb.length()-1)==' ')||(sb.charAt(sb.length()-1)==',')){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    public String PrimaryKeyextractor(String req){
        String reqt[] = req.split("\\r?\\n");
        req = removetabs(reqt[1]);
        reqt = req.split("\\ ");
        for(int i=0;i<reqt.length;i++){
            if (reqt[i].equals("PRIMARY")){
                if (i==0){
                    return reqt[2]+" "+reqt[3];
                }
                else{ 
                    return reqt[0]+" "+reqt[1];   
                }
            }
        }
        return null;
    }
    public void ForeingKeyextractor(String req){
        String reqts[],reqt[] = req.split("\\r?\\n");
        boolean key = true;
        for(int j =0;j<reqt.length;j++){
            req = removetabs(reqt[j]);
            reqts = req.split("\\ ");
            if(reqts[0].equals("FOREIGN")){
                setForeingKey(reqts[2]+" "+reqts[4]);
                key = false;
            }    
        }
        if(key){setForeingKey(null);}
    }
    public void attributExtractor(String req){
        String reqts[],reqt[] = req.split("\\r?\\n");
        int start = 1,end = reqt.length-1;
        if(getPrimaryKey()!=null){start=2;}
        for(int i =start;i<end;i++){
            reqt[i]=removetabs(reqt[i]);
            reqts=reqt[i].split("\\ ");
            if(!reqts[0].equals("FOREIGN")){
                setAttribut(reqts[0]+" "+reqts[1]);
            }
        }
    }
}
