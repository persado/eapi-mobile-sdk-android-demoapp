package com.persado.sdkexample.network

import android.content.Context
import android.util.Log
import com.persado.enterprise.mobilesdk.PSDClient
import com.persado.enterprise.mobilesdk.PSDContent
import com.persado.enterprise.mobilesdk.PSDTrack
import com.persado.enterprise.mobilesdk.dto.TouchpointVariantResponse
import com.persado.enterprise.mobilesdk.dto.TrackAction
import com.persado.sdkexample.R
import com.persado.sdkexample.domain.model.CartContent
import com.persado.sdkexample.domain.model.User
import com.persado.sdkexample.util.APP_ID
import com.persado.sdkexample.util.CAMPAIGN_IDENTIFIER
import com.persado.sdkexample.util.TOUCHPOINT_NAME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class PersadoManager @Inject constructor(
    context: Context,
    private val json: Json
) {

    private lateinit var userAttributes: Map<String, String>

    private val _cartContent = MutableStateFlow(
        CartContent(
            headline = context.getString(R.string.title_shopping_cart),
            total = context.getString(R.string.txt_total),
            cta = context.getString(R.string.button_cta)
        )
    )
    val cartContent = _cartContent.asStateFlow()

    fun initProfile(user: User) {
        userAttributes = userToMap(user)

        PSDClient.Builder(APP_ID)
            .userAttributes(userAttributes)
            .build()
            .initialize { success, errorMessage ->
                if (success) {
                    Log.i(TAG, "Persado EAPI SDK initialized successfully")

                    getTouchpointContent()
                } else {
                    errorMessage?.let { println(it) }
                }
            }
    }

    private fun getTouchpointContent() {
        val tp: TouchpointVariantResponse? =
            PSDContent().getTouchpointContentByLabel(CAMPAIGN_IDENTIFIER, TOUCHPOINT_NAME)

        tp?.content?.let {
            Log.d(TAG, tp.content.toString())
            try {
                _cartContent.value = json.decodeFromString(it)
            } catch (e: Exception) {
                Log.e(TAG, "Error decoding cart content", e)
            }
        }
    }

    fun trackView() {
        trackPersado(TrackAction.VIEW)
    }

    fun trackClick() {
        trackPersado(TrackAction.CLICK)
    }

    fun trackConvert() {
        trackPersado(TrackAction.CONVERT)
    }

    private fun trackPersado(action: TrackAction) {
        PSDTrack().userAttributes(userAttributes)
            .trackByCampaignLabel(action, CAMPAIGN_IDENTIFIER) { success, errorMessage ->
                if (success) {
                    Log.i(TAG, "Persado EAPI SDK track successfully")
                    Log.v(TAG, "Persado: action: $action, userAttributes: $userAttributes")
                } else {
                    errorMessage?.let { Log.e(TAG, it) }
                }
            }
    }

    private fun userToMap(user: User): Map<String, String> =
        hashMapOf(
            "location" to user.city,
            "country" to user.country,
            "gender" to user.gender,
            "marital_status" to user.maritalStatus,
            "loyalty_status" to user.loyaltyStatus,
            "account_status" to user.accountStatus,
            "currency" to user.currency,
            "account_id" to user.accountId,
            "device_type" to user.deviceType,
            "browser" to user.browser,
        )

    companion object {
        private const val TAG = "PersadoManager"
    }
}