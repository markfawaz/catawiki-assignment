package com.catawiki.tests;

import com.catawiki.core.BaseUiTest;
import com.catawiki.pages.*;
import org.junit.jupiter.api.Test;

/**
 * Covers the required assignment flow as described in the instructions.
 */
public class CatawikiAssignmentTest extends BaseUiTest {

    @Test
    void searchSecondTrainAndPrintDetails() {
        HomePage home = new HomePage(page).open();
        acceptCookiesAndPersistIfNeeded();
        SearchResultsPage results = home.search("train").assertOpened();
        LotPage lotPage = results.openSecondLot().assertOpened();
        LotSummary summary = lotPage.readSummary();

        System.out.println("LOT SUMMARY => " + summary);
    }
}
