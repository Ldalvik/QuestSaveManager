package quest.save.manager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    private JPanel rootPanel, debugPane;
    private JTabbedPane tabbedPane;
    private JLabel debugBox;

    ViewCreator vc = new ViewCreator();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("Quest Save Manager v" + Data.VERSION + "b  -  made with love by Root and (YouStayGold? HFP?) :)");
        frame.setContentPane(new Main().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(700, 700));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public Main() {
        try {
            if (Utils.isUpdateAvailable()) {
                int result = JOptionPane.showConfirmDialog(rootPanel,
                        "An update is available. Would you like to open the download page?",
                        "Update available", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) Desktop.getDesktop().browse(new URI(Data.GITHUB));
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        debugPane();
    }

    private void debugPane() {
        debugPane.setLayout(null);
        debugBox = vc.makeLabel(debugPane, "<>", 10, 40, 600, 500);
        vc.makeButton(debugPane, "Check Devices", 10, 10, 100, 30)
                .addActionListener(event -> {
                    try {
                        if (ADB.isQuestConnected()) {
                            debugBox.setText(
                                    "<html>" +
                                            "<h5 style=\"color: green\">DEVICE CONNECTED</h5>" +
                                            "<p>" + ADB.getConnectedDevices() + "</p>" +
                                    "</html>");
                        } else {
                            debugBox.setText(
                                    "<html>" +
                                            "<h5 style=\"color: red\">Device not found.</h5>" +
                                            "<p>" + ADB.getConnectedDevices() + "</p>" +
                                    "</html>");
                        }
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

        vc.makeButton(debugPane, "Get Installed Apps", 115, 10, 100, 30)
                .addActionListener(event -> {
                    try {
                        debugBox.setText("<html><ul>" + String.join("<li>",
                                ADB.getInstalledApps()) + "</ul></html>");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

        vc.makeButton(debugPane, "Get App Directory", 230, 10, 100, 30)
                .addActionListener(event -> {
                    try {
                        debugBox.setText("<html>" + ADB.getAppDirectory(1).substring(8) + "</html>");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}