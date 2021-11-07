package database;

import java.util.ArrayList;
import java.util.HashMap;

public class Se_Update extends Update{
    Se_Update(String input, ArrayList<HashMap<String,Object>> input_to_change){
        this.to_select_list = input_to_change;
        this.input_source = input;
        this.conn = new Connector();

        this.Update_Sex();
    }
    public void Update_Sex(){
        this.conn.connect();
        for(HashMap<String,Object> employee : this.to_select_list){
            String Ssn = employee.get("Ssn").toString();
            String update_query = "UPDATE EMPLOYEE SET Sex = '"+this.input_source+"' WHERE Ssn = "+ Ssn;
            System.out.println(update_query);
            try {
                this.conn.modify(update_query);
            }catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }finally {
                this.conn.close();
            }
        }
    }
}