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
import lombok.ToString;

@Entity
@Table(name = "INTERESTS")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Interest {

    @EmbeddedId
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private InterestId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
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
