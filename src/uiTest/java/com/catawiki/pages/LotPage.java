package com.catawiki.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import java.util.regex.Pattern;

public class LotPage {
    private final Page page;

    public LotPage(Page page) {
        this.page = page;
    }

    public LotPage assertOpened() {
        PlaywrightAssertions.assertThat(page.locator("h1")).isVisible();
        return this;
    }

    public LotSummary readSummary() {
        // Lot title (semantic + stable)
        String title = page.locator("h1").first().innerText().trim();


        Locator favBtn = page.locator("button[title='favourite'][count]").first();
        String favorites = favBtn.getAttribute("count");

        // Current bid: stable container + stable "bid-amount" class prefix
        Locator bidSection = page.getByTestId("lot-bid-status-section");
        String currentBid = bidSection
                .locator("[class^='LotBidStatusSection_bid-amount']")
                .first()
                .innerText()
                .trim(); // "€ 11"

        return new LotSummary(title, favorites, currentBid);
    }

}
