package org.appxi.cbeta.explorer.workbench;

import javafx.application.Platform;
import javafx.event.Event;
import org.appxi.cbeta.explorer.book.BookListController;
import org.appxi.cbeta.explorer.event.BookEvent;
import org.appxi.cbeta.explorer.event.ChapterEvent;
import org.appxi.cbeta.explorer.home.AboutController;
import org.appxi.cbeta.explorer.prefs.PreferencesController;
import org.appxi.cbeta.explorer.book.BookViewController;
import org.appxi.cbeta.explorer.recent.RecentController;
import org.appxi.cbeta.explorer.tool.EpubRenameController;
import org.appxi.javafx.workbench.WorkbenchController;
import org.appxi.javafx.workbench.views.WorkbenchViewpartController;
import org.appxi.tome.cbeta.CbetaBook;
import org.appxi.tome.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class WorkbenchRootController extends WorkbenchController {

    public WorkbenchRootController() {
        super("ROOT-WORKBENCH", "Workbench");
    }

    @Override
    public void setupInitialize() {
        getEventBus().addEventHandler(BookEvent.OPEN, event -> handleOpenBookOrChapter(event, event.book, null));
        getEventBus().addEventHandler(ChapterEvent.OPEN, event -> handleOpenBookOrChapter(event, event.book, event.chapter));

        super.setupInitialize();

        getApplication().updateStartingProgress();
        createAboutLinkAtStatusBar();
    }

    private void handleOpenBookOrChapter(Event event, CbetaBook book, Chapter chapter) {
        final BookViewController openpart = (BookViewController) getViewport().findOpenpart(book.id);
        if (null != openpart) {
            getViewport().selectOpenpart(openpart.viewId);
            event.consume();
            Platform.runLater(() -> openpart.openChapter(null, chapter));
            return;
        }
        Platform.runLater(() -> {
            final BookViewController controller = new BookViewController(book, chapter);
            addWorkbenchViewpartController(controller);
            controller.setupInitialize();
            getViewport().selectOpenpart(controller.viewId);
        });
    }

    @Override
    protected List<WorkbenchViewpartController> createViewpartControllers() {
        final List<WorkbenchViewpartController> result = new ArrayList<>();
        result.add(new BookListController());
        result.add(new RecentController());
        result.add(new EpubRenameController());
//        result.add(new BookmarksController());
//        result.add(new BooknotesController());
        result.add(new PreferencesController());
        result.add(new AboutController());
        return result;
    }

    private void createAboutLinkAtStatusBar() {
//        final Hyperlink link = new Hyperlink(AppInfo.NAME + " " + AppInfo.VERSION);
//        link.setVisited(true);
//        link.setStyle("-fx-underline: false;");
//        getViewport().infotools.addRight(link);
    }
}
