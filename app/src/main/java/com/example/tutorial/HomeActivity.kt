package com.example.tutorial


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.example.tutorial.Adapter.BottomNavAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        configureBottomNavigation()
    }

    private fun configureBottomNavigation(){
        bottom_nav_bg.adapter = BottomNavAdapter(supportFragmentManager, 5)

        bottom_nav.setupWithViewPager(bottom_nav_bg)

        val bottomNav: View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)

        bottom_nav.getTabAt(0)!!.customView = bottomNav.findViewById(R.id.btn_my_page_nav) as RelativeLayout
        bottom_nav.getTabAt(1)!!.customView = bottomNav.findViewById(R.id.btn_star_nav) as RelativeLayout
        bottom_nav.getTabAt(2)!!.customView = bottomNav.findViewById(R.id.btn_home_nav) as RelativeLayout
        bottom_nav.getTabAt(3)!!.customView = bottomNav.findViewById(R.id.btn_chat_nav) as RelativeLayout
        bottom_nav.getTabAt(4)!!.customView = bottomNav.findViewById(R.id.btn_settings_nav) as RelativeLayout
    }
}