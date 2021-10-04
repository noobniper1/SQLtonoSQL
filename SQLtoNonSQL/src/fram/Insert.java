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
    private String JsoneCode;
    
    public String getJsoneCode(){
        return JsoneCode;
    }
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
        JsoneMaker();
    }
    
    
    public void setTablename(String name){
        tablename=name;
    }
    public void setInclude(String inc){
        StringBuffer sb=new StringBuffer(inc);
        if(sb.charAt(sb.length()-1)==';'){
            sb.deleteCharAt(sb.length()-1);
        }
        include =sb.toString();
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
   public void setJsoneCode(String code){
       JsoneCode=code;
   }
    
    public void dataExtractor(String req){
        String[] reqts,vals,reqt = req.split("\\r?\\n");
        StringBuffer sb = new StringBuffer(reqt[0]);
        for(int i=0;i<sb.length();i++){
            if(sb.charAt(i)=='('){
                setArether(true);
                sb.deleteCharAt(sb.length()-1);
                if(sb.charAt(sb.length()-1)==')'){
                    sb.deleteCharAt(sb.length()-1);
                }
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
            System.out.println(getTablename());
        }
        reqts=reqt[1].split("\\(");
        StringBuffer sb1=null;
        for(int i=1;i<reqts.length;i++){
            if(sb1==null){
                sb1=new StringBuffer(reqts[i]);
                while(sb1.charAt(sb1.length()-1)==')'||sb1.charAt(sb1.length()-1)==','||sb1.charAt(sb1.length()-1)==';'){
                    sb1.deleteCharAt(sb1.length()-1);
                }
                System.out.println('u');
                reqts[i]=sb1.toString();    
            }
            else{
                sb1.delete(0, sb1.length()-1);
                sb1.append(reqts[i]);
                while(sb1.charAt(sb1.length()-1)==')'||sb1.charAt(sb1.length()-1)==','||sb1.charAt(sb1.length()-1)==';'){
                    sb1.deleteCharAt(sb1.length()-1);
                }
                reqts[i]=sb1.toString();
            }
            setValue(reqts[i]);
            System.out.println(getValues().get(i-1));
        }
        reqts=reqt[2].split("\\ ");
        if(reqts[1].equals("null;")){
            setInclude(null);
        }
        else{
            reqts=reqt[2].split("\\,")[1].split("\\ ");
            setInclude(reqts[2]);
        }
        System.out.println(getInclude());
    }
    public void JsoneMaker(){
        String[] vals;
        String code="db."+getTablename()+".insert(";
        if(getInclude()==null){
            if(getValues().size()==1){
                vals=getValues().get(0).split("\\,");
                for(int i=0;i<vals.length;i++){
                    if(i==0){
                        code=code+"{"+getValName(i)+":"+vals[i];
                    }
                    else{
                        code=code+","+getValName(i)+":"+vals[i];
                    }
                }
                code=code+"})";
            }
            else{
                code=code+"[";
                for(int j=0;j<getValues().size();j++){
                    vals=getValues().get(j).split("\\,");
                    if(j!=0){
                        code=code+",";
                    }
                    for(int i=0;i<vals.length;i++){
                        if(i==0){
                            code=code+"{"+getValName(i)+":"+vals[i];
                        }
                        else{
                            code=code+","+getValName(i)+":"+vals[i];
                        }
                    }
                    code=code+"}";
                }
                code=code+"]";
            }
            setJsoneCode(code);
            System.out.println(code);
        }
        else{
            if(getInclude().endsWith("Object")){
                code=code+"{"+getInclude()+":";
                if(getValues().size()==1){
                    vals=getValues().get(0).split("\\,");
                    for(int i=0;i<vals.length;i++){
                        if(i==0){
                            code=code+"{"+getValName(i)+":"+vals[i];
                        }
                        else{
                            code=code+","+getValName(i)+":"+vals[i];
                        }
                    }
                    code=code+"})";
                }
                else{
                    code=code+"[";
                    for(int j=0;j<getValues().size();j++){
                        vals=getValues().get(j).split("\\,");
                        if(j!=0){
                            code=code+",";
                        }
                        for(int i=0;i<vals.length;i++){
                            if(i==0){
                                code=code+"{"+getValName(i)+":"+vals[i];
                            }
                            else{
                                code=code+","+getValName(i)+":"+vals[i];
                            }
                        }
                        code=code+"}";
                    }
                    code=code+"]})";
                }
                setJsoneCode(code);
                System.out.println(code);
            }
            else{
                code=code+"{"+getInclude()+":";
                if(getValues().size()==1){
                    vals=getValues().get(0).split("\\,");
                    for(int i=0;i<vals.length;i++){
                        if(i==0){
                            code=code+"["+getValName(i)+":"+vals[i];
                        }
                        else{
                            code=code+","+getValName(i)+":"+vals[i];
                        }
                    }
                    code=code+"])";
                }
                else{
                    code=code+"[";
                    for(int j=0;j<getValues().size();j++){
                        vals=getValues().get(j).split("\\,");
                        if(j!=0){
                            code=code+",";
                        }
                        for(int i=0;i<vals.length;i++){
                            if(i==0){
                                code=code+"["+getValName(i)+":"+vals[i];
                            }
                            else{
                                code=code+","+getValName(i)+":"+vals[i];
                            }
                        }
                        code=code+"]";
                    }
                    code=code+"]})";
                }
                setJsoneCode(code);
                System.out.println(code);
            }
        }
    }
}
