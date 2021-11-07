package database;

import java.util.ArrayList;

public class Update_Ssn_sa extends Update_From_Ssn{
    Update_Ssn_sa(ArrayList<String> input_list, String source){
        conn = new Connector();
        to_update_list = input_list;
        input_source = source;
        this.Update();
    }

    private void Update(){
        for(String Ssn : this.to_update_list){
            String update_query = "UPDATE EMPLOYEE SET Adress = "+this.input_source+" WHERE Ssn = "+Ssn;
            try {
                this.conn.connect();
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