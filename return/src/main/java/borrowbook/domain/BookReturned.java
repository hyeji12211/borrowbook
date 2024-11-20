package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookReturned extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long bookId;
    private Date returnDate;

    public BookReturned(Return aggregate) {
        super(aggregate);
    }

    public BookReturned() {
        super();
    }
}
//>>> DDD / Domain Event
