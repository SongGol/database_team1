package database;

import javax.swing.*;
import javax.swing.border.Border;
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

    //검색범위 strings
    String[] SEARCH_SCOPES = {"전체", "부서", "성별", "연봉", "생일", "부하직원"};
    //checkbox strings
    String[] CHECK_OPTIONS = {"Name", "Ssn", "Bdate", "Address", "Sex", "Salary", "Supervisor", "Department"};
    //update strings
    String[] UPDATE_OPTIONS = {"Address", "Sex", "Salary"};
    //Sex String
    String[] SEXS = {"F", "M"};

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

        //기본 text 정의
        searchOptionPanel.add(new JLabel("검색 범위"));
        checkPanel.add(new JLabel("검색 항목"));
        updatePanel.add(new JLabel("수정"));

        //layout 축 설정
        searchOptionPanel.setLayout(new BoxLayout(searchOptionPanel, BoxLayout.X_AXIS));
        checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.X_AXIS));
        //dBPanel.setLayout(new BoxLayout(dBPanel, BoxLayout.X_AXIS));
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.X_AXIS));
        insertPanel.setLayout(new BoxLayout(insertPanel, BoxLayout.Y_AXIS));
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));

        //searchPanel에 comboBox넣기
        JComboBox<String> searchComboBox = new JComboBox<String>(SEARCH_SCOPES);
        searchComboBox.setPreferredSize(new Dimension(200, 20));
        searchOptionPanel.add(searchComboBox);

        //checkPanel에 checkbox넣기
        JCheckBox[] checkBoxes = new JCheckBox[CHECK_OPTIONS.length];
        for(int i = 0; i < CHECK_OPTIONS.length; ++i) {
            checkBoxes[i] = new JCheckBox(CHECK_OPTIONS[i], true);
            checkBoxes[i].setPreferredSize(new Dimension(90, 50));
            checkPanel.add(checkBoxes[i]);
        }
        //checkPanel에 검색버튼 넣기
        JButton searchBtn = new JButton("검색");
        checkPanel.add(searchBtn);

        //DBPanel 크기 조절
        JTable table = new JTable(new String[8][8], CHECK_OPTIONS);
        table.setRowHeight(40);
        JScrollPane scrollpane = new JScrollPane(table);
        table.setPreferredSize(new Dimension(700, 500));



        //updatePanel에 comboBox넣기
        JComboBox<String> updateComboBox = new JComboBox<String>(UPDATE_OPTIONS);
        updateComboBox.setPreferredSize(new Dimension(200, 20));
        updatePanel.add(updateComboBox);
        //updatePanel에 textField넣기
        JTextField updateValue = new JTextField();
        updateValue.setPreferredSize(new Dimension(200, 20));
        updatePanel.add(updateValue);
        //updatePanel에 update버튼 넣기
        JButton updateBtn = new JButton("UPDATE");
        updatePanel.add(updateBtn);
        //updatePanel에 선택 데이터 삭제 버튼 넣기
        JButton deleteBtn = new JButton("선택한 데이터 삭제");
        updatePanel.add(deleteBtn);

        //insertPanel title 설정
        Border titleBorder = BorderFactory.createTitledBorder("새로운 직원 정보 추가");
        insertPanel.setBorder(titleBorder);

        //insertPanel First name
        JPanel fNamePanel = new JPanel();
        fNamePanel.add(new JLabel("First Name"));
        //textField넣기
        JTextField FNameText = new JTextField();
        FNameText.setPreferredSize(new Dimension(200, 20));
        fNamePanel.add(FNameText);

        //insertPanel Middle init
        JPanel midInitPanel = new JPanel();
        midInitPanel.add(new JLabel("Middle Init"));
        //textField넣기
        JTextField MInitText = new JTextField();
        MInitText.setPreferredSize(new Dimension(200, 20));
        midInitPanel.add(MInitText);

        //insertPanel Last name
        JPanel lNamePanel = new JPanel();
        lNamePanel.add(new JLabel("Last Name"));
        //textField넣기
        JTextField LNameText = new JTextField();
        LNameText.setPreferredSize(new Dimension(200, 20));
        lNamePanel.add(LNameText);

        //insertPanel Ssn
        JPanel ssnPanel = new JPanel();
        ssnPanel.add(new JLabel("Ssn"));
        //textField넣기
        JTextField SsnText = new JTextField();
        SsnText.setPreferredSize(new Dimension(200, 20));
        ssnPanel.add(SsnText);

        //insertPanel Birthdate
        JPanel birthPanel = new JPanel();
        birthPanel.add(new JLabel("Birthdate"));
        //textField넣기
        JTextField birthText = new JTextField();
        birthText.setPreferredSize(new Dimension(200, 20));
        birthPanel.add(birthText);

        //insertPanel Address
        JPanel addrPanel = new JPanel();
        addrPanel.add(new JLabel("Address"));
        //textField넣기
        JTextField addrText = new JTextField();
        addrText.setPreferredSize(new Dimension(200, 20));
        addrPanel.add(addrText);

        //insertPanel Sex
        JPanel sexPanel = new JPanel();
        sexPanel.add(new JLabel("Sex"));
        //insertPanel comboBox넣기
        JComboBox<String> sexComboBox = new JComboBox<String>(SEXS);
        sexPanel.add(sexComboBox);

        //insertPanel Salary
        JPanel salaryPanel = new JPanel();
        salaryPanel.add(new JLabel("Salary"));
        //textField넣기
        JTextField salaryText = new JTextField();
        salaryText.setPreferredSize(new Dimension(200, 20));
        salaryPanel.add(salaryText);

        //insertPanel Super_Ssn
        JPanel sSsnPanel = new JPanel();
        sSsnPanel.add(new JLabel("Super_ssn"));
        //textField넣기
        JTextField SssnText = new JTextField();
        SssnText.setPreferredSize(new Dimension(200, 20));
        sSsnPanel.add(SssnText);

        //insertPanel Dno
        JPanel dnoPanel = new JPanel();
        dnoPanel.add(new JLabel("Dno"));
        //textField넣기
        JTextField dnoText = new JTextField();
        dnoText.setPreferredSize(new Dimension(200, 20));
        dnoPanel.add(dnoText);

        //insertPanel 패널들 집어넣기
        insertPanel.add(fNamePanel);
        insertPanel.add(midInitPanel);
        insertPanel.add(lNamePanel);
        insertPanel.add(ssnPanel);
        insertPanel.add(birthPanel);
        insertPanel.add(addrPanel);
        insertPanel.add(sexPanel);
        insertPanel.add(salaryPanel);
        insertPanel.add(sSsnPanel);
        insertPanel.add(dnoPanel);
        //버튼 집어넣기
        JButton insertBtn = new JButton("정보 추가하기");
        insertPanel.add(insertBtn);



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

        pack();
        //전체 창 사이즈
        setSize(1000, 800);
        //창을 눈에 보이게 설정
        setVisible(true);
    }


    public static void main(String[] args) {
        new MyGui();
    }
}
