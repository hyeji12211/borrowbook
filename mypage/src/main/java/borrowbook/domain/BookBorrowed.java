package borrowbook.domain;

import borrowbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class BookBorrowed extends AbstractEvent {

    private Long userId;
    private Long bookId;
    private Date borrowDate;
    private Long id;
    private Integer qty;
    private String borrowStatus;
}
