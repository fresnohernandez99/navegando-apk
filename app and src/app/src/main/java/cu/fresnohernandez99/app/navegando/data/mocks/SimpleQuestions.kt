package cu.fresnohernandez99.app.navegando.data.mocks

import cu.fresnohernandez99.app.navegando.data.model.entities.Option
import cu.fresnohernandez99.app.navegando.data.model.entities.Question

object SimpleQuestions {

    //Start Fragment
    val FIRST_1 =
        Question(
            0,
            "¿Estás preocupado por  no estar al día con el internet y las nuevas tendencias?",
            arrayListOf(
                Option(0, "Sí"),
                Option(1, "Quiero ayudar a alguien"),
                Option(3, "Sugiéreme buen contenido"),
                Option(4, "No sé que hago aquí")
            )
        )

    val FIRST_2 =
        Question(
            0,
            "¿En qué te puedo ayudar?",
            arrayListOf(
                Option(0, "Ver Tutoriales"),
                Option(1, "Lista de Temas"),
                Option(3, "Descubre más"),
                Option(4, "Ayuda")
            )
        )


    val CONTINUE_OR_START =
        Question(
            0,
            "Seleccione su acción",
            arrayListOf(
                Option(0, "Inicio Nuevo"),
                Option(1, "Continuar")
            )
        )

    val START =
        Question(
            0,
            "Seleccione su acción",
            arrayListOf(
                Option(0, "Iniciar")
            )
        )

    //Menu Fragment
    val MENU =
        Question(
            0,
            "¿Qué harás ahora?",
            arrayListOf(
                Option(0, "¿Qué estoy viendo?"),
                Option(1, "Quiero sugerir un tema"),
                Option(2, "Quiero salir")
            )
        )

    //Tutorial Fragment
    val SURVEY =
        Question(
            0,
            "¿Te quedó claro?",
            arrayListOf(
                Option(0, "Sí"),
                Option(1, "No"),
                Option(2, "Quiero salir")
            )
        )

    //Help Fragment
    val HELP =
        Question(
            0,
            "¿Cuál es tu pregunta?",
            arrayListOf(
                Option(0, "¿De qué trata esto?"),
                Option(1, "¿Quién hizo la app?"),
                Option(2, "¿Puedo ayudar?")
            )
        )

    //Menu Fragment
    val SPONSORS =
        Question(
            0,
            "¿Qué harás ahora?",
            arrayListOf(
                Option(0, "¿Qué estoy viendo?"),
                Option(1, "Quiero sugerir mi app"),
                Option(2, "Quiero salir")
            )
        )
}