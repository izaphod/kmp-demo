@file:OptIn(ExperimentalMaterial3Api::class)

package ru.sequenia.test.ui.compose.component.message

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.compose.theme.MessageStyle

@Composable
fun ErrorMessageComponent(
    message: String,
    modifier: Modifier = Modifier,
    messageStyle: MessageStyle = AppTheme.messageSystem.error,
    action: String? = null,
    onActionClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(messageStyle.minHeight)
            .background(messageStyle.containerColor, messageStyle.shape)
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = message,
            style = messageStyle.contentStyle,
            modifier = Modifier.weight(weight = 1F)
        )

        if (action != null) {
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                Text(
                    text = action.uppercase(),
                    style = messageStyle.actionStyle,
                    modifier = Modifier.clickable { onActionClick() }
                )
            }
        }
    }
}