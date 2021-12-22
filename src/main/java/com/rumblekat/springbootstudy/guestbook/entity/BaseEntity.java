package com.rumblekat.springbootstudy.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass/* 테이블로 생성되지 않는다. */
@EntityListeners(value = {AuditingEntityListener.class})/* JPA 내에서 엔티티 객체가 생성/변경되는 것을 감지하는 역할*/
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(name = "regdate", updatable = false)/*생성시간은 update 되지않는다.*/
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;
}
