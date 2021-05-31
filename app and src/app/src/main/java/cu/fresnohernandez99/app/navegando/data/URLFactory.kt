package cu.fresnohernandez99.app.navegando.data

import cu.fresnohernandez99.app.navegando.utils.enums.Environment

object URLFactory {

    private val host = Environment.DEVELOP

    fun getEntryPoint() = when (host) {
        Environment.DEVELOP -> "http://160.100.10.94:4004/"
        Environment.TEST -> ""
        Environment.PRODUCTION -> "https://navegando-api.herokuapp.com/"
    }

    //EndPoints --> GET INFO
    const val update = "update"

    //login
    const val login = "login"

    //records
    const val updateRecord = "update-record"

    //suggestion
    const val sendSuggestion = "make-suggestion"
}