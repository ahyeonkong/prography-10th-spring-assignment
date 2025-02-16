package assignment.game.PingPong.global.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * h2의 테이블명을 Room과 같이 대소문자를 혼합한 형태로 나타내기 위해,
 * Hibernate의 네이밍 전략을 커스터마이징하기 위한 클래스
 */

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return name; // 카탈로그 이름 그대로 사용
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return name; // 스키마 이름 그대로 사용
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return name; // 엔티티 클래스 이름 그대로 테이블 이름으로 사용
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return name; // 시퀀스 이름 그대로 사용
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return name; // 컬럼 이름 그대로 사용
    }
}
