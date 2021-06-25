package org.jis.java;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomeUI extends JFrame {

        private JPanel welcomeUIPanel;
        private JPanel contentPanel;
        private Controller controller;

        public WelcomeUI(String title) {
            super(title);
            this.setLocation(300, 300);
        }

        public static void main(String[] args) {
            WelcomeUI backgroundWindow = new WelcomeUI("BetterMarker");
            backgroundWindow.setVisible(true);
            backgroundWindow.setupContent();
        }

        /**
         * This method sets up the default frame
         */
        private void setupContent() {
            this.setTitle("iGen");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //TODO
            this.setBounds(1, 1, 856, 482);
            this.setLocationRelativeTo(null);
            this.contentPanel = new JPanel();
            this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.contentPanel.setLayout(new GridLayout(1, 1));
            this.setContentPane(this.contentPanel);

            this.generateConent();

            this.setVisible(true);

        }

        /**
         * This method creates the top panel and adds it to the frame
         */
        private void generateConent() {
            this.welcomeUIPanel = new WelcomePanel();
            this.contentPanel.add(this.welcomeUIPanel);
        }
    }

