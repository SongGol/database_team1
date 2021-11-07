package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Delete {
    private ArrayList<HashMap<String,Object>> Employee_list;
    private boolean rs;
    Connector conn = null;
    Delete(ArrayList<HashMap<String,Object>> input){
        Employee_list = input;
        rs = false;
        conn = new Connector();
    }
    public boolean Delete_Employee(){
        for(HashMap<String,Object> Employee : Employee_list){
            conn.connect();
            String Ssn = Employee.get("Ssn").toString();
            //고용인을 SuperSsn으로 가지고 있는거 업데이트한다.
            String del_this_Employee_Super_Ssn_query = "UPDATE EMPLOYEE SET Super_Ssn = Null WHERE Super_Ssn = "+Ssn;
            System.out.println(del_this_Employee_Super_Ssn_query);
            rs = conn.modify(del_this_Employee_Super_Ssn_query);
            //그 고용인 자체를 없앤다.
            String del_this_Employee_query = "DELETE FROM EMPLOYEE WHERE Ssn = "+ Ssn;
            System.out.println(del_this_Employee_query);
            rs = conn.modify(del_this_Employee_query);

            conn.close();
        }
        return rs;
    }
}