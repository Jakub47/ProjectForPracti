# ProjectForPracti
Solution to task given by Practi

In order to run application  first please import create database book_store and import sql file (book_store.sql)
after that you can run the jar file to do that please move to target folder and then run jar file with command:
java -jar practi-project-0.0.1-SNAPSHOT.jar

There are four endoints default port wasn't changed so in url please write following:
-http://localhost:8080/books/8    (GET) - to get book with provided id
-http://localhost:8080/books      (GET) - to get first page with 50 book
-http://localhost:8080/books?page=2      (GET) - to get second page with 50 book
-http://localhost:8080/books        (POST) - to add new book to database example body 
                                            {
                                              "title": "book3",
                                              "author": "author3",
                                              "isbnNumber" : "1234567890123",
                                              "pagesAmount" : 442,
                                              "rating" : 3.5
                                            }
http://localhost:8080/books/15    (PUT) - to update book in database with id of 15  example body
                                            {
                                              "title": "book3_3",
                                              "author": "author3_3",
                                              "isbnNumber" : "1712361315024",
                                              "pagesAmount" : 445,
                                              "rating" : 3.5
                                            }
http://localhost:8080/books/15    (DELETE) - to delete book in database with id of 15

With almost every request (except delete) you will get additional links with what can you do next with given object (so called hateoas)
