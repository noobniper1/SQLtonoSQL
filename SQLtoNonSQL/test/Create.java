/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fram;
/**
 *
 * @author Info Pratique
 */
import java.lang.*;
public class Create {
    private String TableName;
    private String PrimaryKey;
    private String[] Attribut;
    private boolean Principal;
    private String[] ForeingKey;
    private String[] ExternalKey;
    private String requet;
    
    /* geteres */
    public String getTableName(){
        return TableName;
    }
    public String getPrimaryKey(){
        return PrimaryKey;
    }
    public String[] getAttribut(){
        return Attribut;
    }
    public boolean getPrincipal(){
        return Principal;
    }
    public String[] getForeingKey(){
        return ForeingKey;
    }
    public String[] getExternalKey(){
        return ExternalKey;
    }
    
    /* seteres */
    
    public void setTableName(String Table){
        TableName = Table;
    }
    public void setPrimaryKey(String Primary){
        PrimaryKey = Primary;
    }
    public void setAttribut(String[] Attrib){
        
    }
    public void setPrincipal(boolean Princip){
        Principal = Princip;
    }
    public void setForeingKey(String[] Foreing){
        System.out.println("le foreing key est"+Foreing[0]);
        String fore[][]=new String[Foreing.length][3];
        String fores[]=null;
        StringBuffer[] sb=new StringBuffer[Foreing.length];
        StringBuffer[] bs=new StringBuffer[Foreing.length];
        
        for (int i=0;i<Foreing.length;i++){
            fores=Foreing[i].split("\\ ");
            
            sb[i] = new StringBuffer(fores[2]);
            
            if(sb[i].charAt(0)=='('){
                sb[i].deleteCharAt(0);    
            }
            
            if(sb[i].charAt(sb[i].length()-1)==')'){
                sb[i].deleteCharAt(sb[i].length()-1);
            }
            fore[i][0]=sb[i].toString();
            fores=fores[4].split("\\(");
            fore[i][1]=fores[0];
            System.out.println("fores 1 est "+fores.length);
            bs[i] = new StringBuffer(fores[1]);
            if(bs[i].charAt(bs[i].length()-1)==')'){
                bs[i].deleteCharAt(bs[i].length()-1);
                System.out.println(bs[i]);
            }
            fore[i][2]=bs[i].toString();
        }
    
        ForeingKey = fore;
    }
    public void setExternalKey(String[] External){
        ExternalKey = External;
    }
    
    /*constrector*/
    
    public Create(String requet){
        this.requet = removetabs(requet);
        this.TableName = tableNameExtractor(requet);
        attributextractor(this.requet);
        printor();
        
    }
    
    /* methodes */
   
    
    public void printor(){
        System.out.println("le nom de la table est "+getTableName());
        System.out.println("la cle de la table est "+getPrimaryKey());
        System.out.println("les atribut de la table son ");
        for(int i=0;i<Attribut.length;i++){
            System.out.println(getAttribut()[i][0]+" "+getAttribut()[i][1]);
        }
        System.out.println("les cles etrangaire de la table sont ");
        for(int i=0;i<ForeingKey.length;i++){
            System.out.println(getForeingKey()[i][0]+" "+getForeingKey()[i][1]+" "+getForeingKey()[i][2]);
        }
        System.out.println("-------------------------------------------------------");                
    }
    public static String tableNameExtractor(String req){
        String[] reqt = req.split("\\r?\\n");
        System.out.println(reqt[0]);
        reqt = reqt[0].split("\\(");
        reqt = reqt[0].split("\\ ");
        return reqt[2];
    }    
    public String removetabs(String req){
        StringBuffer sb = new StringBuffer(req);
        while(sb.charAt(0)==' '){
            sb.deleteCharAt(0);
        }
        while(sb.charAt(sb.length()-1)==' '){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    public boolean primar(String req){
        String reqt[] = req.split("\\ ");
        for (int i=0;i<reqt.length;i++){
            if (reqt[i].equals("PRIMARY")){
                return true;
            }
        }
        return false;
    }
    public boolean fore(String req){
        String reqt[] = req.split("\\ ");
        for (int i=0;i<reqt.length;i++){
            if (reqt[i].equals("FOREIGN")){
                return true;
            }
        }
        return false;
    }
    public void attributextractor(String req){
        String reqt[] = req.split("\\r?\n");
        String forei = "",att="";
        for (int i =1;i<reqt.length;i++){
            reqt[i] = removetabs(reqt[i]);
            if(primar(reqt[i])){
                setPrimaryKey(reqt[i]);
            }
            else{
                if(fore(reqt[i])){
                    if (forei.equals("")){
                        forei=reqt[i];
                    }
                    else{
                        forei=forei+reqt[i];
                    }
                }
                else{
                    if(!reqt[i].equals(")")){
                        att =  att+reqt[i];
                    }
                }
            }
        }
        setAttribut(att.split("\\,"));
        System.out.println("les foreing key "+forei);
        setForeingKey(forei.split("\\,"));
        
        
    }
    /*public static void main(String args[]){
        String s="   hello hillo";
        String g[]=s.split("\\    ");
        System.out.println("go go"+g[0]);
    }*/
}
