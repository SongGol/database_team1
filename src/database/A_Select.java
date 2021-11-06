package database;

import java.util.ArrayList;
import java.util.HashMap;

public class A_Select extends Select{
    //1번에 대한 메써드
    //수정 필요함
    A_Select(){
        result = new ArrayList<HashMap<String,Object>>();
        conn = new Connector();
    }
    public ArrayList<HashMap<String,Object>> Select_All(){
        conn.connect();
        ArrayList<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
        String query = "SELECT e.Name, e.Ssn, e.Bdate, e.Address, e.Sex, e.Supervisor, e.Salary, d.Dname" +
                " FROM DEPARTMENT AS d LEFT JOIN (SELECT concat(e.Fname,\" \", e.Minit,\" \" ,e.Lname) AS Name," +
                " e.Ssn, e.Bdate, e.Address, e.Sex,concat(s.Fname, \" \", s.Minit, \" \", s.Lname) AS Supervisor" +
                ", e.Salary, e.Dno FROM EMPLOYEE AS e LEFT OUTER JOIN EMPLOYEE AS s ON e.Super_ssn = s.Ssn) AS e ON e.Dno = d.Dnumber";
//        String query = "SELECT * FROM EMPLOYEE";
        try {
            result = conn.search(query);
            conn.close();
            System.out.println(result.size());
            return result;
        }catch (Exception e){
            conn.close();
            System.out.println(e);
            e.printStackTrace();
        }
        conn.close();
        return result;
    }
}