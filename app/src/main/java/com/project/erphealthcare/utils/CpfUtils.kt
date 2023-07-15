package com.project.erphealthcare.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class CpfUtils {
    companion object {
        private fun replaceChars(cpfFull : String) : String{
            return cpfFull.replace(".", "").replace("-", "")
                .replace("(", "").replace(")", "")
                .replace("/", "").replace(" ", "")
                .replace("*", "")
        }


        fun mask(mask : String, etCpf : EditText) : TextWatcher {

            val textWatcher : TextWatcher = object : TextWatcher {
                var isUpdating : Boolean = false
                var oldString : String = ""
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val str = replaceChars(s.toString())
                    var cpfWithMask = ""

                    if (count == 0)//is deleting
                        isUpdating = true

                    if (isUpdating){
                        oldString = str
                        isUpdating = false
                        return
                    }

                    var i = 0
                    for (m : Char in mask.toCharArray()){
                        if (m != '#' && str.length > oldString.length){
                            cpfWithMask += m
                            continue
                        }
                        try {
                            cpfWithMask += str.get(i)
                        }catch (e : Exception){
                            break
                        }
                        i++
                    }

                    isUpdating = true
                    etCpf.setText(cpfWithMask)
                    etCpf.setSelection(cpfWithMask.length)

                }

                override fun afterTextChanged(editable: Editable) {

                }
            }

            return textWatcher
        }

        fun myValidateCPF(cpf : String) : Boolean{
            val cpfClean = cpf.replace(".", "").replace("-", "")

            //## check if size is eleven
            if (cpfClean.length != 11)
                return false

            //## check if is number
            try {
                val number  = cpfClean.toLong()
            }catch (e : Exception){
                return false
            }

            //continue
            var dvCurrent10 = cpfClean.substring(9,10).toInt()
            var dvCurrent11= cpfClean.substring(10,11).toInt()

            //the sum of the nine first digits determines the tenth digit
            val cpfNineFirst = IntArray(9)
            var i = 9
            while (i > 0 ) {
                cpfNineFirst[i-1] = cpfClean.substring(i-1, i).toInt()
                i--
            }
            //multiple the nine digits for your weights: 10,9..2
            var sumProductNine = IntArray(9)
            var weight = 10
            var position = 0
            while (weight >= 2){
                sumProductNine[position] = weight * cpfNineFirst[position]
                weight--
                position++
            }
            //Verify the nineth digit
            var dvForTenthDigit = sumProductNine.sum() % 11
            dvForTenthDigit = 11 - dvForTenthDigit //rule for tenth digit
            if(dvForTenthDigit > 9)
                dvForTenthDigit = 0
            if (dvForTenthDigit != dvCurrent10)
                return false

            //### verify tenth digit
            var cpfTenFirst = cpfNineFirst.copyOf(10)
            cpfTenFirst[9] = dvCurrent10
            //multiple the nine digits for your weights: 10,9..2
            var sumProductTen = IntArray(10)
            var w = 11
            var p = 0
            while (w >= 2){
                sumProductTen[p] = w * cpfTenFirst[p]
                w--
                p++
            }
            //Verify the nineth digit
            var dvForeleventhDigit = sumProductTen.sum() % 11
            dvForeleventhDigit = 11 - dvForeleventhDigit //rule for tenth digit
            if(dvForeleventhDigit > 9)
                dvForeleventhDigit = 0
            if (dvForeleventhDigit != dvCurrent11)
                return false

            return true
        }
    }
}