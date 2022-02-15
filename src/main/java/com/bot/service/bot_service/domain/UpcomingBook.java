package com.bot.service.bot_service.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(name = "upcoming_books", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class UpcomingBook{

    public UpcomingBook(String title, String author, String publishDate) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "release_date")
    private String publishDate;
}
