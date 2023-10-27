package com.test.code.repo;

import com.test.code.entity.Book;
import com.test.code.vo.BookVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.name = :genre")
    List<BookVo> findByGenres(String genre);

    @Query("SELECT b FROM Book b JOIN b.tags t WHERE t.name = :tag")
    List<BookVo> findByTags(String tag);

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name = :author")
    List<BookVo> findByAuthors(String author);

    @Query("SELECT b FROM Book b WHERE (SELECT SUM(r.rating) FROM Rating r) >= :rating")
    List<BookVo> findByRating(int rating);
}
