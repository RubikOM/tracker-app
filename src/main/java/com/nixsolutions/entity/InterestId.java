package com.nixsolutions.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InterestId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "dictionary_id")
    private Long dictionaryId;

    private InterestId() {
    }

    public InterestId(Long userId, Long dictionaryId) {
        this.userId = userId;
        this.dictionaryId = dictionaryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestId that = (InterestId) o;
        return userId.equals(that.userId) &&
                dictionaryId.equals(that.dictionaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, dictionaryId);
    }

    @Override
    public String toString() {
        return "InterestId{" +
                "userId=" + userId +
                ", dictionaryId=" + dictionaryId +
                '}';
    }
}
