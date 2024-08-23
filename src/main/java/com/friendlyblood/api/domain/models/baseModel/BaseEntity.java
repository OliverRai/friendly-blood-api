package com.friendlyblood.api.domain.models.baseModel;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BaseEntity {

    @Column(name = "created_at")
    public LocalDateTime createAt;

    @Column(name = "updated_at")
    public LocalDateTime updateAt;

    @Column(name = "deleted_at")
    public LocalDateTime deleteAt;
}
