package org.example.util

object CommonUtil {
    fun <T> getInput(msg: String, transform: (String) -> T): T {
        while (true) {
            print(msg)
            try {
                return transform(readln())
            } catch (e: Exception) {
                println(ENTER_VALID_OPTION)
            }
        }
    }
}