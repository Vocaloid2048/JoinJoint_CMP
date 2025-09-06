/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.voc.joinjoint.ui.components.JoinJointButton
import com.voc.joinjoint.utils.FontSizeDefault
import com.voc.joinjoint.utils.pxToDp
import joinjoint.composeapp.generated.resources.Res
import joinjoint.composeapp.generated.resources.app_name
import joinjoint.composeapp.generated.resources.app_name_local
import joinjoint.composeapp.generated.resources.apple_logo
import joinjoint.composeapp.generated.resources.google_logo
import joinjoint.composeapp.generated.resources.icon_arrow_up
import joinjoint.composeapp.generated.resources.joinjoint_logo
import joinjoint.composeapp.generated.resources.line_icon
import joinjoint.composeapp.generated.resources.login_guest_mode_hint
import joinjoint.composeapp.generated.resources.login_line_account
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * A scrollable login page.
 * This page provides a user interface for users to log in to the application.
 * They can either decide to log in or continue as a guest.
 * Therefore, this "page" is assumed to be a part of the HomePage.
 *
 * @param navigator The navigation controller to handle navigation actions.
 */
@Composable
fun LoginPageScrollable(
    navigator: NavHostController,
){

    // LaunchedEffect ...

    // Root
    Column {

        // Area to contain JoinJoint Logo
        val logoAreaHeight = remember { mutableStateOf(100.dp) }
        Box (
            modifier = Modifier
                .weight(520/844f)
                .fillMaxSize()
                .onGloballyPositioned {
                    logoAreaHeight.value = pxToDp(it.size.height)
                }
        ){
            // JoinJoint Icon and Title
            Column (
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
                    .offset(0.dp, logoAreaHeight.value * 0.1f)
            ) {
                // JoinJoint Icon
                Image(
                    painter = painterResource(Res.drawable.joinjoint_logo),
                    contentDescription = "JoinJoint Logo",
                    modifier = Modifier
                )

                // JoinJoint Title
                if(stringResource(Res.string.app_name) != stringResource(Res.string.app_name_local)){
                    Text(
                        text = stringResource(Res.string.app_name_local),
                        modifier = Modifier.align(Alignment.End),
                        style = FontSizeDefault(),
                        color = Color.White,
                    )
                }
            }
        }

        // Login Buttons, Scroll to enable guest mode
        Box (
            modifier = Modifier
                .weight(324/844f)
                .fillMaxSize()
        ){
            // Login Buttons Area
            Column (
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                // Line Login
                JoinJointButton(
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    leftIcon = Res.drawable.line_icon,
                    textStr = Res.string.login_line_account
                )

                // Padding - 16dp height
                Spacer(modifier = Modifier.height(16.dp))

                // Apple & Google Login
                Row {
                    JoinJointButton(
                        modifier = Modifier.fillMaxWidth().weight(1f).height(50.dp),
                        leftIcon = Res.drawable.apple_logo,
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    JoinJointButton(
                        modifier = Modifier.fillMaxWidth().weight(1f).height(50.dp),
                        leftIcon = Res.drawable.google_logo,
                    )
                }
            }

            // Guest Mode Hint
            Column (modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)){
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(Res.string.login_guest_mode_hint),
                )

                Image(
                    painter = painterResource(Res.drawable.icon_arrow_up),
                    contentDescription = "Guest Mode Hint Arrow",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(24.dp)
                )
            }
        }
    }
}