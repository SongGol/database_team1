package database;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.*;
import java.util.*;


//윈도우 창을 띄워주는데 쓰는 클래스 JFrame
//Text입력은 JTextField사용
//버튼클릭 이벤트는 JButton사용
//JButton에는 addActionListener를 달아서 버튼이 눌렸을 때 행동을 정의한다.
//JButton("버튼글자" or new ImageIcon("location")) 버튼에 글자를 넣거나 이미지 버튼을 만들 수 있다.
//선언한 버튼들은 add(버튼);으로 등록
//JPanel은 작은 창이라고 생각하면 될 듯. 똑같이 add()로 등록해주고 jpanel.add(button)으로 패널에 버튼 추가 가능
//JCheckbox 체크박스
//Jtextfield 사용자에게서 값을 입력받을 수 있는 EditText같은 것.
//JcomboBox 누르면 여러 목록들이 튀어나오는 것. spinner같은 것.
//JTable 검색결과 표를 만드는데 사용

public class MyGui extends JFrame{
    String[] tables;
    String val;
    String name;
    int va = 0;
    ArrayList<String> s;
    String insert;
    ArrayList<String> ta;
    String[] login;
    JTable resultTable;

    //검색범위 strings
    String[] SEARCH_SCOPES = {"전체", "부서", "성별", "연봉", "생일", "부하직원"};
    //checkbox strings
    String[] CHECK_OPTIONS = {"선택", "Name", "Ssn", "Bdate", "Address", "Sex", "Supervisor", "Salary", "Department"};
    //checkBox에 해당하는 JCheckBox
    JCheckBox[] checkBoxes = new JCheckBox[CHECK_OPTIONS.length - 1];
    //update strings
    String[] UPDATE_OPTIONS = {"Address", "Sex", "Salary"};
    //Sex String
    String[] SEXS = {"F", "M"};
    //insert strings
    String[] INSERTION_OPTIONS = {"First Name", "Middle Init", "Last Name", "Ssn", "Birthdate", "Address", "Sex", "Salary", "Super_ssn", "Dno"};
    //부서 정보
    String[] DEPARTMENT = {"Research", "Headquarters", "Administration"};
    //생일 정보
    String[] BIRTHDATE = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
    //동적으로 확인 되는 checkBox
    ArrayList<String> checkOptions = new ArrayList<>();

    //테스트용 더미 데이터
    Object[][] data = {{false, "F T W", "333445555", "1955-12-08", "638 V, H", "M", "40000", "J E B", "Research"},
            {false, "R K N", "666884444", "1962-09-15", "975 FO,H", "M", "38000", "F T W", "Research"},
            {false, "J E B", "888665555", "1937-11-10", "450 S,H", "M", "55000", "", "Research"},
            {false, "J S W", "987654321", "1941-06-20", "291 B,B", "F", "43000", "J E B", "Administration"}};

