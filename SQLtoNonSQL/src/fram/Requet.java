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
    // att public bah my3ytlhomch b get set .. w c des tab dinamique bah mayaprisizich la taille wi3mar kima bgha .. li b khadra asamha
    public ArrayList<Create> createTable;
    public ArrayList<Create> principleTable;
    public ArrayList<Insert> insertTtable;
    
    //créé un tab vide pr le remplire apré
    public Requet(char type){
        //ici rajouté ceux de eddy
        switch(type){
            case 'C':
                this.createTable = new ArrayList<Create>();
                this.principleTable = new ArrayList<Create>();
                break;
            case 'I':
                this.insertTtable = new ArrayList<Insert>();
            default:
                System.out.println("la requet est pa valid ");
        }
        
    }
    
    //pour trouvé les tab a affiché dans une relation
    public void setPrincipleTable(){
        for (int i = 0;i<createTable.size();i++){
            if(createTable.get(i).getPrimaryKey()==null){
                for(int j=0;j<createTable.size();j++){
                    for(int k =0;k<createTable.get(i).getForeingKey().size();k++){
                        String[] key = createTable.get(i).getForeingKey().get(k).split("\\ ");
                        if(createTable.get(j).getTableName().equals(key[1])){
                            createTable.get(j).setPrinciple(true);
                            createTable.get(j).setPreority(1);
                        }
                    }
                }
            }
        }
    }
    //thws 3la les tabli 3andhom clé étrangére t3k
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
    //pour etre sur que y a que les creete
    public static String requetType(String req){
        String[] reqt = req.split(" ");
        switch(reqt[0]){
            case "CREATE":{
                return "C";
            }
            default:{
                return null;
            }
        }
    }

    public void jsoncode(){
        ArrayList<String> tablenopr = new ArrayList<String>();
        String table="";
        String[] att;
        for(int i =0;i<createTable.size();i++){
            if(!createTable.get(i).getPrincipal()){
                table="properties: {"+'\n';
                if(createTable.get(i).getPrimaryKey()!=null){
                    att=createTable.get(i).getPrimaryKey().split("\\ ");
                    table=table+att[0]+" : {bsonType:\"objectId\"}";
                }
                if(createTable.get(i).getAttribut()!=null){
                    for(int j=0;j<createTable.get(i).getAttribut().size();j++){
                        if(j==createTable.get(i).getAttribut().size()-1){
                            att=createTable.get(i).getAttribut().get(j).split("\\ ");
                            table=table+","+'\n'+att[0]+" : {bsonType:\""+att[1]+"\"}"+'\n'+"}"+'\n';
                        }
                        else{
                            att=createTable.get(i).getAttribut().get(j).split("\\ ");
                            table=table+att[0]+" : {bsonType:\""+att[1]+"\"}";
                        }
                    }
                }
                if(createTable.get(i).getForeingKey()!=null){
                    if(createTable.get(i).getForeingKey().size()>2){
                        boolean test=false;
                        for(int j=0;j<createTable.get(i).getForeingKey().size();j++){
                            att=createTable.get(i).getForeingKey().get(0).split("\\ ");
                            for(int k=0;k<createTable.size();k++){
                                if(createTable.get(k).getTableName().equals(att[1])){
                                    if(!createTable.get(k).getPrincipal()){
                                        if(test){
                                            table=table+","+'\n'+att[1]+"Ref : {bsonType: \"objectId\"}";
                                        }
                                    }
                                    
                                }
                            }
                        }
                    }
                }
                table=table+'\n'+"}";
                createTable.get(i).setJsone(table);
                table="";
            }
            if(createTable.get(i).getPrincipal()){
                table=table+"db.createCollection(\""+createTable.get(i).getTableName()+"\",{"+'\n'+"validator:{"+'\n'+"$jsonShema: {"+'\n'+"bsonType:\"object\","+'\n';
                table=table+"properties: {"+'\n';
                if(createTable.get(i).getPrimaryKey()!=null){
                    att=createTable.get(i).getPrimaryKey().split("\\ ");
                    table=table+att[0]+" : {bsonType : \"objectid\"}";
                }
                if(createTable.get(i).getAttribut()!=null){
                    for (int j=0;j<createTable.get(i).getAttribut().size();j++){
                        att=createTable.get(i).getAttribut().get(j).split("\\ ");
                        table=table+","+'\n'+att[0]+" : {bsonType: \""+att[1]+"\"}";
                    }
                }
                if(createTable.get(i).getForeingKey()!=null){
                    for (int j=0;j<createTable.get(i).getForeingKey().size();j++){
                        if(createTable.get(i).getForeingKey().get(j)!=null){
                            att=createTable.get(i).getForeingKey().get(j).split("\\ ");
                            System.out.println("tgtgtgtgtgtg");
                            for(int k=0;k<createTable.size();k++){
                                if(createTable.get(k).getTableName().equals(att[1])){
                                    if(!createTable.get(k).getPrincipal()){
                                        table= table+","+'\n'+att[1]+"Object:{"+'\n';
                                        table=table+"bsonType: \"object\","+'\n'+createTable.get(k).getJsone(); 
                                    }
                                }
                            }
                        }
                    }
                }
                if(createTable.get(i).getExternalKey()!=null){
                    for (int j=0;j<createTable.get(i).getExternalKey().size();j++){
                        for(int k=0;k<createTable.size();k++){ 
                            if(createTable.get(k).getTableName().equals(createTable.get(i).getExternalKey().get(j))){
                                if(createTable.get(k).getPrincipal()){
                                    table=table+","+'\n'+createTable.get(k).getTableName()+"RefArray:{"+'\n';
                                    table=table+"bsonType: \'array\',"+'\n'+"items:{"+'\n'+"bsonType: \'objectId\'"+'\n';
                                    table=table+"}"+'\n'+"}"+'\n';
                                }
                                else{
                                    table=table+","+'\n'+createTable.get(k).getTableName()+"RefArray:{"+'\n';
                                    table=table+"bsonType: \"array\","+'\n'+"item:{"+'\n'+"bsonType: \"object\","+'\n';
                                    table=table+createTable.get(k).getJsone()+'\n'+"}"+'\n'+"}";
                                }
                            }
                        }
                    }
                }
                table=table+'\n'+"}"+'\n'+"}"+'\n'+"}"+'\n'+"}"+'\n'+"}"+")"+'\n';
                createTable.get(i).setJsone(table);
            }
        }
    }
    public String getJsoncode(){
            String code="";
            boolean non=true;
            for(int  i=0;i<createTable.size();i++){
                if(createTable.get(i).getPrincipal()){
                    if(non){
                        code=code+createTable.get(i).getJsone();
                    }
                    else{
                        code=","+'\n'+code+createTable.get(i).getJsone();
                    }
                }
            }
        return code;
    }
}
