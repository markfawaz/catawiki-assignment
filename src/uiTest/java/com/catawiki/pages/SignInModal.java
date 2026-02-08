package com.catawiki.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SignInModal {

    private final Page page;

    public SignInModal(Page page) {
        this.page = page;
    }

    private Locator dialog() {
        return page.getByRole(AriaRole.DIALOG);
    }

    public SignInModal assertVisible() {
        assertThat(dialog()).isVisible();
        return this;
    }

    public SignInModal assertEmailAndPasswordFieldsPresent() {
        assertThat(dialog().getByLabel("Email address")).isVisible();
        assertThat(dialog().getByLabel("Password")).isVisible();
        return this;
    }

    public CreateAccountModal clickCreateAccount() {
        dialog()
                .locator("button", new Locator.LocatorOptions()
                        .setHasText("Create account"))
                .first()
                .click();

        return new CreateAccountModal(page).assertVisible();
    }
}
