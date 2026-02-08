package com.catawiki.tests;

import com.catawiki.config.TestConfig;
import com.catawiki.core.BaseUiTest;
import com.catawiki.pages.HeaderNav;
import org.junit.jupiter.api.Test;

public class SignInNegativeTest extends BaseUiTest {

    @Test
    void signInRequiresPassword() {
        page.navigate(TestConfig.baseUrl());
        acceptCookiesAndPersistIfNeeded();
        new HeaderNav(page)
                .openSignInModal()
                .enterEmail("testsignin@example.com")
                .clickSignIn()
                .assertPasswordRequired();
    }

}