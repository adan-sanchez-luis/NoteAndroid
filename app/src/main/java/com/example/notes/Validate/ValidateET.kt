package com.example.notes.Validate

import android.widget.EditText

class ValidateET {

    companion object {

        fun isBlanck(editTexts: List<EditText>): Boolean {
            var isValid = true

            for (editText in editTexts) {
                if (editText.text.isBlank()) {
                    editText.error = "Este campo es obligatorio"
                    isValid = false
                }
            }

            return isValid
        }


        fun isPhone(editText: EditText): Boolean {
            val phoneNumber = editText.text.toString().trim()

            if (phoneNumber.matches(Regex("\\d{10}"))) {
                return true
            } else {
                editText.error = "Ingrese un número de teléfono válido (10 dígitos)"
                return false
            }
        }
    }
}