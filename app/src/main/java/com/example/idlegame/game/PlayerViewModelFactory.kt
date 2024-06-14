import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.idlegame.game.PlayerViewModel

class PlayerViewModelFactory(
    private val context: Context,
    private val soundState: MutableState<Boolean>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(context, soundState) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
