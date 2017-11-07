package com.imagecompare.browser.gui.main;

public class HelpTopic {

    private TopicModel topicModel;

    public HelpTopic(Topic topic) {
        switch (topic) {
            case CREATE_NEW_DATABASE_FILE: {
                topicModel = new TopicModel(topic, "Tworzenie pliku bazy", "<html><h2>Tworzenie pliku bazy</h2>Informacje o tworzeniu nowego plkku bazy.</html>");
                break;
            }
            case OPEN_DATABASE_FILE: {
                topicModel = new TopicModel(topic, "Otwieranie pliku bazy", "<html><h2>Otwieranie pliku bazy</h2> Informacje o otwieraniu pliu bazy.</html>");
                break;
            }
            case OPEN_RECENT_DATABASE_FILE: {
                topicModel = new TopicModel(topic, "Otwieranie ostatniego pliku bazy", "<html><h2>Otwieranie ostatniego pliku bazy</h2> Informacje o otwieraniu ostatnio używanego pliu bazy.</html>");
                break;
            }
            default: {
                topicModel = new TopicModel(topic, "Informacje podstawowe", "<html><h2>Informacje podstawowe</h2><br>To są informacje podstawoowe.</html>");
                break;
            }
        }
    }


    public TopicModel getTopicModel() {
        return topicModel;
    }
}
