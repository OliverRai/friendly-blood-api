package com.friendlyblood.api.domain.models.baseModel;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BaseEntity {

    @Column()
    public LocalDateTime createAt;

    @Column()
    public LocalDateTime updateAt;

    @Column()
    public LocalDateTime deleteAt;
}
