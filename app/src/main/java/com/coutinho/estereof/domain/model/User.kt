package com.coutinho.estereof.domain.model

import java.util.Date

/**
 * Modelo de domínio para um utilizador.
 * Representa o utilizador na camada de negócio, agnóstico à persistência ou API.
 */
data class User(
    val id: String, // UUID do Supabase, usado como ID principal do utilizador
    val name: String,
    val username: String,
    val email: String,
    val createdAt: Date,
    val updatedAt: Date
)
