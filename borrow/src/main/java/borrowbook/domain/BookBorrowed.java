package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookBorrowed extends AbstractEvent {

    private Long userId;
    private Long bookId;
    private Date borrowDate;
    private Long id;
    private Integer qty;
    private String borrowStatus;

    public BookBorrowed(Borrow aggregate) {
        super(aggregate);
    }

    public BookBorrowed() {
        super();
    }
}
//>>> DDD / Domain Event
