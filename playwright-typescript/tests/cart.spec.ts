import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';

test('add product to cart @TC12', async ({ page }) => {
    const homePage = new HomePage(page);
    await homePage.goto();
    const productsPage = await homePage.goToProductsPage();

    await productsPage.addFirstProductToCart();
    const cartPage = await productsPage.viewCartFromModal();

    expect(await cartPage.getCartItemCount()).toBeGreaterThan(0);
});