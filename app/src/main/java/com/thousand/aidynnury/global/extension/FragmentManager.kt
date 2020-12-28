package com.thousand.aidynnury.global.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


internal fun FragmentManager.removeFragment(tag: String) {
    this.findFragmentByTag(tag)?.let {
        this.beginTransaction()
                .disallowAddToBackStack()
                .remove(it)
                .commitNow()
    }
}

internal fun FragmentManager.addFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .add(containerViewId, fragment, tag)
            .commit()
}


internal fun FragmentManager.replaceFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .replace(containerViewId, fragment, tag)
        .commitAllowingStateLoss()
}

internal fun FragmentManager.replaceFragmentWithBackStack(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction()
            .replace(containerViewId, fragment, tag)
            .addToBackStack(tag)
            .commit()
}

internal fun FragmentManager.addFragmentWithBackStack(containerViewId: Int,
                                                          fragment: Fragment,
                                                          tag: String) {
    this.beginTransaction()
            .add(containerViewId, fragment, tag)
            .addToBackStack(tag)
            .commit()
}
