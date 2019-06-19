package com.rubinskyi.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "INTERESTS")
public class Interest {

    @EmbeddedId
    private InterestId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dictionaryId")
    private Dictionary dictionary;

    @Column(name = "priority")
    private Integer priority;

    private Interest() {
    }

    public Interest(User user, Dictionary dictionary, Integer priority) {
        this.user = user;
        this.dictionary = dictionary;
        this.priority = priority;
        this.id = new InterestId(user.getId(), dictionary.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interest interest = (Interest) o;
        return user.equals(interest.user) &&
                dictionary.equals(interest.dictionary) &&
                priority.equals(interest.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, dictionary, priority);
    }

    public InterestId getId() {
        return id;
    }

    public void setId(InterestId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
