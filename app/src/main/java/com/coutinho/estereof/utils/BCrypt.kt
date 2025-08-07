// src/main/java/com/coutinho/estereof/utils/BCrypt.kt
package com.coutinho.estereof.utils

import org.mindrot.jbcrypt.BCrypt

fun encryptPassword(password: String): String {
    // gere um método mais forte, com um custo de 12
    return BCrypt.hashpw(password, BCrypt.gensalt(12))
}

// Nova função para verificar a senha
fun checkPassword(password: String, hashed: String): Boolean {
    return BCrypt.checkpw(password, hashed)
}