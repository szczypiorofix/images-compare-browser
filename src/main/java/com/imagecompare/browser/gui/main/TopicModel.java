package com.imagecompare.browser.gui.main;

public class TopicModel {

    private Topic topic;
    private String topicName, topicContent;

    public TopicModel(Topic topic, String topicName, String topicContent) {
        this.topic = topic;
        this.topicName = topicName;
        this.topicContent = topicContent;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicContent() {
        return topicContent;
    }
}
