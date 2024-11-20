package borrowbook.infra;

import borrowbook.config.kafka.KafkaProcessor;
import borrowbook.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MypageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookBorrowed_then_CREATE_1(
        @Payload BookBorrowed bookBorrowed
    ) {
        try {
            if (!bookBorrowed.validate()) return;

            // view 객체 생성
            Mypage mypage = new Mypage();
            // view 객체에 이벤트의 Value 를 set 함
            mypage.setUserId(bookBorrowed.getUserId());
            mypage.setBorrowCnt(bookBorrowed.getQty());
            mypage.setBorrowStatus(bookBorrowed.getBorrowStatus());
            // view 레파지 토리에 save
            mypageRepository.save(mypage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