    public MyGui(/*ArrayList<String> s, ArrayList<String> ta, String name, String[] tables, String[] login*/) {
        LinkedHashSet<String> p = new LinkedHashSet();
        ArrayList<String> inx = new ArrayList();
        ArrayList<String> inx2 = new ArrayList();
        ArrayList<String> ssn = new ArrayList();

        //패널 정의
        JPanel searchOptionPanel = new JPanel();
        JPanel checkPanel = new JPanel();
        JPanel dBPanel = new JPanel();
        JPanel updatePanel = new JPanel();
        JPanel insertPanel = new JPanel();
        JPanel totalPanel = new JPanel();

        //좌측기준 정렬
        FlowLayout leftFL = new FlowLayout();
        leftFL.setAlignment(FlowLayout.LEFT);

        //layout 축 설정
        //searchOptionPanel.setLayout(new BoxLayout(searchOptionPanel, BoxLayout.X_AXIS));
        searchOptionPanel.setLayout(leftFL);
        checkPanel.setLayout(leftFL);
        //dBPanel.setLayout(new BoxLayout(dBPanel, BoxLayout.X_AXIS));
        updatePanel.setLayout(leftFL);
        insertPanel.setLayout(new BoxLayout(insertPanel, BoxLayout.Y_AXIS));
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));

        //기본 text 정의
        searchOptionPanel.add(new JLabel("검색 범위"), BorderLayout.WEST);
        checkPanel.add(new JLabel("검색 항목"));
        updatePanel.add(new JLabel("수정 "));

        //searchPanel에 comboBox넣기
        JComboBox<String> searchComboBox = new JComboBox<String>(SEARCH_SCOPES);
        searchComboBox.setPreferredSize(new Dimension(150, 30));
        JPanel searchWrapper = new JPanel();
        searchWrapper.add(searchComboBox, BorderLayout.WEST);
        searchOptionPanel.add(searchWrapper);

        //checkPanel에 checkbox넣기
        for(int i = 1; i < CHECK_OPTIONS.length; ++i) {
            checkBoxes[i - 1] = new JCheckBox(CHECK_OPTIONS[i], true);
            checkBoxes[i - 1].setPreferredSize(new Dimension(100, 30));
            checkPanel.add(checkBoxes[i - 1]);
        }
        //checkPanel에 검색버튼 넣기
        JButton searchBtn = new JButton("검색");
        checkPanel.add(searchBtn);

        //선택된 check option들 초기화
        setCheckOptions();

        //DBPanel 크기 조절
        Object[][] dummy = new Object[0][CHECK_OPTIONS.length];
        resultTable = makeTable(dummy, CHECK_OPTIONS);
        JScrollPane scrollpane = new JScrollPane(resultTable);


        //updatePanel에 comboBox넣기
        JComboBox<String> updateComboBox = new JComboBox<String>(UPDATE_OPTIONS);
        updateComboBox.setPreferredSize(new Dimension(100, 30));
        updatePanel.add(updateComboBox);
        //updatePanel에 textField넣기
        JTextField updateValue = new JTextField();
        updateValue.setPreferredSize(new Dimension(200, 30));
        updatePanel.add(updateValue);
        //updatePanel에 update버튼 넣기
        JButton updateBtn = new JButton("UPDATE");
        updateBtn.setPreferredSize(new Dimension(100, 30));
        updatePanel.add(updateBtn);
        //updatePanel에 선택 데이터 삭제 버튼 넣기
        JButton deleteBtn = new JButton("선택한 데이터 삭제");
        deleteBtn.setPreferredSize(new Dimension(150, 30));
        updatePanel.add(deleteBtn);
        updatePanel.setPreferredSize(new Dimension(300, 30));

        //insertPanel title 설정
        Border titleBorder = BorderFactory.createTitledBorder("새로운 직원 정보 추가");
        insertPanel.setBorder(titleBorder);
        //insertPanel.add(new JLabel("새로운 직원정보 추가"));

        //insertionPanels
        JPanel[] insertionPanels = new JPanel[INSERTION_OPTIONS.length];
        JTextField[] insertionTexts = new JTextField[INSERTION_OPTIONS.length - 1];
        JComboBox<String> sexComboBox = new JComboBox<String>(SEXS);
        sexComboBox.setPreferredSize(new Dimension(100, 20));
        for(int i = 0; i < INSERTION_OPTIONS.length; ++i) {
            JPanel panel = new JPanel();
            panel.setLayout(leftFL);
            panel.add(new JLabel(INSERTION_OPTIONS[i]));
            if (INSERTION_OPTIONS[i].equals("Sex")) {
                panel.add(sexComboBox);
            } else {
                JTextField text = new JTextField();
                text.setPreferredSize(new Dimension(200, 20));
                panel.add(text);
                insertionTexts[i > 6 ? i - 1 : i] = text;
            }
            insertionPanels[i] = panel;
            insertPanel.add(panel);
        }

        //버튼 집어넣기
        JButton insertBtn = new JButton("정보 추가하기");
        JPanel IBtnWrapper = new JPanel();
        IBtnWrapper.add(insertBtn);
        IBtnWrapper.setLayout(leftFL);
        insertBtn.setPreferredSize(new Dimension(150, 30));
        insertPanel.add(IBtnWrapper);



        //전체 Panel에 넣기
        totalPanel.add(searchOptionPanel);
        totalPanel.add(checkPanel);
        totalPanel.add(scrollpane);
        totalPanel.add(updatePanel);
        totalPanel.add(insertPanel);
        //this.add(totalPanel);

        //contentPane사용
        Container container = getContentPane();
        container.add(totalPanel);

        //checkBox와 option연동


        //search text
        JTextField searchText = new JTextField();
        searchText.setPreferredSize(new Dimension(200, 20));
        //search department comboBox
        JComboBox searchDepCB = new JComboBox(DEPARTMENT);
        searchDepCB.setPreferredSize(new Dimension(100, 30));
        //search birthdate comboBox
        JComboBox searchBirthCB = new JComboBox(BIRTHDATE);
        searchBirthCB.setPreferredSize(new Dimension(100, 30));
        //search sex comboBox
        JComboBox searchSCB = new JComboBox(SEXS);
        searchSCB.setPreferredSize(new Dimension(100, 30));
        //검색범위가 변경되면 세부 옵션이 달라짐
        searchComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource(); // 콤보박스 알아내기
                int index = cb.getSelectedIndex();// 선택된 아이템의 인덱스
                //기존에 추가된 것들은 다 제거
                searchWrapper.remove(searchDepCB);
                searchWrapper.remove(searchSCB);
                searchWrapper.remove(searchText);
                searchWrapper.remove(searchBirthCB);
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        searchWrapper.add(searchDepCB);
                        break;
                    case 2:
                        searchWrapper.add(searchSCB);
                        break;
                    case 3:
                        searchWrapper.add(searchText);
                        break;
                    case 4:
                        searchWrapper.add(searchBirthCB);
                        break;
                    default:
                        searchWrapper.add(searchText);
                }

                System.out.println(index);
            }
        });

        //Action Listener
        //검색 범위와 checkbox에서 column명을 가져와서 검색
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //테이블 초기화
                removeALlRecords(resultTable);

                setCheckOptions();
                ArrayList<String> checkColumns = new ArrayList<>();
                for (int i = 1; i < checkOptions.size(); ++i) {
                    checkColumns.add(checkOptions.get(i));
                }

                int index = searchComboBox.getSelectedIndex();// 선택된 아이템의 인덱스
                Sellect_filter searchWithSelector;
                switch (index) {
                    case 0:

                        for (String k : checkOptions) {
                            System.out.println("check options: "+k);
                        }



                        searchWithSelector = new Sellect_filter("", "", checkColumns);
                        break;
                    case 1:
                        //null pointer exception발생
                        System.out.println("부서 index: " + searchDepCB.getSelectedIndex() + ", " + DEPARTMENT[searchDepCB.getSelectedIndex()]);
                        searchWithSelector = new Sellect_filter("부서", DEPARTMENT[searchDepCB.getSelectedIndex()], checkColumns);
                        break;
                    case 2:
                        //성별에 따라 출력
                        searchWithSelector = new Sellect_filter("성별", searchSCB.getSelectedIndex() == 0 ? "F" : "M", checkColumns);
                        break;
                    case 3:
                        //연봉에 따라 출력
                        searchWithSelector = new Sellect_filter("연봉", searchText.getText().isEmpty() ? "0" : searchText.getText(), checkColumns);
                        break;
                    case 4:
                        //생일에 따라 출력
                        String birth = BIRTHDATE[searchBirthCB.getSelectedIndex()];
                        int month = Integer.parseInt(birth.substring(0, birth.length()-1));
                        searchWithSelector = new Sellect_filter("생일", String.format("%02d", month), checkColumns);
                        break;
                    default:
                        //상사 번호를 쓰면 부하가 출력
                        searchWithSelector = new Sellect_filter("부하직원", searchText.getText().isEmpty() ? "0" : searchText.getText(), checkColumns);
                }
                //checkbox 재확인
                //setCheckOptions();
                //테이블 재정의
                //resultTable = makeTable(new Object[0][checkOptions.size()], checkOptions.toArray(String[]::new));

                //테이블에 보여줄 원소 추가해주기
                Object[] mData = new Object[checkColumns.size() + 1];
                for (int i = 1; i < searchWithSelector.result.size(); ++i) {
                    String[] subStr = searchWithSelector.result.get(i).split("&");
                    mData[0]= false;
                    for (int k = 0; k < subStr.length; ++k) {
                        mData[k + 1] = subStr[k];
                    }
                    addRecord(resultTable, mData);
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(updateComboBox.getSelectedIndex());
                System.out.println(updateValue.getText());

                String what = "";
                String input = updateValue.getText();;
                switch (updateComboBox.getSelectedIndex()) {
                    case 0:
                        //주소 변경
                        what = "주소";
                        break;
                    case 1:
                        //성별 변경
                        what = "성별";
                        break;
                    default:
                        //연봉 변경
                        what = "월급";
                }
                DefaultTableModel model = (DefaultTableModel)resultTable.getModel();
                ArrayList<String> targetSsn = new ArrayList<>();
                for (int i = model.getRowCount() - 1; i >= 0; i--) {
                    if (model.getValueAt(i, 0).equals(true)){
                        // arrayList에 저장
                        targetSsn.add(model.getValueAt(i, 2).toString());
                        //테이블에서 수정
                        if (what.equals("주소")){
                            model.setValueAt(input, i, 4);
                        } else if (what.equals("성별")) {
                            model.setValueAt(input, i, 5);
                        } else {
                            model.setValueAt(input, i, 7);
                        }
                    }
                }
                //업데이트
                Update_filter update_filter = new Update_filter(what, input, targetSsn);
            }
        });

        insertBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (JTextField text: insertionTexts) {
                    System.out.println(text.getText());
                }
                System.out.println(sexComboBox.getSelectedIndex());

                E_Insert insertPerson = new E_Insert(
                        insertionTexts[0].getText().isEmpty() ? "Default" : insertionTexts[0].getText(),
                        insertionTexts[1].getText().isEmpty() ? "Default" : insertionTexts[1].getText(),
                        insertionTexts[2].getText().isEmpty() ? "Default" : insertionTexts[2].getText(),
                        insertionTexts[3].getText(),
                        insertionTexts[4].getText().isEmpty() ? "1111-11-11" : insertionTexts[4].getText(),
                        insertionTexts[5].getText().isEmpty() ? "Default" : insertionTexts[5].getText(),
                        SEXS[sexComboBox.getSelectedIndex()],
                        insertionTexts[6].getText().isEmpty() ? "0.00" : insertionTexts[6].getText(),
                        insertionTexts[7].getText().isEmpty() ? "NULL" : insertionTexts[7].getText(),
                        insertionTexts[8].getText().isEmpty() ? "4" : insertionTexts[8].getText()
                );

