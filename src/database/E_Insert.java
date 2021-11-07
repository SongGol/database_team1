package database;
// 5번에 대한 내용이다.
import java.time.*;

public class E_Insert extends Insert{
    E_Insert(String Fname, String Minit, String Lname, String Ssn, String Bdate, String Address, String Sex, String Salary, String Super_ssn, String Dno){
        Connector conn = new Connector();
        conn.connect();
        String query = String.format("INSERT INTO `EMPLOYEE` (`Fname`, `Minit`, `Lname`, `Ssn`, `Bdate`, `Address`, `Sex`, `Salary`, `Super_ssn`, `Dno`)" +
                " VALUES ('%s','%s','%s','%s','%s', '%s', '%s', %s, %s, %s)",Fname,Minit,Lname,Ssn,Bdate,Address, Sex,Salary,"NULL",Dno);
        String update_SS_query = "UPDATE EMPLOYEE SET Super_ssn = "+Super_ssn+" WHERE Ssn LIKE '"+Ssn+"'";
        System.out.println(query);
        System.out.println(update_SS_query);
        try {
            boolean rs = conn.modify(query);
            if(!Super_ssn.equals("NULL")){
                conn.modify(update_SS_query);
            }
            System.out.println("Insert 성공");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        conn.close();
    }
}