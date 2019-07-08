package com.rubinskyi.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "INTERESTS")
@Getter @Setter
@NoArgsConstructor
public class Interest {

    @EmbeddedId
    private InterestId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("dictionaryId")
    private Dictionary dictionary;

    @Column(name = "priority")
    private Integer priority;

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
        return priority.equals(interest.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority);
    }
}
