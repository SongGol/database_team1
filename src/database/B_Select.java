package database;

import java.util.ArrayList;
import java.util.HashMap;

public class B_Select extends Select {
    String filter_condition;

    B_Select(String s) {
        result = new ArrayList<HashMap<String, Object>>();
        filter_condition = s;
        conn = new Connector();
    }

    public ArrayList<HashMap<String, Object>> Select_All() {
        System.out.println("Birth_day_collector");
        conn.connect();
        String query = "SELECT e.Name, e.Ssn, e.Bdate, e.Address, e.Sex, e.Supervisor, e.Salary, d.Dname" +
                " FROM DEPARTMENT AS d LEFT JOIN (SELECT concat(e.Fname,\" \", e.Minit,\" \" ,e.Lname) AS Name," +
                " e.Ssn, e.Bdate, e.Address, e.Sex,concat(s.Fname, \" \", s.Minit, \" \", s.Lname) AS Supervisor" +
                ", e.Salary, e.Dno FROM EMPLOYEE AS e LEFT OUTER JOIN EMPLOYEE AS s ON e.Super_ssn = s.Ssn) AS e ON e.Dno = d.Dnumber"
                + " WHERE e.Bdate LIKE '" + filter_condition + "'";
        System.out.println(query);
        try {
            result = conn.search(query);
            conn.close();
            return result;
        } catch (Exception e) {
            conn.close();
            e.printStackTrace();
        }
        conn.close();
        return null;
    }
}