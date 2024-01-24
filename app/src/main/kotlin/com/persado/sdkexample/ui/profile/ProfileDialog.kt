package com.persado.sdkexample.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.persado.sdkexample.R
import com.persado.sdkexample.domain.model.User
import com.persado.sdkexample.ui.theme.DividerLightGray

@Composable
fun ProfileDialog(
    selected: User,
    onSelectProfile: (User) -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val users = viewModel.users.collectAsStateWithLifecycle()
    var selectedProfile: User by remember { mutableStateOf(selected) }

    Dialog(
        onDismissRequest = { onSelectProfile(selectedProfile) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    text = stringResource(R.string.title_select_profile),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(
                        top = 32.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    ProfileDropdown(
                        modifier = Modifier
                            .fillMaxWidth(),
                        placeholder = stringResource(R.string.title_select_profile),
                        textStyle = MaterialTheme.typography.titleSmall,
                        items = users.value.map { it.name },
                        selected = selectedProfile.name,
                        onItemSelected = {
                            selectedProfile = users.value.first { user -> user.name == it }
                        }
                    )

                    ProfileDetails(
                        modifier = Modifier
                            .fillMaxWidth(),
                        selectedProfile
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RectangleShape,
                    enabled = selectedProfile.name.isNotEmpty(),
                    onClick = {
                        onSelectProfile(selectedProfile)
                    })
                {
                    Text(
                        text = stringResource(R.string.button_done),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileDetails(modifier: Modifier = Modifier, user: User) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        val headerModifier = Modifier.padding(top = 8.dp)
        val rowModifier = Modifier.padding(top = 8.dp)
        val dividerModifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(color = DividerLightGray)

        Header(
            modifier = headerModifier,
            text = stringResource(id = R.string.profile_category_personal)
        )
        Divider(modifier = dividerModifier)

        DetailRow(rowModifier, label = stringResource(R.string.profile_city), text = user.city)
        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_country),
            text = user.country
        )
        DetailRow(rowModifier, label = stringResource(R.string.profile_gender), text = user.gender)
        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_marital_status),
            text = user.maritalStatus
        )
        Header(
            modifier = headerModifier,
            text = stringResource(id = R.string.profile_category_customer_segment)
        )
        Divider(modifier = dividerModifier)

        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_loyalty_status),
            text = user.loyaltyStatus
        )
        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_account_status),
            text = user.accountStatus
        )
        Header(
            modifier = headerModifier,
            text = stringResource(id = R.string.profile_category_account)
        )
        Divider(modifier = dividerModifier)

        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_currency),
            text = user.currency
        )
        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_account_id),
            text = user.accountId
        )
        Header(
            modifier = headerModifier,
            text = stringResource(id = R.string.profile_category_device)
        )
        Divider(modifier = dividerModifier)

        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_device_type),
            text = user.deviceType
        )
        DetailRow(
            rowModifier,
            label = stringResource(R.string.profile_browser),
            text = user.browser
        )
    }
}

@Composable
fun Header(modifier: Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
    )
}

@Composable
fun DetailRow(modifier: Modifier = Modifier, label: String, text: String) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}