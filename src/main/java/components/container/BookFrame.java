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
import javafx.scene.layout.Pane;
import model.Book;
import utils.FileHelper;
import utils.Constants;
import components.interfaces.Listener;
import controller.ExploreController;

/**
 * A flexible container for displaying a collection of books in a grid-like layout.
 *
 * Extends FlexBox to provide a dynamic grid arrangement of BookItems.
 * Manages the creation and positioning of book items within a grid.
 */
public class BookFrame extends FlexBox {
    /** Current row index for adding book items. */
    private int rowIndex = 0;

    /** Current column index for adding book items. */
    private int columnIndex = 0;

    /** Shared listener for book-related events. */
    private static Listener listener;

    /**
     * Constructs an empty BookFrame.
     *
     * Creates a default FlexBox container without any initial book items.
     */
    public BookFrame() {
        super();
    }

    /**
     * Initializes the event listener for book interactions.
     *
     * Loads the explore view controller if the listener has not been set.
     * Uses FileHelper to load the FXML loader and extract the controller.
     * Suppresses multiple initializations of the listener.
     */
    private void initListener() {
        if (listener != null) {
            return;
        }
        try {
            FXMLLoader loader = FileHelper.getLoader(Constants.EXPLORE_VIEW_FILE);
            setListener(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a BookFrame with a collection of books.
     *
     * Initializes the frame with books, sets up column and row constraints,
     * and creates book items for each book in the provided list.
     *
     * @param books ArrayList of Book objects to be displayed in the frame
     */
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

    /**
     * Creates BookItem instances for each book in the provided list.
     *
     * Iterates through the book collection and generates a BookItem
     * for each book to be added to the frame.
     *
     * @param books ArrayList of Book objects to convert to BookItems
     */
    private void createBookItem(ArrayList<Book> books) {
        for (Book book : books) {
            BookItem bookItem = new BookItem(book);
            // bookItem.setListener(getListener());
            addBookItem(bookItem);
        }
    }

    /**
     * Adds a single BookItem to the frame at the current column and row.
     *
     * Places the BookItem in the grid and increments the column index
     * for the next item placement.
     *
     * @param bookItem BookItem to be added to the frame
     */
    private void addBookItem(BookItem bookItem) {
        this.addNode(bookItem, columnIndex, rowIndex);
        columnIndex++;
    }

    /**
     * Sets the event listener for book-related interactions.
     *
     * @param listener Listener to handle book-related events
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Retrieves the current event listener for book interactions.
     *
     * @return The current Listener instance
     */
    public Listener getListener() {
        return listener;
    }
}