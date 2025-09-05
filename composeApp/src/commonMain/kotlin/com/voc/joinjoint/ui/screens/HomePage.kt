/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomePage(
    navigator: NavHostController,
){
    // Initialize ...

    // DoItLater: Show the Login Page, if not logged in
    LoginPageScrollable(navigator = navigator)

    // DoItLater: Show the main content, if logged in
}