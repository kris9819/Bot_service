package com.bot.service.bot_service.controller;

import com.bot.service.bot_service.domain.UpcomingBook;
import com.bot.service.bot_service.service.UpcomingBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class UpcomingBookController {

    @Autowired
    UpcomingBookService upcomingBookService;

    @GetMapping("/getAllUpcomingBooks")
    public List<UpcomingBook> getAllBooks() {
        return upcomingBookService.getAll();
    }

    @GetMapping("/deleteAll")
    public void delete() {
        upcomingBookService.deleteAll();
    }

    @GetMapping("/getNewBooks")
    public void getNewBooks() throws IOException {
        upcomingBookService.getNewBooks();
    }

    @GetMapping("/getBooksByAuthor")
    public List<UpcomingBook> getByAuthor(@RequestParam("author") String author) {
        return upcomingBookService.findByAuthor(author);
    }

    @GetMapping("/getBooksByTitle")
    public List<UpcomingBook> getByTitle(@RequestParam("title") String title) {
        return upcomingBookService.findByTitle(title);
    }

}
