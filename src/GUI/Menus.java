package GUI;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Created by Александр on 26.09.2014.
 */
public class Menus extends MenuBar {

    Menus() {
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save file");
        MenuItem open = new MenuItem("Open file");
        file.getItems().addAll(open, save);
        this.getMenus().addAll(file);
    }
}
