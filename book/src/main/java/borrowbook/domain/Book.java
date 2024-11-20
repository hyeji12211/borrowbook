package borrowbook.domain;

import borrowbook.BookApplication;
import borrowbook.domain.BookDecreased;
import borrowbook.domain.BookIncreased;
import borrowbook.domain.OutOfStock;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Book_table")
@Data
//<<< DDD / Aggregate Root
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String author;

    private Integer bookCnt;

    @PostPersist
    public void onPostPersist() {
        BookDecreased bookDecreased = new BookDecreased(this);
        bookDecreased.publishAfterCommit();

        BookIncreased bookIncreased = new BookIncreased(this);
        bookIncreased.publishAfterCommit();

        OutOfStock outOfStock = new OutOfStock(this);
        outOfStock.publishAfterCommit();
    }

    public static BookRepository repository() {
        BookRepository bookRepository = BookApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    //<<< Clean Arch / Port Method
    public static void decreaseBook(BookBorrowed bookBorrowed) {
        repository().findById(bookBorrowed.getId()).ifPresent(book -> {
        if(book.getBookCnt() >= bookBorrowed.getQty()) {
            //BookDecreased bookDecreased = new BookDecreased(book);
            //bookDecreased.publishAfterCommit();
            book.setBookCnt(book.getBookCnt() - bookBorrowed.getQty());
            repository().save(book);
        }

        else {

            OutOfStock outOfStock = new OutOfStock(book);
            outOfStock.publishAfterCommit();
            System.out.println("Out of Stock 발생!");

        } 
        });
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        BookDecreased bookDecreased = new BookDecreased(book);
        bookDecreased.publishAfterCommit();
        OutOfStock outOfStock = new OutOfStock(book);
        outOfStock.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(bookBorrowed.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);

            BookDecreased bookDecreased = new BookDecreased(book);
            bookDecreased.publishAfterCommit();
            OutOfStock outOfStock = new OutOfStock(book);
            outOfStock.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseBook(BookReturned bookReturned) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        BookIncreased bookIncreased = new BookIncreased(book);
        bookIncreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(bookReturned.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);

            BookIncreased bookIncreased = new BookIncreased(book);
            bookIncreased.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
