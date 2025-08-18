package org.example.ecommerce.config.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.example.ecommerce.config.UuidV7Generator;
import org.hibernate.annotations.IdGeneratorType;

/**
 * UUIDv7을 ID로 사용하는 필드를 위한 커스텀 어노테이션.
 * @Id, @GeneratedValue(generator="uuid-v7"), @Column 설정을 포함합니다.
 */
@IdGeneratorType(UuidV7Generator.class)
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 어노테이션 정보 유지
@Target(ElementType.FIELD)
public @interface GeneratedUuidV7 {
}
