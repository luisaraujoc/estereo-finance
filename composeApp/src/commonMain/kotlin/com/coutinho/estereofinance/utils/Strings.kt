package com.coutinho.estereofinance.utils

import androidx.compose.runtime.Composable
import estereofinancekmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

object Strings {
    @Composable
    fun get(id: StringRes, vararg args: Any): String {
        return when (id) {
            StringRes.APP_NAME -> stringResource(Res.string.app_name)
            // Bottom Navigation
            StringRes.BOTTOM_NAV_HOME -> stringResource(Res.string.bottom_nav_home)
            StringRes.BOTTOM_NAV_TRANSACTIONS -> stringResource(Res.string.bottom_nav_transactions)
            StringRes.BOTTOM_NAV_CATEGORIES -> stringResource(Res.string.bottom_nav_categories)
            StringRes.BOTTOM_NAV_REPORTS -> stringResource(Res.string.bottom_nav_reports)

            // Login Screen
            StringRes.LOGIN_TITLE -> stringResource(Res.string.login_title)
            StringRes.LOGIN_EMAIL_LABEL -> stringResource(Res.string.login_email_label)
            StringRes.LOGIN_PASSWORD_LABEL -> stringResource(Res.string.login_password_label)
            StringRes.LOGIN_BUTTON_TEXT -> stringResource(Res.string.login_button_text)
            StringRes.LOGIN_REGISTER_PROMPT -> stringResource(Res.string.login_register_prompt)
            StringRes.LOGIN_REGISTER_PROMPT_BUTTON -> stringResource(Res.string.login_register_prompt_button)

            // Register Screen
            StringRes.REGISTER_TITLE -> stringResource(Res.string.register_title)
            StringRes.REGISTER_EMAIL_LABEL -> stringResource(Res.string.register_email_label)
            StringRes.REGISTER_NAME_LABEL -> stringResource(Res.string.register_name_label)
            StringRes.REGISTER_CONFIRM_PASSWORD_LABEL -> stringResource(Res.string.register_confirm_password_label)
            StringRes.REGISTER_BUTTON_TEXT -> stringResource(Res.string.register_button_text)
            StringRes.REGISTER_LOGIN_PROMPT -> stringResource(Res.string.register_login_prompt)
            StringRes.REGISTER_LOGIN_PROMPT_BUTTON -> stringResource(Res.string.register_login_prompt_button)

            // Error Messages
            StringRes.ERROR_EMPTY_FIELD -> stringResource(Res.string.error_empty_field)
            StringRes.ERROR_INVALID_EMAIL -> stringResource(Res.string.error_invalid_email)
            StringRes.ERROR_PASSWORD_MISMATCH -> stringResource(Res.string.error_password_mismatch)
            StringRes.ERROR_WEAK_PASSWORD -> stringResource(Res.string.error_weak_password)

            // Success Messages
            StringRes.SUCCESS_REGISTRATION -> stringResource(Res.string.success_registration)
            StringRes.SUCCESS_LOGIN -> stringResource(Res.string.success_login)

            // Home Screen
            StringRes.HOME_TITLE -> stringResource(Res.string.home_title)
            StringRes.GENERAL_BALANCE_LABEL -> stringResource(Res.string.general_balance_label)
            StringRes.INCOME_LABEL -> stringResource(Res.string.income_label)
            StringRes.EXPENSE_LABEL -> stringResource(Res.string.expense_label)
            StringRes.HISTORY_LABEL -> stringResource(Res.string.history_label)
            StringRes.CURRENCY_FORMAT -> stringResource(Res.string.currency_format, *args)

            // Other Screens
            StringRes.TRANSACTIONS_TITLE -> stringResource(Res.string.transactions_title)
            StringRes.CATEGORIES_TITLE -> stringResource(Res.string.categories_title)
            StringRes.REPORTS_TITLE -> stringResource(Res.string.reports_title)
        }
    }
}

enum class StringRes {
    APP_NAME,
    // Bottom Navigation
    BOTTOM_NAV_HOME,
    BOTTOM_NAV_TRANSACTIONS,
    BOTTOM_NAV_CATEGORIES,
    BOTTOM_NAV_REPORTS,

    // Login Screen
    LOGIN_TITLE,
    LOGIN_EMAIL_LABEL,
    LOGIN_PASSWORD_LABEL,
    LOGIN_BUTTON_TEXT,
    LOGIN_REGISTER_PROMPT,
    LOGIN_REGISTER_PROMPT_BUTTON,

    // Register Screen
    REGISTER_TITLE,
    REGISTER_EMAIL_LABEL,
    REGISTER_NAME_LABEL,
    REGISTER_CONFIRM_PASSWORD_LABEL,
    REGISTER_BUTTON_TEXT,
    REGISTER_LOGIN_PROMPT,
    REGISTER_LOGIN_PROMPT_BUTTON,

    // Error Messages
    ERROR_EMPTY_FIELD,
    ERROR_INVALID_EMAIL,
    ERROR_PASSWORD_MISMATCH,
    ERROR_WEAK_PASSWORD,

    // Success Messages
    SUCCESS_REGISTRATION,
    SUCCESS_LOGIN,

    // Home Screen
    HOME_TITLE,
    GENERAL_BALANCE_LABEL,
    INCOME_LABEL,
    EXPENSE_LABEL,
    HISTORY_LABEL,
    CURRENCY_FORMAT,

    // Other Screens
    TRANSACTIONS_TITLE,
    CATEGORIES_TITLE,
    REPORTS_TITLE
}