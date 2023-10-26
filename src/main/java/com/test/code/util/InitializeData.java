package com.test.code.util;

import com.test.code.entity.Genre;
import com.test.code.entity.Tag;
import com.test.code.repo.AuthorRepo;
import com.test.code.repo.GenreRepo;
import com.test.code.repo.TagRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InitializeData {

    private final TagRepo tagRepo;

    private final GenreRepo genreRepo;

    private final AuthorRepo authorRepo;

    public InitializeData(TagRepo tagRepo, GenreRepo genreRepo, AuthorRepo authorRepo) {
        this.tagRepo = tagRepo;
        this.genreRepo = genreRepo;
        this.authorRepo = authorRepo;
    }

    @Transactional
    @PostConstruct
    public void init() {

        List<String> genres = List.of(
                "Action",
                "Adventure",
                "Comedy",
                "Crime",
                "Drama",
                "Fantasy",
                "Historical",
                "Historical fiction",
                "Horror",
                "Magical realism",
                "Mystery",
                "Paranoid Fiction",
                "Philosophical",
                "Political",
                "Romance",
                "Saga",
                "Satire",
                "Science fiction",
                "Social",
                "Speculative",
                "Thriller",
                "Urban",
                "Western"
        );

        genres.forEach(genre -> genreRepo.save(new Genre(genre)));



        List<String> tags = List.of(
                "Action-Packed",
                "Intriguing Plot",
                "Classic",
                "Bestseller",
                "Romantic",
                "Suspenseful",
                "Award-Winning",
                "Science Fiction",
                "Fantasy World",
                "Historical Setting",
                "Mystery",
                "Adventure",
                "Epic Fantasy",
                "Dystopian",
                "Time Travel",
                "Humorous",
                "Heartwarming",
                "Coming of Age",
                "Thrilling",
                "Character-Driven",
                "Space Exploration",
                "Historical Fiction",
                "Page-Turner",
                "Mind-Blowing",
                "Family Drama",
                "Cinematic",
                "Horror",
                "Philosophical",
                "Political Intrigue",
                "Love Triangle",
                "Dark Fantasy",
                "Philosophical",
                "Post-Apocalyptic",
                "Paranormal",
                "Urban Fantasy",
                "Satirical",
                "Social Commentary",
                "Magical Realism",
                "Western",
                "Satire",
                "Cult Classic",
                "Unique World Building",
                "Suspense",
                "Allegorical",
                "Mind-Bending",
                "Whodunit",
                "Historical Romance",
                "Action Thriller"
        );

        tags.forEach(tag -> tagRepo.save(new Tag(tag)));

//        List<String> authors = List.of(
//                "J. K. Rowling",
//                "William Shakespeare",
//                "J. R. R. Tolkien",
//                "Agatha Christie",
//                "Dan Brown",
//                "Dr. Seuss",
//                "Stephen King",
//                "Charles Dickens",
//                "Ernest Hemingway",
//                "Mark Twain",
//                "George Orwell",
//                "Jane Austen",
//                "J. D. Salinger",
//                "Leo Tolstoy",
//                "Oscar Wilde",
//                "C. S. Lewis",
//                "F. Scott Fitzgerald",
//                "Lewis Carroll",
//                "Roald Dahl",
//                "Edgar Allan Poe",
//                "Kurt Vonnegut",
//                "Aldous Huxley",
//                "Charlotte Brontë",
//                "Emily Brontë",
//                "Herman Melville",
//                "Arthur Conan Doyle",
//                "Virginia Woolf",
//                "H. G. Wells",
//                "Jules Verne",
//                "Homer",
//                "Douglas Adams",
//                "George R. R. Martin",
//                "Harper Lee",
//                "Gabriel García Márquez",
//                "Victor Hugo",
//                "Anne Frank",
//                "Ayn Rand",
//                "John Steinbeck",
//                "Ray Bradbury",
//                "William Golding",
//                "Jack Kerouac",
//                "Johann Wolfgang von Goethe",
//                "James Joyce",
//                "H. P. Lovecraft"
//        );
//
//        authors.forEach(author -> {
//            authorRepo.save(new Author(author));
//        });
    }
}
