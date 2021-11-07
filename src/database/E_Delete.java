package database;

import java.util.ArrayList;

public class E_Delete {
    E_Delete(ArrayList<String> input_to_del){
        Connector conn = new Connector();
        for (String employee : input_to_del){
            conn.connect();
            String del_this_Employee_Super_Ssn_query = "UPDATE EMPLOYEE SET Super_Ssn = Null WHERE Super_Ssn = "+employee;
            String del_this_Employee_query = "DELETE FROM EMPLOYEE WHERE Ssn = "+ employee;
            conn.modify(del_this_Employee_Super_Ssn_query);
            conn.modify(del_this_Employee_query);
            conn.close();
        }
    }
}