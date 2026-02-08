package com.catawiki.tests;

import com.catawiki.core.BaseUiTest;
import com.catawiki.pages.SignInModal;
import com.catawiki.pages.CreateAccountModal;
import com.catawiki.pages.HeaderNav;
import com.catawiki.pages.HomePage;
import org.junit.jupiter.api.Test;


public class CreateAccountModalTest extends BaseUiTest {

    @Test
    void createAccountFromSignInModal() {
        new HomePage(page).open();
        acceptCookiesIfPresent();

        SignInModal signIn =
                new HeaderNav(page).openSignInModal();

        CreateAccountModal createAccount =
                signIn.clickCreateAccount();

        createAccount.assertCreateAccountFieldsPresent();
    }

}
