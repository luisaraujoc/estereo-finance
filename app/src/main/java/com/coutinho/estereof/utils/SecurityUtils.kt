package com.coutinho.estereof.utils

import org.mindrot.jbcrypt.BCrypt


object SecurityUtils {

    private const val BCRYPT_LOG_ROUNDS = 10

    fun hashPassword(password: String): String {
        // BCrypt.hashpw gera um salt aleatório e faz o hash da senha.
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_LOG_ROUNDS))
    }

    fun checkPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }
}
