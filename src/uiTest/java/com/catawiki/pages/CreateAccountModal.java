package com.catawiki.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CreateAccountModal {

    private final Page page;

    public CreateAccountModal(Page page) {
        this.page = page;
    }

    private Locator dialog() {
        return page.getByRole(AriaRole.DIALOG);
    }

    public CreateAccountModal assertVisible() {
        assertThat(dialog()).isVisible();
        return this;
    }

    public void assertCreateAccountFieldsPresent() {
        assertThat(dialog().getByLabel("First name")).isVisible();
        assertThat(dialog().getByLabel("Last name")).isVisible();
        assertThat(dialog().getByLabel("Email address")).isVisible();
        assertThat(dialog().getByLabel("Password")).isVisible();
    }
}
