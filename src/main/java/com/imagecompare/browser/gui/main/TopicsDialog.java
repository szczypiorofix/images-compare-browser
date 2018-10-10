package com.imagecompare.browser.gui.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import java.awt.*;

public class TopicsDialog extends JDialog {

    public TopicsDialog(JFrame frame, String title, boolean modal) {
        super(frame, title, modal);
        createGUI(frame);
    }

    private void createGUI(JFrame frame) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setResizable(true);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());
        setUndecorated(false);

        JPanel mainPanel = new JPanel(new BorderLayout());


        DefaultListModel<String> topicsListModel = new DefaultListModel<>();

        HelpTopic[] helpTopics = new HelpTopic[5];

        helpTopics[0] = new HelpTopic(Topic.GENERAL_INFORMATION);
        helpTopics[1] = new HelpTopic(Topic.CREATE_NEW_DATABASE_FILE);
        helpTopics[2] = new HelpTopic(Topic.OPEN_DATABASE_FILE);
        helpTopics[3] = new HelpTopic(Topic.OPEN_RECENT_DATABASE_FILE);

        topicsListModel.addElement(helpTopics[0].getTopicModel().getTopicName());
        topicsListModel.addElement(helpTopics[1].getTopicModel().getTopicName());
        topicsListModel.addElement(helpTopics[2].getTopicModel().getTopicName());
        topicsListModel.addElement(helpTopics[3].getTopicModel().getTopicName());

        JList<String> topicsList = new JList<>(topicsListModel);
        topicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JLabel contentTopics = new JLabel(helpTopics[0].getTopicModel().getTopicContent());
        contentTopics.setBorder(new EmptyBorder(5, 5, 5, 5));

        topicsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                JList list = (JList) e.getSource();
                if (list.getSelectedIndex() >= 0 && list.getSelectedIndex() < helpTopics.length) {
                    contentTopics.setText(helpTopics[list.getSelectedIndex()].getTopicModel().getTopicContent());
                }
            }
        });
        topicsList.setSelectedIndex(0);

        JScrollPane topicsListScrollPane = new JScrollPane(topicsList);
        JScrollPane contentTopicsScrollPanel = new JScrollPane(contentTopics);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, topicsListScrollPane, contentTopicsScrollPanel);
        mainSplitPane.setContinuousLayout(true);
        mainSplitPane.setDividerSize(4);

        mainPanel.add(mainSplitPane, BorderLayout.CENTER);

        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(mainPanel, BorderLayout.CENTER);
    }
}
