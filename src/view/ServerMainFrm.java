package view;

import control.ServerCtr;
import model.IPAddress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerMainFrm extends JFrame implements ActionListener{
    private JTextField txtServerHost, txtServerPort;
    private JButton btnStartServer, btnStopServer;
    private JTextArea mainText;
    private ServerCtr myServer;

    public ServerMainFrm(){
        super("TCP server view");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Server TCP/IP");
        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lblTitle.setBounds(new Rectangle(150, 15, 200, 30));
        mainPanel.add(lblTitle, null);

        JLabel lblHost = new JLabel("Server host:");
        lblHost.setBounds(new Rectangle(10, 70, 150, 25));
        mainPanel.add(lblHost, null);

        txtServerHost = new JTextField(50);
        txtServerHost.setBounds(new Rectangle(170, 70, 150, 25));
        txtServerHost.setText("localhost");
        txtServerHost.setEditable(false);
        mainPanel.add(txtServerHost,null);

        JLabel lblPort = new JLabel("Server port:");
        lblPort.setBounds(new Rectangle(10, 100, 150, 25));
        mainPanel.add(lblPort, null);

        txtServerPort = new JTextField(50);
        txtServerPort.setBounds(new Rectangle(170, 100, 150, 25));
        mainPanel.add(txtServerPort,null);

        btnStartServer = new JButton("Start server");
        btnStartServer.setBounds(new Rectangle(10, 150, 150, 25));
        btnStartServer.addActionListener(this);
        mainPanel.add(btnStartServer,null);

        btnStopServer = new JButton("Stop server");
        btnStopServer.setBounds(new Rectangle(170, 150, 150, 25));
        btnStopServer.addActionListener(this);
        btnStopServer.setEnabled(false);
        mainPanel.add(btnStopServer,null);

        JScrollPane jScrollPane1 = new JScrollPane();
        mainText = new JTextArea("");
        jScrollPane1.setBounds(new Rectangle(10, 200, 610, 240));
        mainPanel.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mainText, null);

        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(640, 480));
        this.setResizable(false);
    }

    public void showServerInfor(IPAddress addr) {
        txtServerHost.setText(addr.getHost());
        txtServerPort.setText(addr.getPort()+"");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // TODO Auto-generated method stub
        if(ae.getSource() instanceof JButton) {
            JButton clicked = (JButton)ae.getSource();
            if(clicked.equals(btnStartServer)) {// start button
                if(!txtServerPort.getText().isEmpty() && (txtServerPort.getText().trim().length() > 0)) {//custom port
                    int port = Integer.parseInt(txtServerPort.getText().trim());
                    myServer = new ServerCtr(this, port);
                    // new ServerCtr(this, port);
                }else {// default port
                    myServer = new ServerCtr(this);
                    //new ServerCtr(this);
                }
                btnStopServer.setEnabled(true);
                btnStartServer.setEnabled(false);
            }else if(clicked.equals(btnStopServer)) {// stop button
                if(myServer != null) {
                    myServer.stopServer();
                    myServer = null;
                }
                btnStopServer.setEnabled(false);
                btnStartServer.setEnabled(true);
                txtServerHost.setText("localhost");
            }
        }
    }

    public void showMessage(String s){
        mainText.append("\n"+s);
        mainText.setCaretPosition(mainText.getDocument().getLength());
    }

    public static void main(String[] args) {
        ServerMainFrm view = new ServerMainFrm();
        view.setVisible(true);
    }
}