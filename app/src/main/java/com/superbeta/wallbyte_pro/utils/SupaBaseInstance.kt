package com.superbeta.wallbyte_pro.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseInstance {
    val supabase = createSupabaseClient(
        supabaseUrl = "your_supabase_url_here",
        supabaseKey = "your_supabase_key_here"
    ) {
        install(Auth)
        install(Postgrest)
    }

}
