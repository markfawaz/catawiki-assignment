package com.catawiki.pages;

import com.catawiki.config.TestConfig;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public HomePage open() {
        page.navigate(TestConfig.baseUrl());
        return this;
    }

    public SearchResultsPage search(String keyword) {
        // input (pick first visible match to avoid strict-mode multi-match)
        Locator searchInput = page.getByTestId("search-field").first();
        searchInput.waitFor();
        searchInput.fill(keyword);

        // magnifier button (your inspect is correct: wrapper has data-testid=text-field-prefix)
        Locator magnifierButton = page.getByTestId("text-field-prefix")
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Search"))
                .first();

        magnifierButton.waitFor();
        magnifierButton.click();

        // simple, stable confirmation: URL changes to search-like url
        page.waitForURL(Pattern.compile(".*(search|q=|query).*"));

        return new SearchResultsPage(page);
    }


}
