package com.rubinskyi.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter @Setter
@ToString @EqualsAndHashCode
class InterestId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "dictionary_id")
    private Long dictionaryId;

    private InterestId() {
    }

    InterestId(Long userId, Long dictionaryId) {
        this.userId = userId;
        this.dictionaryId = dictionaryId;
    }
}
