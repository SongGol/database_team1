package database;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;

public class Sellect_filter{
    ArrayList<String> result = new ArrayList<String>();

    Sellect_filter(String filter, Object attribute){
        String Condition = attribute.toString();
        String range = filter.toString();

        if(filter.equals("부서")){
            D_Select test_d_select = new D_Select(Condition);
            ArrayList<HashMap<String,Object>> tmp = new ArrayList<HashMap<String,Object>>();
            tmp = test_d_select.Select_All();
            String columns = "NAME&SSN&BDATE&ADDRESS&SEX&SALARY&SUPERVISOR&DEPARTMENT";
            result.add(columns);
            for (HashMap<String, Object> row : tmp){
                String row_one = row.get("Name")+"&"+row.get("Ssn")+"&"+row.get("Bdate")+"&"+row.get("Address")+"&"
                        +row.get("Sex")+"&"+row.get("Supervisor")+"&"+row.get("Salary")+"&"+row.get("Dname");
                result.add(row_one);
            }
        }else if(filter.equals("성별")){
            Se_Select test_se_select = new Se_Select(Condition);
            ArrayList<HashMap<String,Object>> tmp = new ArrayList<HashMap<String,Object>>();
            tmp = test_se_select.Select_All();
            String columns = "NAME&SSN&BDATE&ADDRESS&SEX&SALARY&SUPERVISOR&DEPARTMENT";
            result.add(columns);
            for (HashMap<String, Object> row : tmp){
                String row_one = row.get("Name")+" "+row.get("Ssn")+row.get("Bdate")+" "+row.get("Address")+" "
                        +row.get("Sex")+" "+row.get("Supervisor")+" "+row.get("Salary")+" "+row.get("Dname");
                result.add(row_one);
                System.out.println(row_one);
            }
        } else if (filter.equals("연봉")) {
            S_Select s_select = new S_Select(Condition);
            ArrayList<HashMap<String,Object>> tmp = new ArrayList<HashMap<String,Object>>();
            tmp = s_select.Select_All();
            String columns = "NAME&SSN&BDATE&ADDRESS&SEX&SALARY&SUPERVISOR&DEPARTMENT";
            result.add(columns);
            for (HashMap<String, Object> row : tmp){
                String row_one = row.get("Name")+" "+row.get("Ssn")+row.get("Bdate")+" "+row.get("Address")+" "
                        +row.get("Sex")+" "+row.get("Supervisor")+" "+row.get("Salary")+" "+row.get("Dname");
                System.out.println(row_one);
//                result.add(row_one);
            }
        }else if (filter.equals("생일")){
            B_Select b_select = new B_Select(Condition);
            ArrayList<HashMap<String,Object>> tmp = new ArrayList<HashMap<String,Object>>();
            tmp = b_select.Select_All();
            String columns = "NAME&SSN&BDATE&ADDRESS&SEX&SALARY&SUPERVISOR&DEPARTMENT";
            result.add(columns);
            for (HashMap<String, Object> row : tmp){
                String row_one = row.get("Name")+" "+row.get("Ssn")+row.get("Bdate")+ " "+row.get("Address")+" "
                        +row.get("Sex")+" "+row.get("Supervisor")+" "+row.get("Salary")+" "+row.get("Dname");
                System.out.println(row_one);
//                result.add(row_one);
            }
        }else if(filter.equals("부하직원")){
            Su_Select su_select = new Su_Select(Condition);
            ArrayList<HashMap<String,Object>> tmp = new ArrayList<HashMap<String,Object>>();
            tmp = su_select.Select_All();
            String columns = "NAME&SSN&BDATE&ADDRESS&SEX&SALARY&SUPERVISOR&DEPARTMENT";
            result.add(columns);
            for (HashMap<String, Object> row : tmp){
                String row_one = row.get("Name")+" "+row.get("Ssn")+row.get("Bdate")+" "+row.get("Address")+" "
                        +row.get("Sex")+" "+row.get("Supervisor")+" "+row.get("Salary")+" "+row.get("Dname");
                System.out.println(row_one);
//                result.add(row_one);
            }
        }else{
            A_Select a_select = new A_Select();
            ArrayList<HashMap<String, Object>> tmp = new ArrayList<HashMap<String,Object>>();
            tmp = a_select.Select_All();
            String columns = "NAME&SSN&BDATE&ADDRESS&SEX&SALARY&SUPERVISOR&DEPARTMENT";
            result.add(columns);
            for(HashMap<String,Object> row : tmp){
                String row_one = row.get("Name")+"&"+row.get("Ssn")+"&"+row.get("Bdate")+"&"+row.get("Address")+"&"
                        +row.get("Sex")+"&"+row.get("Supervisor")+"&"+row.get("Salary")+"&"+row.get("Dname");
                System.out.println(row_one);
                result.add(row_one);
            }
            //여기서 gui 부르면 될 거 같은데 어떻게 부를지 모르겠네
        }
    }
}