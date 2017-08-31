package com.begemot.kteacher

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*

/**
 * A placeholder fragment containing a simple view.
 */
class Main3ActivityFragment : Fragment() {
    private val log = AnkoLogger("MYPOS")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        log.warn("onCreateView Main3ActivityFragment")
        return inflater.inflate(R.layout.fragment_main3, container, false)
    }
}
