package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.User.*;

public class RegistrationFormTest {

    @BeforeEach
    void setUp() {
        activeUserRegistration();
        open("http://localhost:9999/");
        $("[data-test-id='login'] .input__control").setValue(getUsername());
        $("[data-test-id='password'] .input__control").setValue(getPassword());
    }

    @Test
    void shouldSucceedLogin() {
        $("[data-test-id='action-login'] .button__text").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotSucceedLoginByLogin() {
        $("[data-test-id='login'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE, getAnotherUsername());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification'] .notification__content").
                shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSucceedLoginByPassword() {
        $("[data-test-id='password'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE, getAnotherPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification'] .notification__content").
                shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSucceedLoginByBlocked() {
        blockedUserRegistration();
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification'] .notification__content").
                shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }
}
