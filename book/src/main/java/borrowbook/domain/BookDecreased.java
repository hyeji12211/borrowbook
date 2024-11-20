package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookDecreased extends AbstractEvent {

    private Long id;
    private String title;
    private String author;
    private Integer bookCnt;

    public BookDecreased(Book aggregate) {
        super(aggregate);
    }

    public BookDecreased() {
        super();
    }
}
//>>> DDD / Domain Event
