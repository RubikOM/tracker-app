package com.rubinskyi.entity;

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

@Entity
@Table(name = "INTERESTS")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Interest {

    @EmbeddedId
    @EqualsAndHashCode.Exclude
    private InterestId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
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
}
