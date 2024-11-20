package borrowbook.domain;

import borrowbook.BorrowApplication;
import borrowbook.domain.BookBorrowed;
import borrowbook.domain.BookCanceled;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Borrow_table")
@Data
//<<< DDD / Aggregate Root
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Long bookId;

    private String borrowStatus;

    private Date borrowDate;

    private Long id;

    private Integer qty;

    @PostPersist
    public void onPostPersist() {
        BookBorrowed bookBorrowed = new BookBorrowed(this);
        bookBorrowed.publishAfterCommit();

        BookCanceled bookCanceled = new BookCanceled(this);
        bookCanceled.publishAfterCommit();
    }

    public static BorrowRepository repository() {
        BorrowRepository borrowRepository = BorrowApplication.applicationContext.getBean(
            BorrowRepository.class
        );
        return borrowRepository;
    }

    //<<< Clean Arch / Port Method
    public static void cancelBook(OutOfStock outOfStock) {
        //implement business logic here:

        /** Example 1:  new item 
        Borrow borrow = new Borrow();
        repository().save(borrow);

        BookCanceled bookCanceled = new BookCanceled(borrow);
        bookCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(outOfStock.get???()).ifPresent(borrow->{
            
            borrow // do something
            repository().save(borrow);

            BookCanceled bookCanceled = new BookCanceled(borrow);
            bookCanceled.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
