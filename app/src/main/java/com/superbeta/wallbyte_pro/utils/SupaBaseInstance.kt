package com.superbeta.wallbyte_pro.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseInstance {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://brjyhpkgerglihfbwthb.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJyanlocGtnZXJnbGloZmJ3dGhiIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NjcyMzI4NTQsImV4cCI6MTk4MjgwODg1NH0.zVyvxDBDIlB8AFuEamG5ioKnHf0nx7KwBDCOqJ011-o"
    ) {
        install(Auth)
        install(Postgrest)
    }

}