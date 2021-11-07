package database;

public class Main {
    public static void main(String args[]) {
        Connector conn = new Connector();
//모든 EMPLOYEE의 정보를 가져오는 법
//        ArrayList<String> test_select = new ArrayList<String>();
//        test_select.add("Name");
//        test_select.add("Ssn");
//        test_select.add("Sex");
//        Sellect_filter all = new Sellect_filter("","",test_select);
//        // 성별 SELECT
//        Sellect_filter Sex = new Sellect_filter("성별","F", test_select);
////        연봉 SELECT
//        Sellect_filter Salary = new Sellect_filter("월급",90000, test_select);
//        부서
//        Sellect_filter Depart = new Sellect_filter("부서","Research", test_select);
//        //생일
//        Sellect_filter Birthday = new Sellect_filter("생일","07", test_select);
//        //부하직원
//        Sellect_filter Supvervising = new Sellect_filter("부하직원","987654321", test_select);
//        Insert 테스트 코드 돈을 많이 버는 vidigummy를 만드는 방법 다른 방법 있으려나?
//        Fname, Middle_name, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno 순으로 (모두 다 string이 편하다)
//        E_Insert insert_test = new E_Insert("Vidi", "R", "Gummy", "15125021", "1996-07-22", "SEOUL-GangDong", "M", "100000.00", "NULL", "1");

//  Delete Employee의 경우, 삭제를 하려면 객체를 가지고 있어야 한다.(당연쓰) So, Test Code를 Sellect_filter의 연봉 Select에 넣어놨으니 참고 바란다.
        // 원시적인 방법
//        conn.connect();
//        conn.modify("INSERT INTO `EMPLOYEE` (`Fname`, `Minit`, `Lname`, `Ssn`, `Bdate`, `Address`, `Sex`, `Salary`, `Super_ssn`, `Dno`) VALUES ('vidi', 'B', 'gummy', '', '1996-07-22', 'SEOUL', 'M', '30000.00', '666884444', '4')");
//        conn.close();
//        일은 안 하고 맨날 집에서 배 긁는 vidigummy의 흔적을 없애자.(testcode)
//        ArrayList<String> del_list = new ArrayList<String>();
//        del_list.add("15125021");
//        del_list.add("17125021");
//        E_Delete del_vidi = new E_Delete(del_list);
//        ArrayList test_Ssn = new ArrayList<String>();
//        test_Ssn.add("15125021");
//        Update_filter update_filter = new Update_filter("주소","California",test_Ssn);

        new MyGui();
    }
}