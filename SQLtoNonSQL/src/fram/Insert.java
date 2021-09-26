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
public class Insert {
    private String tablename;
    private ArrayList<String> values=new ArrayList<String>();
    private String include;
    private ArrayList<String> valNames=new ArrayList<String>();
    private boolean arether;
    
    public String getTablename(){
        return tablename;
    }
    public String getInclude(){
        return include;
    }
    public ArrayList<String> getValues(){
        return values;
    }
    public String getValue(int i){
        if(i<values.size()){
            return values.get(i);
        }
        else{
            return null;
        }
    }
    public boolean getArether(){
        return arether;
    }
    public String getValName(int i){
        return valNames.get(i);
    }
    public ArrayList<String> getValNames(){
        return valNames;
    }
    public Insert(String req){
        dataExtractor(req);
    }
    
    
    public void setTablename(String name){
        tablename=name;
    }
    public void setInclude(String inc){
        include =inc;
    }
    public void setValue(String val){
        StringBuffer sb = new StringBuffer(val);
        while(sb.charAt(sb.length()-1)==' '||sb.charAt(sb.length()-1)==')'){
            sb.deleteCharAt(sb.length()-1);
        }
        values.add(sb.toString());
    }
    public void setArether(boolean bol){
        arether=bol;
    }
    public void setValName(String val){
        StringBuffer sb = new StringBuffer(val);
        while(sb.charAt(sb.length()-1)==' '||sb.charAt(sb.length()-1)==')'){
            sb.deleteCharAt(sb.length()-1);
        }
        valNames.add(sb.toString());
    }
    
    public String dataExtractor(String req){
        String[] reqts,vals,reqt = req.split("\\r?\\n");
        StringBuffer sb = new StringBuffer(reqt[0]);
        for(int i=0;i<sb.length();i++){
            if(sb.charAt(i)=='('){
                setArether(true);
                sb.deleteCharAt(sb.length()-1);
            }
        }
        if (getArether()){
            setTablename(sb.toString().split("\\(")[0].split("\\ ")[2]);
            System.out.println(getTablename());
            vals=sb.toString().split("\\(");
            vals=vals[1].split("\\,");
            for (int i=0;i<vals.length;i++){
                System.out.println(vals[i]);
                setValName(vals[i]);    
            }
            
            
            System.out.println(getValNames());
        }
        else{
            setTablename(reqt[0].split("\\ ")[2]);
        }
        StringBuffer sb1=new StringBuffer(reqt[1]);
        
        ArrayList<String> value=new ArrayList<String>();
        return null;
    }


}
