package database;

import java.util.ArrayList;
import java.util.HashMap;

public class Update_filter {
    Update_filter(String what, String input, ArrayList<String> input_to_Update){
        if(what.equals("주소")){
            Update_Ssn_ad update_ssn_ad = new Update_Ssn_ad(input_to_Update,input);
            System.out.println("주소 변경 완료");
        }else if(what.equals("성별")){
            Update_Ssn_Se update_ssn_se = new Update_Ssn_Se(input_to_Update,input);
            System.out.println("성별 변경 완료");
        }else if(what.equals("월급")){
            Update_Ssn_sa update_ssn_sa = new Update_Ssn_sa(input_to_Update, input);
            System.out.println("월급 변경 완료");
        }else{
            System.out.println("잘못된 조건 입력");
        }
    }
}