package com.bot.service.bot_service.service;

import com.bot.service.bot_service.dao.UpcomingBookDAO;
import com.bot.service.bot_service.domain.UpcomingBook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UpcomingBookService {

    @Autowired
    UpcomingBookDAO upcomingBookDAO;

    public void getNewBooks() throws IOException {
        String bookTitle = null;
        String bookAuthor = null;
        String bookReleaseDate = null;

        for (int i = 1 ; i <= 34 ; i++ ) {
            Document doc = Jsoup.connect("https://lubimyczytac.pl/ksiazki/zapowiedzi?page=" + i + "&listId=booksFilteredList&isAnnouncement=1&showFirstLetter=0&paginatorType=Standard&catalogSortBy=published-desc&paginatorType=Standard").get();
            String docString = doc.toString();
            List<String> allMatches = new ArrayList<>();
            Matcher matcher = Pattern.compile("listBookElement\\d+")
                    .matcher(docString);

            while (matcher.find()) {
                allMatches.add(matcher.group());
            }

            for (String match : allMatches) {
                Element element = doc.getElementById(match);
                Elements authors = element.getElementsByClass("authorAllBooks__singleTextAuthor authorAllBooks__singleTextAuthor--bottomMore");
                Elements titles = element.getElementsByClass("authorAllBooks__singleTextTitle float-left");

                for (Element title : titles) {
                    bookTitle = title.text();
                    String url = title.attr("abs:href");
                    Document document = Jsoup.connect(url).get();
                    Element el = document.getElementById("book-details");
                    Elements dds = el.select("dd");

                    for (Element dd : dds) {
                        Matcher matcherDate = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
                                .matcher(dd.text().trim());
                        if (matcherDate.find()) {
                            bookReleaseDate = dd.text();
                            break;
                        }
                    }
                }
                for (Element author : authors)
                    bookAuthor = author.text();

                upcomingBookDAO.save(UpcomingBook.builder()
                        .author(bookAuthor)
                        .title(bookTitle)
                        .publishDate(bookReleaseDate)
                        .build());
            }
        }
    }

    public void deleteAll() {
        upcomingBookDAO.deleteAll();
    }

    public List<UpcomingBook> getAll() {
        return upcomingBookDAO.findAll();
    }

    public List<UpcomingBook> findByAuthor(String author) {
        return upcomingBookDAO.findByAuthorContainsIgnoreCase(author);
    }

    public List<UpcomingBook> findByTitle(String title) {
        return upcomingBookDAO.findByTitleContainsIgnoreCase(title);
    }
}
