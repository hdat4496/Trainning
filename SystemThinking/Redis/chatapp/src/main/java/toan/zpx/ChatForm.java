package toan.zpx;
import javax.swing.*;



public class ChatForm extends javax.swing.JFrame {
    private String username = "";
    private JButton btnSend;
    private JScrollPane jScrollPane1;
    private JTextArea txtAllMsg;
    private JTextField txtNewMsg;
    public ChatForm() {
        initComponents();
        setVisible(true);

        while(username.equals("")) {
            username = javax.swing.JOptionPane.showInputDialog(this, "Enter your username: ");
            if(username == null) {
                this.dispose();
                return;
            }
            App.addUser(username);
        }

        if(username != null) {
            setTitle(username);
        }
    }

    private void initComponents() {
        btnSend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAllMsg = new javax.swing.JTextArea();
        txtNewMsg = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSend.setText("Send");

        txtAllMsg.setColumns(20);
        txtAllMsg.setRows(5);
        jScrollPane1.setViewportView(txtAllMsg);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNewMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNewMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );



        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                App.shutdow();
                System.exit(0);
            }
        });



        txtNewMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionSend(evt);
            }
        });


        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionSend(evt);
            }
        });


        pack();
        setLocationRelativeTo(null);
    }

    public void appendMessage(String str) {
        txtAllMsg.append(str + "\n");
    }


    private void actionSend(java.awt.event.ActionEvent evt) {
        String str = txtNewMsg.getText();
        App.addhMsg(username, str);
        txtNewMsg.setText("");
        txtNewMsg.requestFocus();
    }



}
