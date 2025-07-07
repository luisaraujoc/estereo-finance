package com.coutinho.estereof.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val dotenv = Dotenv.configure()
    .directory("/assets/")
    .filename(".env")
    .load()

val APIURL = dotenv["SUPABASE_URL"] ?: "https://your-supabase-url.supabase.co"
val APIKEY = dotenv["SUPABASE_KEY"] ?: "your-supabase-key"

val supabase = createSupabaseClient(
    supabaseUrl = APIURL,
    supabaseKey = APIKEY
){
    install(Postgrest)
}