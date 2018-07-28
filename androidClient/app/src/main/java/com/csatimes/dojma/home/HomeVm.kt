package com.csatimes.dojma.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * [AndroidViewModel] class for [com.csatimes.dojma.activities.MainActivity]
 *
 * @author Rushikesh Jogdand.
 */
class HomeVm(application: Application) : AndroidViewModel(application) {
    val currentSection: MutableLiveData<Section> = object : MutableLiveData<Section>() {}

    init {
        currentSection.value = Section.HERALD
    }
}

/**
 * Sections in the home screen. Refer [com.csatimes.dojma.activities.MainActivity]
 */
enum class Section {
    HERALD, ISSUES, FAVOURITES, EVENTS, UTILITIES
}
