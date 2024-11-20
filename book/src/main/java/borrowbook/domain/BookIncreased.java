package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookIncreased extends AbstractEvent {

    private Long id;
    private String title;
    private String author;
    private Integer bookCnt;

    public BookIncreased(Book aggregate) {
        super(aggregate);
    }

    public BookIncreased() {
        super();
    }
}
//>>> DDD / Domain Event
