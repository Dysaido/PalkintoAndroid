package com.bankirobot.palkinto.menu.ui.placeholder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bankirobot.palkinto.R
import com.bankirobot.palkinto.menu.ui.placeholder.ui.CouponsFragment
import com.bankirobot.palkinto.menu.ui.placeholder.ui.DustMapFragment
import com.bankirobot.palkinto.menu.ui.placeholder.ui.UserStatsFragment

private val TAB_TITLES = arrayOf(
    R.string.nav_title_dust_map,
    R.string.nav_title_user_stats,
    R.string.nav_title_coupons
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        lateinit var fragment : Fragment
        when (position) {
            0 -> fragment = DustMapFragment()
            1 -> fragment = UserStatsFragment()
            2 -> fragment = CouponsFragment()
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1)
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 3

}