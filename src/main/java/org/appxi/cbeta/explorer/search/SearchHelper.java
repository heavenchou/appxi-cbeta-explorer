package org.appxi.cbeta.explorer.search;

import org.appxi.javafx.workbench.WorkbenchController;
import org.appxi.tome.cbeta.CbetaBook;

import java.util.function.Function;

public abstract class SearchHelper {
    private SearchHelper() {
    }

    public static Function<String, CbetaBook> searchById;

    public static CbetaBook searchById(String id) {
        return null == searchById ? null : searchById.apply(id);
    }

    private static SearchService searchService;

    public static void setupSearchService(WorkbenchController controller) {
        if (null != searchService)
            return;
        searchService = new SearchService(controller);
        searchService.setupInitialize();
    }
}
