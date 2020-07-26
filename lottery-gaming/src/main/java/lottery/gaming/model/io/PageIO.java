package lottery.gaming.model.io;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PageIO {

    private int pageIndex = 1;

    private int pageSize = 10;

    private int offset = 0;

}
