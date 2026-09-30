import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';

test('place order - login before checkout @TC16', async ({ page }) => {
    const homePage = new HomePage(page);
    await homePage.goto();
    const loginPage = await homePage.goToLoginPage();

    await loginPage.login('jih7ne@gmail.com', 'YourCorrectPassword123');

    const loggedInHome = new HomePage(page);
    const productsPage = await loggedInHome.goToProductsPage();

    await productsPage.addFirstProductToCart();
    const cartPage = await productsPage.viewCartFromModal();

    const checkoutPage = await cartPage.proceedToCheckout();

    expect(await checkoutPage.isReviewOrderVisible()).toBe(true);

    await checkoutPage.addOrderComment('Automated test order - please ignore.');
    await checkoutPage.clickPlaceOrder();
});