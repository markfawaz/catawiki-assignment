package com.catawiki.tests;

import com.catawiki.core.BaseUiTest;
import com.catawiki.pages.SignInModal;
import com.catawiki.pages.HeaderNav;
import com.catawiki.pages.HomePage;
import org.junit.jupiter.api.Test;

public class SignInModalTest extends BaseUiTest {

    @Test
    void signInModalOpens() {
        new HomePage(page).open();
        acceptCookiesAndPersistIfNeeded();
        SignInModal modal = new HeaderNav(page).openSignInModal();
        modal.assertEmailAndPasswordFieldsPresent();
    }
}
