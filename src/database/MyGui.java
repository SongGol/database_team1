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
    //update strings
    String[] UPDATE_OPTIONS = {"Address", "Sex", "Salary"};
    //Sex String
    String[] SEXS = {"F", "M"};
    //insert strings
    String[] INSERTION_OPTIONS = {"First Name", "Middle Init", "Last Name", "Ssn", "Birthdate", "Address", "Sex", "Salary", "Super_ssn", "Dno"};
    //부서 정보
    String[] DEPARTMENT = {"Research", "Headquarter", "Administration"};
    //생일 정보
    String[] BIRTHDATE = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};


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
        JCheckBox[] checkBoxes = new JCheckBox[CHECK_OPTIONS.length - 1];
        for(int i = 1; i < CHECK_OPTIONS.length; ++i) {
            checkBoxes[i - 1] = new JCheckBox(CHECK_OPTIONS[i], true);
            checkBoxes[i - 1].setPreferredSize(new Dimension(100, 30));
            checkPanel.add(checkBoxes[i - 1]);
        }
        //checkPanel에 검색버튼 넣기
        JButton searchBtn = new JButton("검색");
        checkPanel.add(searchBtn);


        //DBPanel 크기 조절
        //JTable table = new JTable(data, CHECK_OPTIONS);
        //table.setRowHeight(40);
        //table.setPreferredSize(new Dimension(700, 500));
        //JScrollPane scrollpane = new JScrollPane(resultTable);
        //JTable resultTable = makeTable(data, CHECK_OPTIONS);
        Object[][] dummy = new Object[0][CHECK_OPTIONS.length];
        JTable resultTable = makeTable(dummy, CHECK_OPTIONS);
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

                for(JCheckBox cb: checkBoxes) {
                    if (cb.isSelected()) {
                        System.out.println(cb.getLabel());
                    }
                }

                int index = searchComboBox.getSelectedIndex();// 선택된 아이템의 인덱스
                Sellect_filter searchWithSelector;
                switch (index) {
                    case 0:
                        searchWithSelector = new Sellect_filter("", "");
                        break;
                    case 1:
                        searchWithSelector = new Sellect_filter("부서", "");
                        break;
                    case 2:
                        //성별에 따라 출력
                        searchWithSelector = new Sellect_filter("성별", searchSCB.getSelectedIndex() == 0 ? "F" : "M");
                        break;
                    case 3:
                        searchWithSelector = new Sellect_filter("연봉", "");
                        break;
                    case 4:
                        searchWithSelector = new Sellect_filter("생일", "");
                        break;
                    default:
                        searchWithSelector = new Sellect_filter("부하직원", "");
                }

                for(String s: searchWithSelector.result) {
                    System.out.println("aa " + s);
                    String[] ss = s.split("&");
                    for(String sss: ss) {
                        System.out.print(sss + " ");
                    }
                    System.out.println();
                }

                //테이블에 보여줄 원소 추가해주기
                Object[] mData = new Object[CHECK_OPTIONS.length];
                for (int i = 1; i < searchWithSelector.result.size(); ++i) {
                    String[] subStr = searchWithSelector.result.get(i).split("&");
                    for (int k = 0; k < subStr.length; ++k) {
                        mData[k + 1] = subStr[k];
                    }
                    addRecord(resultTable, mData);
                }


                //resultTable = makeTable(mData, CHECK_OPTIONS);
                //scrollpane.add(resultTable);

                

                //받은 데이터 테이블에 넣어주기

                /*
                try {
                    insert = text.getText();
                    Dbinsert dbi = new Dbinsert(insert, name, login);
                    Db2 db = new Db2(name, login);
                    dispose();
                } catch (SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }*/
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(updateComboBox.getSelectedIndex());
                System.out.println(updateValue.getText());
                /*
                try {
                    for (int i = 0; i < resultTable.getRowCount(); i++) {
                        String array[] = ta.get(i).split("&");
                        if (check[i].isSelected()) {
                            ssn.add(array[1]);
                        }
                        if (resultTable.getColumn(0)[i] == true) {

                        }
                    }
                    insert = text.getText();
                    Dbupdate dbu = new Dbupdate(ssn, insert, name, login);

                    Db2 db = new Db2(name, login);
                    dispose();

                } catch (SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }*/
            }
        });

        insertBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (JTextField text: insertionTexts) {
                    System.out.println(text.getText());
                }
                System.out.println(sexComboBox.getSelectedIndex());

                /*
                try {
                    insert = text.getText();
                    Dbinsert dbi = new Dbinsert(insert, name, login);
                    Db2 db = new Db2(name, login);
                    dispose();
                } catch (SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }*/
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(resultTable.getRowCount());
                System.out.println(resultTable.getColumn("선택"));
                //몇번 행이 선택되었는지 확인
                for(int i = 0; i < resultTable.getRowCount(); ++i) {
                    System.out.println(resultTable.getValueAt(i, 0));
                }

                /*
                try {
                    insert = text.getText();
                    Dbinsert dbi = new Dbinsert(insert, name, login);
                    Db2 db = new Db2(name, login);
                    dispose();
                } catch (SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }*/
            }
        });
        /*

        String[] dname = {"부서별", "Research", "Administration", "Headquarters"};
        this.name = name;
        this.s = new ArrayList<String>(); //s;
        this.ta = new ArrayList<String>();//ta;
        this.login = login;
        int size = 0;
        String[] header = new String[s.size()];

        for (String temp : s) {
            header[size++] = temp;
        }
        String[][] cc = new String[ta.size()][s.size()];
        for (int i = 0; i < ta.size(); i++) {
            String array[] = ta.get(i).split("&");
            for (int j = 0; j < s.size(); j++) {
                cc[i][j] = array[j];
            }
        }

        JButton[] button = new JButton[s.size()];
        JPanel checkPanel = new JPanel();
        JPanel atrPanel = new JPanel();
        JCheckBox[] atr = new JCheckBox[s.size()];
        JCheckBox[] check = new JCheckBox[ta.size()];
        JTextField text = new JTextField();
        JButton updateButton = new JButton("선택수정");
        JButton insertButton = new JButton("삽입");
        JButton deleteTypedButton = new JButton("입력삭제");
        JButton deleteSelectedButton = new JButton("선택삭제");

        Container d = getContentPane();
        setTitle("database");
        d.revalidate();
        JPanel c = new JPanel();
        // c.setLayout(new FlowLayout());


        JComboBox combo = new JComboBox(tables);
        c.add(combo, BorderLayout.NORTH);
        JComboBox combo2 = new JComboBox(dname);
        c.add(combo2, BorderLayout.NORTH);
        Font font = new Font("돋움", 4, 25);
        Font font2 = new Font("돋움", 4, 20);
        text.setFont(font);
        JTable table = new JTable(cc, header);
        table.setRowHeight(40);
        JScrollPane scrollpane = new JScrollPane(table);
        table.setPreferredSize(new Dimension(700, 500));
        scrollpane.setPreferredSize(new Dimension(960, 500));
        c.add(updateButton);
        updateButton.setPreferredSize(new Dimension(100, 40));
        c.add(insertButton);
        insertButton.setPreferredSize(new Dimension(100, 40));
        deleteSelectedButton.setPreferredSize(new Dimension(100, 40));
        c.add(deleteTypedButton);
        c.add(deleteSelectedButton);
        c.add(text);
        text.setPreferredSize(new Dimension(900, 40));
        deleteTypedButton.setPreferredSize(new Dimension(100, 40));
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                p.add(e.getActionCommand());
            }
        };

        for (int m = 0; m < ta.size(); m++) {
            check[m] = new JCheckBox("tuple" + (m + 1) + "");
            check[m].setPreferredSize(new Dimension(90, 50));
            checkPanel.add(check[m], BorderLayout.CENTER);
        }
        c.add(checkPanel);


        for (int j = 0; j < s.size(); j++) {
            atr[j] = new JCheckBox();
            atr[j].setText(s.get(j));
            atr[j].setFont(font2);
            atr[j].setSelected(true);
            atr[j].setPreferredSize(new Dimension(100, 100));
            atr[j].addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    int j = 0;
                    for (int i = 0; i < s.size(); i++) {
                        if (atr[i].isSelected()) {
                            j++;
                        }

                    }
                    for (int i = 0; i < s.size(); i++) {

                        if (atr[i].isSelected()) {
                            System.out.println(i);
                            table.getColumn(atr[i].getText()).setWidth(960 / j);
                            table.getColumn(atr[i].getText()).setMinWidth(960 / j);
                            table.getColumn(atr[i].getText()).setMaxWidth(960 / j);


                        } else if (!atr[i].isSelected()) {
                            table.getColumn(atr[i].getText()).setWidth(0);
                            table.getColumn(atr[i].getText()).setMinWidth(0);
                            table.getColumn(atr[i].getText()).setMaxWidth(0);

                        }

                    }
                    c.repaint();
                    d.add(c);
                }
            });

            atrPanel.add(atr[j], BorderLayout.SOUTH);
        }
        c.add(atrPanel);
        c.add(scrollpane, BorderLayout.SOUTH);

        d.add(c);*/

        // 콤보박스에 Action 리스너 등록. 선택된 아이템의 이미지 출력

        setTitle("database assignment1");
        pack();
        //전체 창 사이즈
        setSize(1000, 800);
        //창을 눈에 보이게 설정
        setVisible(true);
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
    public void addRecord(JTable table ,Object[] record) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();

        for (Object o: record) {
            System.out.println("addRecord" + o);
        }

        model.addRow(record);
    }

    //테이블에서 기록 삭제
    public void removeRecord(JTable table, int index) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        if (index < 0) {
            if(table.getRowCount() == 0)//비어있는 테이블이면
                return;
            index = 0;
        }
        model.removeRow(index);
    }

    //테이블에서 모든 기록 삭제
    public void removeALlRecords(JTable table) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
}
