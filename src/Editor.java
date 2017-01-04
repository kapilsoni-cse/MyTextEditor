import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by kapilsoni on 04-01-2017.
 */
public class Editor extends JPanel implements ActionListener {

    File file;
    JButton save = new JButton("Save");
    JButton savec = new JButton("Save & Close");
    JTextArea text = new JTextArea(20,40);

    public Editor(String s) throws IOException {
        file = new File(s);
        save.addActionListener(this);
        savec.addActionListener(this);
        if (file.exists()) {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line = input.readLine();
            while(line != null){
                text.append(line+"\n");
                line = input.readLine();
            }
            input.close();

        }
        add(save);
        add(savec);
        add(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileWriter out = new FileWriter(file);
            out.write(text.getText());
            out.close();
            if (e.getSource() == savec) {
                Login login = (Login) getParent();
                login.cl.show(login,"fb");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
