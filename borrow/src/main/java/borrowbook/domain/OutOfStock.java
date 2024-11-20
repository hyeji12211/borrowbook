package borrowbook.domain;

import borrowbook.domain.*;
import borrowbook.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OutOfStock extends AbstractEvent {

    private Long id;
    private String title;
    private String author;
    private Integer bookCnt;
}
