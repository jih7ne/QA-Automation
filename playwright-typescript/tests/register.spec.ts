import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';

test('register user @TC1', async ({ page }) => {
    const uniqueEmail = `jihane.qa${Date.now()}@example.com`;

    const homePage = new HomePage(page);
    await homePage.goto();
    const signupPage = await homePage.goToSignupPage();

    const accountInfoPage = await signupPage.signup('Jihane QA', uniqueEmail);

    await accountInfoPage.fillAndSubmit(
        'TestPass123',
        'Jihane',
        'QA',
        '123 Test Street',
        'India',
        'Tetouan',
        'Tetouan',
        '93000',
        '0600000000'
    );

    expect(await accountInfoPage.isAccountCreated()).toBe(true);

    const afterSignup = await accountInfoPage.clickContinue();
    expect(await afterSignup.isLoggedInAsVisible()).toBe(true);

    await afterSignup.deleteAccountAndVerify();
});