package borrowbook.domain;

import borrowbook.ReturnApplication;
import borrowbook.domain.BookOverdued;
import borrowbook.domain.BookReturned;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Return_table")
@Data
//<<< DDD / Aggregate Root
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long bookId;

    private Date returnDate;

    private Integer qty;

    @PostPersist
    public void onPostPersist() {
        BookReturned bookReturned = new BookReturned(this);
        bookReturned.publishAfterCommit();

        BookOverdued bookOverdued = new BookOverdued(this);
        bookOverdued.publishAfterCommit();
    }

    public static ReturnRepository repository() {
        ReturnRepository returnRepository = ReturnApplication.applicationContext.getBean(
            ReturnRepository.class
        );
        return returnRepository;
    }
}
//>>> DDD / Aggregate Root
