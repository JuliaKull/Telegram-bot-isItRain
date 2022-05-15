package ee.kull.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Stickers {

    @Value("${stikers.tom}")
    private String tom;

    @Value("${stikers.askTom}")
    private String askTom;

}
