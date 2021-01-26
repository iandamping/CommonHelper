package com.ian.app.helper.util

import java.text.NumberFormat
import java.util.*


/**
 * Created by Ian Damping on 08,June,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun getDollarConvertToRupiah(nominal: String?): String {
    val localeID = Locale("en", "US")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

    /** why we use dollar currency ? because we can achieve this with that
     * Rp 3,999.00*/
    return if (nominal != null) {
        if (nominal.contains(".")){
            val strPartsOfNominal: List<String> = nominal.split(".")
            when {
                strPartsOfNominal[1].length > 1 -> {
                    if (strPartsOfNominal[1] == "00"){
                        formatRupiah.apply {
                            minimumFractionDigits = 0
                            maximumFractionDigits = 0
                        }
                    }else{
                        formatRupiah.apply {
                            minimumFractionDigits = 2
                            maximumFractionDigits = 2
                        }
                    }

                }
                strPartsOfNominal[1].length < 2 -> {
                    formatRupiah.apply {
                        minimumFractionDigits = 1
                        maximumFractionDigits = 1
                    }
                }
            }
        }else{
            formatRupiah.apply {
                minimumFractionDigits = 0
                maximumFractionDigits = 0
            }
        }

        //this is where you want to replace $ with any currency you want
        val replacingRp = formatRupiah.format(nominal.toDouble()).replace("$", "Rp ")
        replacingRp


    } else "0"
}