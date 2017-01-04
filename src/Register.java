import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

/**
 * Created by kapilsoni on 04-01-2017.
 */

public class Register extends JPanel implements ActionListener{
    JLabel userL = new JLabel("Username: ");
    JTextField userTF = new JTextField();

    JLabel passL = new JLabel("Password");
    JPasswordField passTF = new JPasswordField();

    JLabel passLC = new JLabel("Confirm Password");
    JPasswordField passTFC = new JPasswordField();

    JButton register = new JButton("Register");
    JButton back = new JButton("Back");

    public Register(){
        JPanel loginP = new JPanel();
        loginP.setLayout(new GridLayout(4,2));

        loginP.add(userL);
        loginP.add(userTF);

        loginP.add(passL);
        loginP.add(passTF);

        loginP.add(passLC);
        loginP.add(passTFC);

        register.addActionListener(this);
        back.addActionListener(this);
        loginP.add(register);
        loginP.add(back);
        add(loginP);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register && passTF.getPassword().length > 0 && userTF.getText().length() > 0){
            String pass = new String(passTF.getPassword());
            String confirm = new String(passTFC.getPassword());
            if (pass.equals(confirm)) {
                try {

                    /////////////
                    BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
                    String line = input.readLine();
                    while(line != null){
                        StringTokenizer st = new StringTokenizer(line);
                        if (userTF.getText().equals(st.nextToken())) {
                            System.out.println("User Already Exists");
                            return;
                        }
                        line = input.readLine();
                    }
                    input.close();
                    /////////////

                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(pass.getBytes());
                    byte byteData[] = md.digest();
                    StringBuffer sb = new StringBuffer();
                    for (int i=0; i< byteData.length; i++) {
                        sb.append(Integer.toString((byteData[i] & 0xFF)+ 0x100, 16).substring(1));
                    }
                    //////////////
                    BufferedWriter output = new BufferedWriter(new FileWriter("passwords.txt",true));
                    output.write(userTF.getText()+" "+sb.toString()+"\n");
                    output.close();
                    Login login = (Login) getParent();
                    login.cl.show(login,"login");
                    //////////////
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if(e.getSource() == back){
            Login login = (Login) getParent();
            login.cl.show(login,"login");
        }
    }
}