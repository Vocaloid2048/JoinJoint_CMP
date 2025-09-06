/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.voc.joinjoint.utils.FontSizeDefault
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun JoinJointButton(
    modifier: Modifier = Modifier,
    text : String? = null,
    textStr : StringResource? = null,
    leftIcon : DrawableResource?= null,
    rightIcon : DrawableResource? = null,
    backgroundColor: Color = Color.Black,
    iconColor : Color = Color.White,
    textColor: Color = Color.White,
) {
    Row (modifier = modifier
        .background(backgroundColor, shape = RoundedCornerShape(25.dp))
        .padding(16.dp)
    ){
        // Icon on the left
        if(leftIcon != null){
            Image(
                painter = painterResource(leftIcon),
                contentDescription = "Left Icon",
                colorFilter = ColorFilter.tint(iconColor)
            )
        }

        if(text != null || textStr != null){
            Text(
                text = text ?: stringResource(textStr!!),
                color = textColor,
                style = FontSizeDefault()
            )
        }

        // Icon on the right
        if (rightIcon != null){
            Image(
                painter = painterResource(rightIcon),
                contentDescription = "Right Icon",
                colorFilter = ColorFilter.tint(iconColor)
            )
        }
    }
}