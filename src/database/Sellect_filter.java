package database;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;

public class Sellect_filter{
    ArrayList<String> result = new ArrayList<String>();

    Sellect_filter(String filter, Object attribute, ArrayList<String> area){
        if(area.contains("Department")){
            area.set(area.indexOf("Department"),"Dname");
        }
        String Condition = attribute.toString();
        String range = filter.toString();
        if(filter.equals("부서")){
            D_Select test_d_select = new D_Select(Condition);
            ArrayList<HashMap<String,Object>> result_list = new ArrayList<HashMap<String,Object>>();
            result_list = test_d_select.Select_All();
            for(HashMap<String,Object> row : result_list){
                String return_row = "";
                for (String selected : area){
                    return_row = return_row+row.get(selected).toString()+"&";
                }
                System.out.println(return_row);
                result.add(return_row);
            }
        }else if(filter.equals("성별")){
            Se_Select test_se_select = new Se_Select(Condition);
            ArrayList<HashMap<String,Object>> result_list = new ArrayList<HashMap<String,Object>>();
            result_list = test_se_select.Select_All();
            for(HashMap<String,Object> row : result_list){
                String return_row = "";
                for (String selected : area){
                    return_row = return_row+row.get(selected).toString()+"&";
                }
                System.out.println(return_row);
                result.add(return_row);
            }
        } else if (filter.equals("연봉")) {
            S_Select s_select = new S_Select(Condition);
            ArrayList<HashMap<String,Object>> result_list = new ArrayList<HashMap<String,Object>>();
            result_list = s_select.Select_All();
            for(HashMap<String,Object> row : result_list){
                String return_row = "";
                for (String selected : area){
                    return_row = return_row+row.get(selected).toString()+"&";
                }
                System.out.println(return_row);
                result.add(return_row);
            }
            //vidigummy는 연봉이 쓸데없이 높다 Delete testCode(중간에 gui가 없어서...)
//            Delete_From_List Employee test 코드(어디 Select에 갖다 붙여도 상관 없다)
//            System.out.println("kill vidigummy!!!!");
//            kill_vidigummy = new Delete(result_list);
//            boolean rs = kill_vidigummy.Delete_Employee();
//            System.out.println(rs);

        }else if (filter.equals("생일")){
            B_Select b_select = new B_Select(Condition);
            ArrayList<HashMap<String,Object>> result_list = new ArrayList<HashMap<String,Object>>();
            result_list = b_select.Select_All();
            for(HashMap<String,Object> row : result_list){
                String return_row = "";
                for (String selected : area){
                    return_row = return_row+row.get(selected).toString()+"&";
                }
                System.out.println(return_row);
                result.add(return_row);
            }
        }else if(filter.equals("부하직원")){
            Su_Select su_select = new Su_Select(Condition);
            ArrayList<HashMap<String,Object>> result_list = new ArrayList<HashMap<String,Object>>();
            result_list = su_select.Select_All();
            for(HashMap<String,Object> row : result_list){
                String return_row = "";
                for (String selected : area){
                    return_row = return_row+row.get(selected).toString()+"&";
                }
                System.out.println(return_row);
                result.add(return_row);
            }
        }else{
            A_Select a_select = new A_Select();
            ArrayList<HashMap<String, Object>> result_list = new ArrayList<HashMap<String,Object>>();
            result_list = a_select.Select_All();
            for(HashMap<String,Object> row : result_list){
                String return_row = "";
                for (String selected : area){
                    return_row = return_row+row.get(selected).toString()+"&";
                }
                System.out.println(return_row);
                result.add(return_row);
            }
            //여기서 gui 부르면 될 거 같은데 어떻게 부를지 모르겠네
        }
    }
}