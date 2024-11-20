package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class BookReturned extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long bookId;
    private Date returnDate;
    private Integer qty;
}
