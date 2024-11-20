package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookOverdued extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long bookId;
    private Date returnDate;
    private Integer qty;

    public BookOverdued(Return aggregate) {
        super(aggregate);
    }

    public BookOverdued() {
        super();
    }
}
//>>> DDD / Domain Event
