import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

/**
 * Created by kapilsoni on 04-01-2017.
 */
public class Login extends JPanel implements ActionListener{

    JLabel userL = new JLabel("Username: ");
    JTextField userTF = new JTextField();

    JLabel passL = new JLabel("Password: ");
    JPasswordField passTF = new JPasswordField();

    JButton login = new JButton("Login");
    JButton register = new JButton("Register");

    JPanel loginP = new JPanel(new GridLayout(4,2));
    JPanel panel = new JPanel();

    JLabel alert = new JLabel("User or Password Error!");

    CardLayout cl;

    Login(){
        setLayout(new CardLayout());
        loginP.add(userL);
        loginP.add(userTF);
        loginP.add(passL);
        loginP.add(passTF);
        login.addActionListener(this);
        register.addActionListener(this);
        loginP.add(login);
        loginP.add(register);
        loginP.add(alert).setVisible(false);
        panel.add(loginP);
        add(panel,"login");
        cl = (CardLayout) getLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            add(new Register(),"register");
            cl.show(this,"register");
        }

        if (e.getSource() == login) {
            try {
                BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
                String pass = null;
                String line = input.readLine();
                while(line != null){
                    StringTokenizer st = new StringTokenizer(line);
                    if(userTF.getText().equals(st.nextToken())){
                        pass = st.nextToken();
                    }else{
                        alert.setVisible(true);
                    }
                    line = input.readLine();
                }
                input.close();
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(new String(passTF.getPassword()).getBytes());
                byte byteData[] = md.digest();
                StringBuffer sb = new StringBuffer();
                for (int i=0; i< byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xFF)+ 0x100, 16).substring(1));
                    if (pass.equals(sb.toString())) {
                        add(new FileBrowser(userTF.getText()),"fb");
                        cl.show(this,"fb");
                    }
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        Login login = new Login();
        frame.add(login);
        frame.setVisible(true);

    }
}
