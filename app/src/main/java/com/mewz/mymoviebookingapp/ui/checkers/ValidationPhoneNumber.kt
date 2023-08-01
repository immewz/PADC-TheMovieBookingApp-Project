package com.mewz.mymoviebookingapp.ui.checkers

class ValidationPhoneNumber(private var phone: String) {

    var showErrorMessage: String = ""

    fun isValidatedPhoneNumber(): Boolean {
        return emptySituation() && filledSituation()
    }

    private fun emptySituation(): Boolean {
        return if (phone.isNotEmpty()){
            true
        }else{
            showErrorMessage = "Phone Number can't be empty"
            false
        }
    }

    private fun filledSituation(): Boolean {
        return if (phone.length == 10){
            if(phone.startsWith("9")){
                true
            }else{
                showErrorMessage = "Please start with 9 "
                false
            }
        }else{
            showErrorMessage = "Please! Check your phone number"
            false
        }
    }

}