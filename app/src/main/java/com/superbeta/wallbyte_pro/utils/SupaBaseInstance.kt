package com.superbeta.wallbyte_pro.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseInstance {
    val supabase = createSupabaseClient(
        supabaseUrl = "supabase_url",
        supabaseKey = "supabase_key"
    ) {
        install(Auth)
        install(Postgrest)
    }

}
