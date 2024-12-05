package components.container;

import components.abstracts.FlexBox;
import model.Book;

public class BookCanvas extends FlexBox {

    public BookCanvas(Book book) {
        super();
        this.setColumnPercentages(70, 30);
        this.setRowPercentages(100);
    }

    
}
