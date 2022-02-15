package com.bot.service.bot_service.dao;

import com.bot.service.bot_service.domain.UpcomingBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpcomingBookDAO extends CrudRepository<UpcomingBook, Long> {
    List<UpcomingBook> findByAuthorContainsIgnoreCase(String author);
    List<UpcomingBook> findByTitleContainsIgnoreCase(String title);
    List<UpcomingBook> findAll();
    void deleteAll();
}
