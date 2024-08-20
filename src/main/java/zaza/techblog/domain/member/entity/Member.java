package zaza.techblog.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import zaza.techblog.global.common.entity.BaseEntity;

@Getter
@Entity
@Table(name = "t_cmn_member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private long number;
    private String id;
    private String password;
    private String role;
    private String status;

    public Member() {}

    public Member(long number, String id, String password, String role, String status) {
        this.number = number;
        this.id = id;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
