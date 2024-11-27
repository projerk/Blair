package components.container;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;

import components.abstracts.FlexBox;
import components.interfaces.IContainer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Book;
import utils.FileHelper;
import utils.Constants;
import components.interfaces.Listener;
import controller.ExploreController;

public class BookFrame extends FlexBox {
    private int rowIndex = 0;
    private int columnIndex = 0;
    private static Listener listener;

    public BookFrame() {
        super();
    }

    private void initListener() {
        if (listener != null) {
            return;
        }
        try {
            FXMLLoader loader = FileHelper.getLoader(Constants.EXPLORE_VIEW_FILE);
            // ExploreController controller = loader.getController();
            // controller.setListener(controller);
            // Parent root = loader.load();
            setListener(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BookFrame(ArrayList<Book> books) {
        super();
        initListener();
        this.setBalanceColumnPercentage(books.size());
        this.setBalanceRowPercentage(1);
        this.getRowConstraints().get(0).setMinHeight(0);
        this.getRowConstraints().get(0).setMaxHeight(250);
        createBookItem(books);
        setHgap(10);
    }

    private void createBookItem(ArrayList<Book> books) {
        for (Book book : books) {
            BookItem bookItem = new BookItem(book);
            // bookItem.setListener(getListener());
            addBookItem(bookItem);
        }
    }

    private void addBookItem(BookItem bookItem) {
        this.addNode(bookItem, columnIndex, rowIndex);
        columnIndex++;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Listener getListener() {
        return listener;
    }

}
