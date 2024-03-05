package mysqlpoint.realmysqlpoint.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.SuperBuilder;
import mysqlpoint.realmysqlpoint.enumerated.MemberRole;
import mysqlpoint.realmysqlpoint.enumerated.ProviderType;
import mysqlpoint.realmysqlpoint.util.MemberRoleConverter;
import mysqlpoint.realmysqlpoint.util.ProviderTypeConverter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {
    @Comment("회원 메일")
    @Column(unique = true, length = 50)
    private String email;

    @Comment("회원 가입 소셜 정보")
    @Column(length = 20)
    @Convert(converter = ProviderTypeConverter.class)
    private ProviderType provider;

    @Comment("회원 본명")
    @Column(length = 50)
    private String name;

    @Comment("회원 별명")
    @Column(length = 50)
    private String nickname;

    @Comment("회원 권한")
    @Column(length = 50)
    @Convert(converter = MemberRoleConverter.class)
    private MemberRole role;

    @Comment("회원 연락처")
    private Long phone;

    @Comment("성인인증 날짜")
    private LocalDate certifyAt;

    @Comment("이용 약관 동의")
    private Boolean agreedToServiceUse;

    @Comment("개인정보 수집 이용 안내 동의")
    private Boolean agreedToServicePolicy;

    @Comment("개인정보 활용 동의")
    private Boolean agreedToServicePolicyUse;
}
