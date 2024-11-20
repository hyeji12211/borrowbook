package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class BookBorrowed extends AbstractEvent {

    private Long userId;
    private Long bookId;
    private Date borrowDate;
    private Long id;
    private Integer qty;
    private String borrowStatus;
}
