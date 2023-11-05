package com.grud.lojavirtualfirebase.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


//METODOS

class ViewPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentList: MutableList<Fragment> = ArrayList()


    private val titleList: MutableList<String> = ArrayList()

    fun getTitle(position: Int): String {
        return titleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {// Ele retorna o número total de fragmentos no ViewPager, que é o tamanho da lista
        return fragmentList.size
    }


}