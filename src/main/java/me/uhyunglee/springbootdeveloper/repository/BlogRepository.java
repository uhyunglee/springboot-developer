package me.uhyunglee.springbootdeveloper.repository;

import me.uhyunglee.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {// 엔티티 Article과 PK타입 Long을 인수로 넣음
}