//                for (JTextField t: insertionTexts) {
//                    System.out.println("insert: " + t.getText());
//                }
//                System.out.println("insert: "+SEXS[sexComboBox.getSelectedIndex()]);
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //checkBox가 선택된 요소만 제거
                removeSelectedRecords(resultTable);
            }
        });

        setTitle("database assignment1");
        pack();
        //전체 창 사이즈
        setSize(1000, 800);
        //창을 눈에 보이게 설정
        setVisible(true);
    }

    private void setCheckOptions() {
        checkOptions.clear();
        checkOptions.add("선택");
        for(JCheckBox cb: checkBoxes) {
            if (cb.isSelected()) {
                checkOptions.add(cb.getText());
            }
        }
    }

    private JTable makeTable(Object[][] data, String[] header) {
        DefaultTableModel model = new DefaultTableModel(data, header);
        JTable table = new JTable(model) {
            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
        };
        table.setPreferredSize(new Dimension(650, 500));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        return table;
    }

    //테이블에 기록 추가
    private void addRecord(JTable table ,Object[] record) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();

        for (Object o: record) {
            System.out.println("addRecord " + o);
        }

        model.addRow(record);
    }

    //테이블에서 선택된 기록 삭제
    private void removeSelectedRecords(JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        ArrayList<String> targetSsn = new ArrayList<>();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            if (model.getValueAt(i, 0).equals(true)){
                // arrayList에 저장
                targetSsn.add(model.getValueAt(i, 2).toString());
                //테이블에서만 삭제
                model.removeRow(i);
                System.out.println("selected row: "+i+", ssn: "+model.getValueAt(i, 2).toString());
            }
        }
        //삭제
        E_Delete delete = new E_Delete(targetSsn);
    }

    //테이블에서 모든 기록 삭제
    private void removeALlRecords(JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
}
