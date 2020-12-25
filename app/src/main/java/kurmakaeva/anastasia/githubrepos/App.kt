package kurmakaeva.anastasia.githubrepos

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {
        var context: Context? = null
            private set
    }
}