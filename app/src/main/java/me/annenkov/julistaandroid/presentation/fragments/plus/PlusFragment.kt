package me.annenkov.julistaandroid.presentation.fragments.plus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_plus.*
import kotlinx.android.synthetic.main.item_plus_notifications.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.Utils
import me.annenkov.julistaandroid.domain.model.OnPurchase
import me.annenkov.julistaandroid.presentation.ViewPagerFragment
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.toast

class PlusFragment : ViewPagerFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_plus, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Preferences.getInstance(activity!!).notificationsSubscription)
            buy.text = getString(R.string.plus_activated)

        botVk.setOnClickListener { _ ->
            val botCode = Preferences.getInstance(activity!!).botCode
            alert("Вы можете подключить нашего бота для ВК. " +
                    "Просто откройте бота и следуйте инструкциям.\n\n" +
                    "Твой код: $botCode", "Бот для ВК") {
                positiveButton("Скопировать и открыть") {
                    Utils.copyToClipboard(activity!!,
                            "Код для бота",
                            botCode)
                    toast("Код скопирован")

                    browse("https://vk.me/julista.mobile")
                }
                noButton {}
            }.show()
        }
        buy.setOnClickListener {
            EventBus.getDefault().post(OnPurchase())
        }
    }

    override fun fetchData() {
    }
}