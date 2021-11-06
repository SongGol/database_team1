package database;

public class Main {
    public static void main(String args[]) {
        Connector conn = new Connector();
        System.out.println("hh...");
        new MyGui();
//모든 EMPLOYEE의 정보를 가져오는 법
        Sellect_filter all = new Sellect_filter("", "");
        // 성별 SELECT
        Sellect_filter Sex = new Sellect_filter("성별", "M");
//        연봉 SELECT
        Sellect_filter Salary = new Sellect_filter("연봉", 10000);
        //생일
        Sellect_filter Birthday = new Sellect_filter("생일", "1965-01-09");
        //부하직원
        Sellect_filter Supvervising = new Sellect_filter("부하직원", "Franklin T Wong");

    }
}