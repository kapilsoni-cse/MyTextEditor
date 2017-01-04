import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by kapilsoni on 04-01-2017.
 */
public class FileBrowser extends JPanel implements ActionListener{

    JLabel label = new JLabel("File List");
    JButton newFile = new JButton("New File");
    JButton openFile = new JButton("Open");
    JTextField newFileTF = new JTextField(10);
    ButtonGroup bg;
    File directory;

    public FileBrowser(String dir){
        directory = new File(dir);
        directory.mkdir();
        JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+3,1));
        fileList.add(label);
        bg = new ButtonGroup();

        for (File file : directory.listFiles()) {
            JRadioButton radio = new JRadioButton(file.getName());
            radio.setActionCommand(file.getName());
            bg.add(radio);
            fileList.add(radio);
        }

        JPanel newP = new JPanel();
        newP.add(newFileTF);
        newP.add(newFile);
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        fileList.add(openFile);
        fileList.add(newP);
        add(fileList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Login login = (Login) getParent();
        if (e.getSource() == openFile) {
            try {
                login.add(new Editor(directory.getName()+"\\"+bg.getSelection().getActionCommand()),"Editor");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            login.cl.show(login,"Editor");
        }
        if (e.getSource() == newFile) {
            String file = directory.getName()+"\\"+newFileTF.getText()+".txt";
            if (newFileTF.getText().length() > 0 && !(new File(file).exists())) {
                try {
                    login.add(new Editor(file),"Editor");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                login.cl.show(login,"Editor");
            }
        }
    }
}
