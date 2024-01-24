package com.persado.sdkexample.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.persado.sdkexample.data.repository.FakeDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    dataRepository: FakeDataRepository
) : ViewModel() {

    val users = dataRepository.getUsers()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}