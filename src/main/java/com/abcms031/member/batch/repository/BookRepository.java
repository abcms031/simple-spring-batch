package com.abcms031.member.batch.repository;

import com.abcms031.member.batch.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {


}
