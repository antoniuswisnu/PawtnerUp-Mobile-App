package com.example.pawtnerup.ui.setting

import android.os.Bundle
import android.preference.ListPreference
import android.preference.SwitchPreference
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.PreferenceFragmentCompat
import com.bumptech.glide.Glide.init
import com.example.pawtnerup.R

class SettingFragment : PreferenceFragmentCompat() {

    private lateinit var DARK_MODE: String
    private lateinit var NOTIFICATION: String

    private lateinit var themePreference: ListPreference
    private lateinit var notificationPreference: SwitchPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
//        init()

        /**
        notificationPreference.setOnPreferenceChangeListener { _, newValue ->
            val dailyReminder = DailyReminder()
            if (newValue == true) {
                dailyReminder.setDailyReminder(requireContext())
            } else if (newValue == false) {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }


        themePreference.setOnPreferenceChangeListener { _, newValue ->
            when (newValue.toString()) {
                "on" -> {
                    updateTheme(MODE_NIGHT_YES)
                }
                "off" -> {
                    updateTheme(MODE_NIGHT_NO)
                }
                "auto" -> {
                    updateTheme(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            true
        }
        **/
    }

    /**
    private fun init() {
        DARK_MODE = resources.getString(R.string.pref_key_dark)
        NOTIFICATION = resources.getString(R.string.pref_key_notify)

        themePreference = findPreference<ListPreference>(DARK_MODE) as ListPreference
        notificationPreference = findPreference<SwitchPreference>(NOTIFICATION) as SwitchPreference
    }

    private fun updateTheme(nightMode: Int): Boolean {
        setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
     **/
}